package cn.freeexchange.common.base.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassUtils {
	
	/**
     * 获取类的泛型类型
     *
     * @param ownerClass
     * @param idx
     * @return
     */
    public static Class<?> getGenericType(Class<?> ownerClass, int idx) {
        Type genericSuperclass = ownerClass.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            if (genericSuperclass != null) {//考虑到cglib生成子类型的原因,所以此处使用了递归调用
                return getGenericType((Class<?>) genericSuperclass, idx);
            } else {
                throw new RuntimeException("宿主类【" + ownerClass + "】的父类型【" + genericSuperclass + "】不是泛型类型！");
            }
        }
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        if (idx > actualTypeArguments.length) {
            throw new RuntimeException("指定索引【" + idx + "】大于泛型数量【" + actualTypeArguments.length + "】");
        }
        return (Class<?>) actualTypeArguments[idx];
    }
    
    public static void setValueToField(Object aimObj, String fieldName, Object filedValue) {
        try {
            setObjectValueToField(aimObj, aimObj.getClass().getField(fieldName), filedValue);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("获取[" + aimObj + "]的[" + fieldName + "]属性发生异常:" + e.getMessage(), e);
        }
    }

    /**
     * 将Object值设置到特定对象的特定属性中
     *
     * @param aimObj
     * @param field
     * @param filedValue
     */
    public static void setObjectValueToField(Object aimObj, Field field, Object filedValue) {
        try {
            if (filedValue != null) {
                field.setAccessible(true);
                field.set(aimObj, filedValue);
            }
        } catch (Exception e) {
            throw new RuntimeException("将属性[" + field.getName() + "]的值[" + filedValue + "]设置到对象[" + aimObj + "]时发生异常", e);
        }
    }
    
    
    public static final String[] WORK_PACKAGE_NAMES = { "com.eju.fbc" };

    /**
     * 取得类路径下指定文件夹下指定类型名的文件列表
     *
     * @param dirPath  文件夹名称（例如：com/shengpay)
     * @param fileType 类型名称（例如：.class 或者 .properties)
     * @return 符合要求的文件路面集合(例如：\com\shengpay\commons\core\propertiesfile\
     * PropertiesFileHandlerImplForSystemFile.class）
     */
    public static Set<String> getClassSetByPackageName(String dirPath, String fileType) {
        // 取得包含有指定包的所有URL集合
        Enumeration<URL> resources;
        try {
            resources = Thread.currentThread().getContextClassLoader().getResources(dirPath.replace('\\', '/'));
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }

        // 分别从文件夹或JAR中取得类集合
        Set<String> classSet = new HashSet<String>();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();

            log.info("从路径【" + url + "】加载类信息！");
            String protocol = url.getProtocol();
            if ("jar".equals(protocol)) {
                try {
                    getClassSetForJar(dirPath, url, classSet, fileType);
                } catch (Exception e) {
                    throw new RuntimeException("", e);
                }
            } else if ("file".equals(protocol)) {
                String filePath;
                try {
                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("", e);
                }
                findAndAddClassesInPackageByFile(dirPath, filePath, classSet, fileType);
            }
        }
        return classSet;
    }

    /**
     * @param packageName
     * @param url
     * @param classSet
     * @param fileType
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void getClassSetForJar(String packageName, URL url, Set<String> classSet, String fileType) throws IOException, ClassNotFoundException {
        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String jarEntryName = jarEntry.getName();
            if (jarEntry.isDirectory()) {
                continue;
            }
            if (!jarEntryName.startsWith(packageName)) {
                continue;
            }
            if (!jarEntryName.endsWith(fileType)) {
                continue;
            }
            classSet.add(jarEntryName);
        }
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param classSet
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, Set<String> classSet, final String fileType) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (file.isDirectory()) || (file.getName().endsWith(fileType));
            }
        });

        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + '/' + file.getName(), file.getAbsolutePath(), classSet, fileType);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName();
                classSet.add(packageName + '/' + className);
            }
        }
    }
}
