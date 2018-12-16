package cn.freeexchange.common.base.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountUtils {
	
	
	
	public static String toAmount(Long banlance) {
		BigDecimal amt = new BigDecimal(banlance).divide(new BigDecimal(1000));
		return formatAmount(amt).toString();
	}
	
	 private static BigDecimal formatAmount(BigDecimal amount){
		 return amount.setScale(2, RoundingMode.HALF_DOWN);
	 }
}
