package cn.freeexchange.common.base.utils;

public class RediskeyUtils {
	
	public static String makeTokenRedisKey(String partner,String openId) {
        return String.format("gw_loginToken_%s_%s",partner,openId);
    }

}
