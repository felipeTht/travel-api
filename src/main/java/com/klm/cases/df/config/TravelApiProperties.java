package com.klm.cases.df.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;


@Configuration
@ConfigurationProperties(prefix = "api")
@PropertySource(value = "classpath:application.yml")
@Getter
@Setter
public class TravelApiProperties {
	
	private String user;
	private String password;
	private String hostName;
	private int hostPort;
	private String hostScheme;
	private String baseUrl;
	private String airportsEndpoint;
	private String fareEndPoint;
}
