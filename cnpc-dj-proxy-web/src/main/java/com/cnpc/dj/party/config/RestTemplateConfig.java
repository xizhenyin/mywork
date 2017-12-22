package com.cnpc.dj.party.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	@Bean
	public RestTemplate restTemplate() {
		// 闀胯繛鎺ヤ繚鎸�30绉�
		PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		// 鎬昏繛鎺ユ暟
		pollingConnectionManager.setMaxTotal(1000);
		// 鍚岃矾鐢辩殑骞跺彂鏁�
		pollingConnectionManager.setDefaultMaxPerRoute(1000);
		
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		httpClientBuilder.setConnectionManager(pollingConnectionManager);
		// 閲嶈瘯娆℃暟锛岄粯璁ゆ槸3娆★紝娌℃湁寮�鍚�
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
		// 淇濇寔闀胯繛鎺ラ厤缃紝闇�瑕佸湪澶存坊鍔燢eep-Alive
		httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

		// RequestConfig.Builder builder = RequestConfig.custom();
		// builder.setConnectionRequestTimeout(200);
		// builder.setConnectTimeout(5000);
		// builder.setSocketTimeout(5000);
		//
		// RequestConfig requestConfig = builder.build();
		// httpClientBuilder.setDefaultRequestConfig(requestConfig);

		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader("Content-Type", "text/html"));  
        headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml,application/json;q=0.9,image/webp,*/*;q=0.8"));
		headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN"));
		headers.add(new BasicHeader("Connection", "Keep-Alive"));

		httpClientBuilder.setDefaultHeaders(headers);

		HttpClient httpClient = httpClientBuilder.build();

		// httpClient杩炴帴閰嶇疆锛屽簳灞傛槸閰嶇疆RequestConfig
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);
		// 杩炴帴瓒呮椂
		clientHttpRequestFactory.setConnectTimeout(5000);
		// 鏁版嵁璇诲彇瓒呮椂鏃堕棿锛屽嵆SocketTimeout
		clientHttpRequestFactory.setReadTimeout(1000 * 10);
		// 杩炴帴涓嶅鐢ㄧ殑绛夊緟鏃堕棿锛屼笉瀹滆繃闀匡紝蹇呴』璁剧疆锛屾瘮濡傝繛鎺ヤ笉澶熺敤鏃讹紝鏃堕棿杩囬暱灏嗘槸鐏鹃毦鎬х殑
		clientHttpRequestFactory.setConnectionRequestTimeout(200);
		// 缂撳啿璇锋眰鏁版嵁锛岄粯璁ゅ�兼槸true銆傞�氳繃POST鎴栬�匬UT澶ч噺鍙戦�佹暟鎹椂锛屽缓璁皢姝ゅ睘鎬ф洿鏀逛负false锛屼互鍏嶈�楀敖鍐呭瓨銆�
		// clientHttpRequestFactory.setBufferRequestBody(false);

		// 娣诲姞鍐呭杞崲鍣�
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
		
		RestTemplate restTemplate = new RestTemplate(messageConverters);
		restTemplate.setRequestFactory(clientHttpRequestFactory);
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

		return restTemplate;
	}
	
/*	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory) {
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Bean
	public HttpClient httpClient() {
		return HttpClientBuilder.create().build();
	}*/
}
