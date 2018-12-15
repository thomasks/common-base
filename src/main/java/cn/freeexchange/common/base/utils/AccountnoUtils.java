package cn.freeexchange.common.base.utils;

public class AccountnoUtils {
	
	public static String makeL1AccountNo(String subjectCode) {
        return String.format("%s",subjectCode);
    }
	
	public static String makeL2AccountNo(String subjectCode,String openId,String accountSeq) {
        return String.format("%s%s%s",subjectCode,openId,accountSeq);
    }

}
