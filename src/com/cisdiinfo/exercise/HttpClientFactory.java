package com.cisdiinfo.exercise;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 对HttpClient进行封装<br>
 * 
 * @author Leckie
 * @date 2014年10月25日
 */
@SuppressWarnings( "deprecation" )
public class HttpClientFactory {
	private static HttpClient httpClient = null;

	private HttpClientFactory() {}

	/**
	 * 获取httpClient对象<br>
	 * 
	 * @return
	 */
	public static synchronized HttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
		}
		// TODO:下下之策，应该引入线程池，开多个线程并发去执行任务
		return new DefaultHttpClient();
	}
}
