package cn.freeexchange.common.base.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class HttpUtils {
	
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
	

    private static RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
    private static String UTF_8 = "UTF-8";

    public static String sendGet(String reqUrl, Map<String, String> params) {
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        HttpGet request = new HttpGet();
        try {
        	URI uri = new URI(buildUrl(reqUrl, params));
            request.setURI(uri);
            log.info("begin http get,url is: {}.",uri.toURL().toString());
            HttpResponse response = client.execute(request);
            String respStr = EntityUtils.toString(response.getEntity());
            log.info("end http get,url is: {},response is: {}",reqUrl,respStr);
            return respStr;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            request.releaseConnection();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String sendGet(String reqUrl) throws Exception {
        return sendGet(reqUrl, null);
    }

    public static String sendPost(String reqUrl, Map<String, String> params) {
        List<NameValuePair> list = makeNameValuePairs(params);
        if(list ==null || list.size() < 1) throw new RuntimeException("参数不全，请稍后重试");

        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        HttpPost request = null;
        try {
            request = new HttpPost(reqUrl);
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(list, UTF_8);
            request.setEntity(encodedFormEntity);
            log.info("begin http post,url is: {},request is: {}",reqUrl,encodedFormEntity);

            HttpResponse response = client.execute(request);
            String respStr = EntityUtils.toString(response.getEntity());
            log.info("end http post,url is: {},response is: {}",reqUrl,respStr);
            
            return respStr;

        } catch (Throwable throwable) {
        	log.info("http post meet error,url is: {}.",reqUrl,throwable);
            throw new RuntimeException(throwable);
        } finally {
            request.releaseConnection();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String sendPostJson(String url, String json) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = null;
        try {
            request = new HttpPost(url);
            StringEntity strEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(strEntity);
            log.info("begin http post,url is: {},request is: {},param is : {}",url,strEntity,json);
            HttpResponse response = client.execute(request);
            String respStr =  EntityUtils.toString(response.getEntity(), UTF_8);
            log.info("end http post,url is: {},response is: {}",url,respStr);
            return respStr;

        } catch (Throwable throwable) {
        	log.info("http post meet error,url is: {}.",url,throwable);
            throw new RuntimeException(throwable);
        } finally {
            request.releaseConnection();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String sendPostJsonWithHeader(String url, String json,Map<String,String> headerMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = null;
        try {
            request = new HttpPost(url);
            Set<Entry<String, String>> entrySet = headerMap.entrySet();
            for (Entry<String, String> entry : entrySet) {
            	request.setHeader(entry.getKey(), entry.getValue());
			}
            StringEntity strEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(strEntity);
            log.info("begin http post,url is: {},request is: {},param is : {}",url,strEntity,json);
            HttpResponse response = client.execute(request);
            String respStr =  EntityUtils.toString(response.getEntity(), UTF_8);
            log.info("end http post,url is: {},response is: {}",url,respStr);
            return respStr;

        } catch (Throwable throwable) {
        	log.info("http post meet error,url is: {}.",url,throwable);
            throw new RuntimeException(throwable);
        } finally {
            request.releaseConnection();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static String buildUrl(String reqUrl, Map<String, String> params) {
        if(params == null || params.isEmpty()) return reqUrl;
        StringBuilder query = new StringBuilder();
        Set<String> set = params.keySet();
        for (String key : set) {
            query.append(String.format("%s=%s&", key, params.get(key)));
        }
        return reqUrl + "?" + query.toString();
    }

    private static List<NameValuePair> makeNameValuePairs(Map<String, String> params){
        Set<String> set = params.keySet();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String key : set) {
            list.add(new BasicNameValuePair(key, params.get(key)));
        }
        return list;
    }
}