package com.kms.test;



import java.io.IOException;
import java.util.Arrays;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class TestHttpClient {
	
	protected static Logger logger = Logger.getLogger( TestLog4J.class.getName());
public static void main(String[] args) {
	//System.out.print("test");

	//DEBUG < INFO < WARN < ERROR < FATAL

	logger.fatal("log4j:logger.fatal()");
	logger.error("log4j:logger.error()");
	logger.warn("log4j:logger.warn()");
	logger.info("log4j:logger.info()");
	logger.debug("log4j:logger.debug()");

	
/*    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(18);
    cm.setDefaultMaxPerRoute(6);*/
    
    CloseableHttpClient httpclient = null; 

/*    RequestConfig requestConfig = RequestConfig.custom()
    .setSocketTimeout(30000)
    .setConnectTimeout(30000)
    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM))
    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
    .build();

    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY,
            new NTCredentials("sj10706", "Citibank01", "dummy", "APAC"));
*/
    // Finally we instantiate the client. Client is a thread safe object and can be used by several threads at the same time. 
    // Client can be used for several request. The life span of the client must be equal to the life span of this EJB.
     httpclient = HttpClients.custom()
    //.setConnectionManager(cm)
    //.setDefaultCredentialsProvider(credentialsProvider)
    //.setDefaultRequestConfig(requestConfig)
    .build();
    
    // HttpPost httppost = new HttpPost("http://www.naver.com");   
     HttpPost httpPost = new HttpPost("http://www.whatsmyuseragent.com/");
     //httppost.setConfig(requestConfig);      

     httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36");
     
     //httpclient.setAttribute( CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13");

     
 	// Proxy 정보 생성
 //    HttpHost proxy = new HttpHost("krproxy.apac.nsroot.net", 8080);

/*     RequestConfig config = RequestConfig.custom()
         .setProxy(proxy)
         .build();     
     
     httpPost.setConfig(config);   
*/     
     // HttpClientContext is not thread safe, one per request must be created.
    // HttpClientContext context = HttpClientContext.create();    
     HttpResponse response;
	try {
 		 response = httpclient.execute(httpPost);
 		 System.out.println( EntityUtils.toString(response.getEntity()) );

	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     

  }
} 