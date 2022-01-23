package com.klm.cases.df.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;

public class CustomWebClientTags extends DefaultWebMvcTagsProvider {
	public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler,
			Throwable exception) {
		return Tags.of(super.getTags(request, response, handler, exception)).and(getTenantTag(response));
	}

	private Tag getTenantTag(HttpServletResponse response) {

		String tagValue = String.valueOf(response.getStatus());
		
		if (response.getStatus() >= 400 && response.getStatus() < 500) {
			tagValue = "4xx";
		} else if (response.getStatus() >= 500 && response.getStatus() < 600) {
			tagValue = "5xx";
		}
		
		return Tag.of("status", tagValue);
	}
}
