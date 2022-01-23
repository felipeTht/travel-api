package com.klm.cases.df.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/metrics")
public class MetricsController {

	@Autowired
	private MetricService metricService;
	
	@GetMapping
	public MetricsDto getMetrics() {
		return metricService.getMetrics();
	}
}
