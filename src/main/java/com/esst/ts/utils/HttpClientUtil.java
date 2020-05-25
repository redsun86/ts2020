package com.esst.ts.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	/*
	 * 最大链接数
	 */
	private final static int MAX_TOTAL_CONS =600;
	/*
	 * 每个路由最大连接数
	 */
	private final static int MAX_ROUTE_CONS = 600;
	/*
	 * 链接超时时间
	 */
	private final static int CON_TIMEOUT = 60000;
	/*
	 * 读取超时时间
	 */
	private final static int READ_TIMEOUT = 2*60*1000;

	private static HttpClient client = null;

	static {
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(CON_TIMEOUT)
				.setSocketTimeout(READ_TIMEOUT).build();

		PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
		clientConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONS);
		clientConnectionManager.setMaxTotal(MAX_TOTAL_CONS);

		client = HttpClients.custom()
				.setDefaultRequestConfig(config)
				.setConnectionManager(clientConnectionManager)
				.build();
	}

	public static String doRequestForward(String url, String method, String param) {
		String result = "";

		HttpRequestBase requestBase = null;

		if ("POST".equals(method)) {
			requestBase = getPost(url, param);
		}

		HttpResponse response = null;

		try {
			response = client.execute(requestBase);
			if (null != response) {
				result = new String(EntityUtils.toByteArray(response.getEntity()), "UTF-8");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static HttpPost getPost(String url, String param) {
		HttpPost httpPost = new HttpPost(url);

		StringEntity entity = new StringEntity(param, ContentType.APPLICATION_JSON);
		httpPost.setEntity(entity);

		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Content-Encoding", "UTF-8");

		return httpPost;
	}

}	
