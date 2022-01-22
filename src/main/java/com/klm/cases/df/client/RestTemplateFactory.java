package com.klm.cases.df.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.klm.cases.df.config.TravelApiProperties;

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {
	private RestTemplate restTemplate;

	@Autowired
	private TravelApiProperties travelApiProperties;

	public RestTemplateFactory() {
		super();
	}

	@Override
	public RestTemplate getObject() {
		return restTemplate;
	}

	@Override
	public Class<RestTemplate> getObjectType() {
		return RestTemplate.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private List<HttpMessageConverter<?>> getConverterList() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModules(new Jackson2HalModule(), new JavaTimeModule());
		mapper.findAndRegisterModules().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON, MediaType.APPLICATION_JSON));
		converter.setObjectMapper(mapper);

		List<HttpMessageConverter<?>> converterList = new ArrayList<>();
		converterList.add(converter);
		return converterList;
	}

	@Override
	public void afterPropertiesSet() {
		HttpHost host = new HttpHost(travelApiProperties.getHostName(), travelApiProperties.getHostPort(),
				travelApiProperties.getHostScheme());
		final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(host);
		restTemplate = new RestTemplate(requestFactory);
		restTemplate.setMessageConverters(getConverterList());
		restTemplate.getInterceptors().add(
				new BasicAuthenticationInterceptor(travelApiProperties.getUser(), travelApiProperties.getPassword()));
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
	}

}