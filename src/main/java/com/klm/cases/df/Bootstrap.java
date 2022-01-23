package com.klm.cases.df;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.klm.cases.df.config.CustomWebClientTags;

@SpringBootApplication
@EnableAsync
public class Bootstrap {

	public static void main(final String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(50);
		executor.setMaxPoolSize(50);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("async-task-");
		executor.initialize();
		return executor;
	}
	
	@Bean
	public WebMvcTagsProvider webMvcTagsProvider() {
	    return new CustomWebClientTags();
	}

}
