package cn.freeexchange.common.base.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.freeexchange.common.base.exception.BusinessException;
import cn.freeexchange.common.base.exception.extra.PropertiesFileHandler;
import cn.freeexchange.common.base.exception.extra.PropertiesFileHandlerImplForResourceBundle;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by qiuliujun on 2018/5/7
 */
@Slf4j
public class BusinessExceptionUtils {

    private static Set<String> bcFilePathArr;

    private static List<PropertiesFileHandler> messageResourceHandlerList;
    static {
        //取得保护业务代码文件的路径信息
        String fileType=".properties";
        messageResourceHandlerList = new ArrayList<PropertiesFileHandler>();
        Set<String> allPropertiesFilePathArr = ClassUtils.getClassSetByPackageName("META-INF", fileType);
        bcFilePathArr=new HashSet<String>();
        for (String bcFile : allPropertiesFilePathArr) {
            if(bcFile.indexOf("bc_")==-1){
                continue;
            }

            try {
                messageResourceHandlerList.add(new PropertiesFileHandlerImplForResourceBundle(bcFile.replace(fileType, "")));
            } catch (Throwable t) {
                log.error("绑定资源文件[" + bcFile + "]时发生错误[" + t.getMessage() + "],现已跳过该文件!");
            }
            bcFilePathArr.add(bcFile);
        }
    }

    /**
     * 获取业务异常信息
     *
     * @param e
     * @return
     */
    public static String getBusinessInfo(BusinessException e) {
        return getBusinessInfo(e.getBusinessCode(), e.getArgs());
    }

    /**
     * 获取业务异常信息
     *
     * @param businessCode 业务信息代码
     * @param businessArgs 业务信息参数
     * @return
     */
    public static String getBusinessInfo(String businessCode, Object ... businessArgs) {
        for (PropertiesFileHandler messageResourceHandler : messageResourceHandlerList) {
            String message = messageResourceHandler.getStringCanNull(businessCode, businessArgs);
            if (message != null) {
                return message;
            }
        }
        throw new RuntimeException("未能从资源[" + bcFilePathArr + "]中找到业务异常[" + businessCode + "]对应的业务信息");
    }
}