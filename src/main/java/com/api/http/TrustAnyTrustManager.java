package com.api.http;




import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 
* 类描述：   工具类
* 项目名称：zhaieq   
* 类名称：TrustAnyTrustManager   
* 创建人：lilin
* 创建时间：2015年5月5日 下午1:53:49   
* 修改人：lilin 
* @version
 */
public class TrustAnyTrustManager implements X509TrustManager{

	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}