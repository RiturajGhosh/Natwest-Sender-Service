package com.natwest.transactionsender.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This class is to configure the rest template
 * 
 * @author Rituraj
 *
 */
@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

	private final Integer maxTotalConnections;
	private final Integer defaultMaxTotalConnetions;
	private final Integer connectionRequestTimeout;
	private final Integer socketTimeout;

	public BlockingRestTemplateCustomizer(@Value("${natwest.maxtotalconnections}") Integer maxTotalConnections,
			@Value("${natwest.defaultmaxtotalconnections}") Integer defaultMaxTotalConnetions,
			@Value("${natwest.connectionrequesttimeout}") Integer connectionRequestTimeout,
			@Value("${natwest.sockettimeout}") Integer socketTimeout) {
		this.maxTotalConnections = maxTotalConnections;
		this.defaultMaxTotalConnetions = defaultMaxTotalConnetions;
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.socketTimeout = socketTimeout;
	}

	public ClientHttpRequestFactory clientHttpRequestFactory() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxTotalConnections);
		connectionManager.setDefaultMaxPerRoute(defaultMaxTotalConnetions);

		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
				.setSocketTimeout(socketTimeout).build();

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).setDefaultRequestConfig(requestConfig)
				.build();

		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	public void customize(RestTemplate restTemplate) {
		restTemplate.setRequestFactory(this.clientHttpRequestFactory());
	}

}