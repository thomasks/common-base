package cn.freeexchange.common.base.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtils {

    /**
     * 获取Throwable信息
     *
     * @param t
     * @return
     */
    public static String getThrowableInfo(Throwable t) {
        if (t == null) return null;

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);

        return stringWriter.toString();
    }

    /**
     * 取得异常的根信息
     *
     * @param t
     * @return
     */
    public static String getRootMessage(Throwable t) {
        if (t == null) return "";

        String message;
        while ((message = t.getMessage()) == null) {
            Throwable cause = t.getCause();
            if (cause != null) {
                t = cause;
            } else {
                return t.getClass().getName();
            }
        }

        return message;
    }
}
