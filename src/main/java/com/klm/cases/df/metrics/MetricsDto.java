package com.klm.cases.df.metrics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetricsDto {
	private long okStatusRespNumber;
	private long notFoundRespNumber;
	private long errorRespNumber;
	private double avgResponseTime;
	private long minResponseTime;
	private long maxResponseTime;
}
