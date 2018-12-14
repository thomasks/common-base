package cn.freeexchange.common.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by qiuliujun on 2018/8/28
 */
@Component
public class GetBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private GetBeanUtil() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
    public static <T> T getBean(Class<T> clazz, String name) { return applicationContext.getBean(name, clazz); }
}