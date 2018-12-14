package cn.freeexchange.common.base.exception.extra;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by qiuliujun on 2018/5/7
 */
public class PropertiesFileHandlerImplForSystemFile extends PropertiesFileHandlerImplForAbstract {
    /**
     * 存储资源的属性对象;
     */
    private Properties properties;

    /**
     * 构造消息资源句柄
     *
     * @param systemPropertiesFile
     */
    public PropertiesFileHandlerImplForSystemFile(String systemPropertiesFile) {
        properties=new Properties();
        try {
            properties.load(new FileInputStream(systemPropertiesFile));
        } catch (Exception e) {
            throw new RuntimeException("无法加载属性文件["+systemPropertiesFile+"]",e);
        }
    }

    /*
     * (non-Javadoc)
     */
    public String getStringCanNull(String key, Object... args) {
        String resource=properties.getProperty(key);
        if(resource==null) {
            return null;
        }
        return MessageFormat.format(resource, args);
    }
}