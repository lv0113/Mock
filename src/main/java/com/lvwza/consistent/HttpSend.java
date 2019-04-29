package com.lvwza.consistent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpSend implements AdapterI {
	private static Log log = LogFactory.getLog(HttpSend.class);
	private String url=null; 
	private String timeout="60000";
	private String encoding="UTF-8";
	private String method = "post";
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public byte[] send(byte[] data) {
		try {
			String ret = send(url,new String(data,"UTF-8"));
			return ret.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public String send(String url, String msg) {
			log.info("send data:"+msg);
		 	CloseableHttpClient httpClient = HttpClients.createSystem();
		 	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Integer.parseInt(timeout)).setConnectTimeout(3000).build();//设置请求和传输超时时
	        CloseableHttpResponse httpResponse = null;
	        try {
	        	if("post".equalsIgnoreCase(method)) {
	        		HttpPost  method = new HttpPost (url);
		        	method.setConfig(requestConfig);
		            StringEntity entity = new StringEntity(msg, encoding);
		            entity.setContentEncoding(encoding);
		            entity.setContentType("text/html");
		            method.setEntity(entity);
		            httpResponse = httpClient.execute(method);
	        	} else {
	        		HttpGet method = new HttpGet(url);
	        		httpResponse = httpClient.execute(method);
	        	}
	        	
	        	HttpEntity httpEntity = httpResponse.getEntity();
		        String recvMsg =  EntityUtils.toString(httpEntity,encoding);
		        log.info("receive data:"+recvMsg);
		        return recvMsg;
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                httpClient.close();
	                httpResponse.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	}
	

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getTimeout() {
		return timeout;
	}


	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	public static void main(String[] args) {
		
	}


	
	

}
