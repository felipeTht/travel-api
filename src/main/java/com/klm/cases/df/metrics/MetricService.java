package com.klm.cases.df.metrics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

	@Autowired
	private MetricsRepository metricsRepository;

	public void saveTime(long time, int status) {
		metricsRepository.save(Metrics.builder().time(time).status(status).build());
	}

	public MetricsDto getMetrics() {
		List<Metrics> allMetrics = metricsRepository.findAll();
		long okResponses = allMetrics.stream().filter(el -> el.getStatus() >= 200 && el.getStatus() < 300).toList()
				.size();
		long notFoundResponses = allMetrics.stream().filter(el -> el.getStatus() >= 400 && el.getStatus() < 500)
				.toList().size();
		long errorResponse = allMetrics.stream().filter(el -> el.getStatus() >= 500 && el.getStatus() < 600).toList()
				.size();

		long min = allMetrics.stream().mapToLong(el -> el.getTime()).min().orElse(0);
		long max = allMetrics.stream().mapToLong(el -> el.getTime()).max().orElse(0);
		double avg = allMetrics.stream().mapToDouble(el -> el.getTime()).average().orElse(0);

		return MetricsDto.builder().okStatusRespNumber(okResponses).notFoundRespNumber(notFoundResponses)
				.errorRespNumber(errorResponse).avgResponseTime(errorResponse).avgResponseTime(avg).minResponseTime(min)
				.maxResponseTime(max).build();
	}
}
