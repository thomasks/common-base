package cn.freeexchange.common.base.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {

	public static Map<String, String> parseHeaderMap(HttpServletRequest request) {
		Map<String, String> headerMap = new HashMap<>();
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement().toString();
			String value = request.getHeader(name);
			headerMap.put(name, value);
		}
		return headerMap;
	}
	
	//
	public static void setResponseHeader(HttpServletResponse response,
			Map<String, String> responseHeader) {
		for (Map.Entry<String, String> entry : responseHeader.entrySet()) {
			response.addHeader(entry.getKey(), entry.getValue());
		}
	}

	// get remote ip.
	public static String getRemoteIp(HttpServletRequest request) {

		// 取代理ip地址
		String ip = request.getHeader("x-forwarded-for");

		// 取nginx代理设置的ip地址
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-real-ip");
		}

		// 从网上取的
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		// 取JAVA获得的ip地址
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// 去除unkonwn
		if (ip.startsWith("unknown")) {
			ip = ip.substring(ip.indexOf("unknown") + "unknown".length());
		}

		// 去除多多余的信息
		ip = ip.trim();

		if (ip.startsWith(",")) {
			ip = ip.substring(1);
		}

		if (ip.indexOf(",") > 0) {
			ip = ip.substring(0, ip.indexOf(","));
		}

		return ip;
	}
	
	public static void writerResponseBody(HttpServletResponse response, String responseBody) {
        try {
            response.getWriter().append(responseBody);
        } catch (IOException e) {
            throw new RuntimeException("输出[" + responseBody + "]给[" + response + "]时发生异常:" + e.getMessage(), e);
        }
    }

}
