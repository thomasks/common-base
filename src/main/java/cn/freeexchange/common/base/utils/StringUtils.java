package cn.freeexchange.common.base.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static Map<String, String> string2map(String string) {
        if (string == null || string.trim().equals("")) return new HashMap<String, String>();

        Map<String, String> map = new LinkedHashMap<String, String>();
        String[] lines = string.trim().split("\n");
        for (String line : lines) {
            if (line.startsWith("#")) continue;
            int indexOf = line.indexOf("=");
            if (indexOf < 0) continue;
            String key = line.substring(0, indexOf);
            String value = line.substring(indexOf + 1);
            if (map.get(key) != null) throw new RuntimeException("[" + string + "]中有重复项[" + key + "]");
            map.put(key.trim(), value.trim());
        }

        return map;
    }

    public static String map2string(Map<?, ?> map) {
        if (map == null || map.isEmpty()) return "";

        StringBuffer buf = new StringBuffer();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (buf.length() > 0) buf.append("\n");
            buf.append(key != null ? key.toString() : "");
            buf.append("=");
            buf.append(value != null ? value.toString() : "");
        }

        return buf.toString();
    }
    
    
    public static boolean isUrl(String str) {
        str = str.toLowerCase();
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"
                + "|"
                + "([0-9a-z_!~*'()-]+\\.)*"
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\."
                + "[a-z]{2,6})"
                + "(:[0-9]{1,4})?"
                + "((/?)|"
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return Pattern.matches(regex, str);
    }
    
    /**
     * 生成指定位数验证码
     */
    public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
    
    private static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
    
    public static  boolean validEmail(String email){
		
		String regex="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";			
		return Pattern.compile(regex).matcher(email).find();			
	}
	
	public static boolean validPhone(String phone) {
		String regex="^1[3578]{1}\\d{9}$";
		return Pattern.compile(regex).matcher(phone).find();
	}
	
	
	public static String makeString(Object... values) {
        return makeString(",", values);
    }

    public static String makeString(String splitChars, Object... values) {
        return makeString(values, splitChars);
    }

    public static String makeString(Object[] objArr, String splitChars) {
        return makeString(objArr, splitChars, "", "");
    }


    public static String makeString(List list, String splitChar) {
        if (list == null) return null;
        StringBuffer buf = new StringBuffer();
        for (Object o : list) {
            if (o == null) continue;
            if (buf.length() > 0) buf.append(splitChar);
            buf.append(o.toString());
        }
        return buf.toString();
    }

    public static String makeString(Object[] objArr, String splitChars, String beginBracket, String endBracket) {
        if (objArr == null) {
            return null;
        }

        StringBuffer buf = new StringBuffer(beginBracket);
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                buf.append(splitChars);
            }
            buf.append(objArr[i]);
        }
        buf.append(endBracket);
        return buf.toString();
    }

    public static String toLowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }
}
