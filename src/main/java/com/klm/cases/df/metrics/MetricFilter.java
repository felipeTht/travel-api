package com.klm.cases.df.metrics;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

public class MetricFilter implements Filter {

    @Autowired
    private MetricService metricService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
      throws IOException, ServletException {
    	try {
    		var token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
            MDC.put("Slf4jMDCFilter.UUID", token);	
    		long start = System.currentTimeMillis();
    		chain.doFilter(request, response);
    		long end = System.currentTimeMillis();
    		int status = ((HttpServletResponse) response).getStatus();
    		metricService.saveTime((end - start), status);
    	} finally {
    		MDC.remove("Slf4jMDCFilter.UUID");
    	}
    }
}
