package cn.freeexchange.common.base.exception.extra;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by qiuliujun on 2018/5/7
 */
public class PropertiesFileHandlerImplForResourceBundle extends PropertiesFileHandlerImplForAbstract {

    /**
     * 存储资源的属性对象;
     */
    ResourceBundle resourceBundle;

    /**
     * 构造消息资源句柄
     *
     * @param resourceInClasspath
     */
    public PropertiesFileHandlerImplForResourceBundle(String resourceInClasspath) {
        resourceBundle = ResourceBundle.getBundle(resourceInClasspath);
    }

    /* (non-Javadoc)
     */
    public String getStringCanNull(String key, Object... args) {
        String resource;
        try {
            resource = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            //e.printStackTrace();
            return null;
        }

        return MessageFormat.format(resource, args);
    }
}