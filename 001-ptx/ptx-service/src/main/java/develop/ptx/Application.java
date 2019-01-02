package develop.ptx;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.servlet.Filter;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import develop.ptx.trace.UserContextFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
//@EnableResourceServer
public class Application {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		try {
			TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy).build();

			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setHttpClient(httpClient);
			restTemplate = new RestTemplate(requestFactory);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return restTemplate;
	}

	@Bean
	public Filter userContextFilter() {
		UserContextFilter userContextFilter = new UserContextFilter();
		return userContextFilter;
	}

	@Bean
	public ObjectMapper getJsonMapper() {
		return new ObjectMapper();
	}
}
