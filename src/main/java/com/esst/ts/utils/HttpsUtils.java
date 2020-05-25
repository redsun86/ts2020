package com.esst.ts.utils;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * HTTPS安全连接请求（加证书）
 * SHY
 */
public class HttpsUtils {

	public final static String CHARSET_UTF8 = "UTF-8";
	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient client = null;

	static {
		try {
			System.out.println("请求中。。。");
			/*
			 * SSLContext sslContext = SSLContexts.custom().useTLS().build();
			 * sslContext.init(null, new TrustManager[] { new X509TrustManager()
			 * {
			 *
			 * public X509Certificate[] getAcceptedIssuers() { return null; }
			 *
			 * public void checkClientTrusted( X509Certificate[] certs, String
			 * authType) { }
			 *
			 * public void checkServerTrusted( X509Certificate[] certs, String
			 * authType) { } }}, null);
			 */
			SSLContext sslContext = SSLContext.getInstance("TLS");
			X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
				public void checkClientTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null; // return new
					// java.security.cert.X509Certificate[0];
				}
			};
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket�?使用
			sslContext.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", (ConnectionSocketFactory) socketFactory)
			 		.build();

			connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			client = HttpClients.custom().setConnectionManager(connManager)
					.build();
			// Create socket configuration
			SocketConfig socketConfig = SocketConfig.custom()
					.setTcpNoDelay(true).build();
			connManager.setDefaultSocketConfig(socketConfig);
			// Create message constraints
			// MessageConstraints messageConstraints =
			// MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();
			// Create connection configuration
			ConnectionConfig connectionConfig = ConnectionConfig.custom()
					.setMalformedInputAction(CodingErrorAction.IGNORE)
					.setUnmappableInputAction(CodingErrorAction.IGNORE)
					.setCharset(Consts.UTF_8)
					// .setMessageConstraints(messageConstraints)
					.build();
			connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(50000);
			connManager.setDefaultMaxPerRoute(10000);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static void setHttpCilentTimeOut(HttpRequestBase httpRequestBase) {
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(20000)
				// sock读取超时，并不是响应处理的时�?
				.setConnectTimeout(15000).setConnectionRequestTimeout(15000)
				.build();
		//httpRequestBase.setConfig(requestConfig);
	}

	public static String postJson(String url, String json, String charset)
			throws Exception {
		if (url == null || StringUtils.isEmpty(url)) {
			return null;
		}
		// 创建HttpClient实例
		HttpResponse response = null;
		HttpEntity entity = null;
		StringEntity myEntity = new StringEntity(json,
				charset == null ? CHARSET_UTF8 : charset);

		HttpPost hp = new HttpPost(url);
		setHttpCilentTimeOut(hp);
		hp.addHeader("Content-type", "application/json");
		hp.setEntity(myEntity);
		// 发�?�请求，得到响应
		response = client.execute(hp);
		try {
			entity = response.getEntity();
			if (response.getEntity() != null) {
				return EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (entity != null)
				entity.consumeContent();
			hp.releaseConnection();
		}

		return null;
	}


	public static String postForm(String url, Map<String, String> param, String charset)
			throws Exception {
		// 创建HttpClient实例
		HttpResponse response = null;
		HttpEntity entity = null;

		HttpPost hp = new HttpPost(url);
		setHttpCilentTimeOut(hp);
		hp.addHeader("Content-type", "application/x-www-form-urlencoded");
		if (null != param) {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : param.entrySet()) {
				//给参数赋值
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams);
			hp.setEntity(urlEncodedFormEntity);
		}

		response = client.execute(hp);
		try {
			entity = response.getEntity();
			if (response.getEntity() != null) {
				return EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (entity != null)
				entity.consumeContent();
			hp.releaseConnection();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		/*// 请求地址
		//String url = "http://10.149.239.22/pcsII/service?method=LoginOrUpdate&access=mobile-app&token=cFANoortAO3T&key=SFZH&value=371426199304261228&newPWD=e10adc3949ba59abbe56e057f20f883e&oldPWD=e10adc3949ba59abbe56e057f20f883e";

		String url = "https://20.124.145.20:9486/services/IDataTransfer/dataTrans";
//String json = "{\"staffCode\":\"test-jmeter\",\"appCode\":\"10000209\",\"devId\":\"A1-12420\",\"reqServerId\":\"A2-28B7B8ADACA64D8D9B09F6C723DD9258\",\"targetServerId\":\"A3-5260771FA4F24B798529169FA3727F10\",\"targetMethod\":\"/service\",\"param\":\"{\"method\":\"LoginOrUpdate\",\"access\":\"mobile-app\",\"token\":\"cFANoortAO3T\",\"key\":\"SFZH\",\"value\":\"371426199304261228\",\"newPWD\":\"e10adc3949ba59abbe56e057f20f883e\",\"oldPWD\":\"e10adc3949ba59abbe56e057f20f883e\"}\"}";
		String json = "{\"staffCode\":\"test-jmeter\",\"appCode\":\"10000209\",\"devId\":\"A1-12420\",\"reqServerId\":\"A2-28B7B8ADACA64D8D9B09F6C723DD9258\",\"targetServerId\":\"A3-5260771FA4F24B798529169FA3727F10\",\"targetMethod\":\"/service\",\"param\":\"{\\\"method\\\":\\\"LoginOrUpdate\\\",\\\"access\\\":\\\"mobile-app\\\"}\"}";
		System.out.println(postJson(url, json, null));

		// "targetMethod": "/Hello",
		// "param":"{\"resourceUri\":\"/mobiles/login\",\"method\":\"POST\",\"userName\":\"970250\",\"password\":\"970250\"}",*/

		String url ="https://182.138.104.146:3572/sdk_service/rest/users/login/v1.1";
		Map<String, String> param = new HashMap<>();
		param.put("account","zhangsan");
		param.put("pwd","111111");

		System.out.println(postForm(url,param,null));

	}




}
