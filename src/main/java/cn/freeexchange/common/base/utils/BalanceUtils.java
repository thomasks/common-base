package cn.freeexchange.common.base.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class BalanceUtils {
	
	public static String calcBalanceAuth(String id, Long amount,String salt){
        String body = StringUtils.makeString(id, amount, salt);

        //RpcResponse<String> response = gatewayFacade.md5Sign(md5Key, body);
        //processResponse(response);
        //this.signature = response.getDto();
        return DigestUtils.sha256Hex(body);
    }

    public static boolean verifySign(String id, Long amount,String salt,String balanceAuth){
    	String body = StringUtils.makeString(id, amount, salt);
    	String actual = DigestUtils.sha256Hex(body);
    	return actual.equalsIgnoreCase(balanceAuth);
    }


}
