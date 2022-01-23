package com.klm.cases.df.fare;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.klm.cases.df.config.TravelApiProperties;
import com.klm.cases.df.location.LocationMapper;
import com.klm.cases.df.location.LocationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FareService {

	@Autowired
	private LocationService locationService;

	@Autowired
	private TravelApiProperties travelApiProperties;

	@Autowired
	private RestTemplate restTemplate;

	public FareDto calculate(String originCode, String destinationCode) {
		var origin = locationService.getLocation(originCode);
		var destination = locationService.getLocation(destinationCode);
		var fareFut = getFare(originCode, destinationCode);

		FareDto fareDto = new FareDto();

		try {

			CompletableFuture.allOf(origin, destination, fareFut).join();

			var fare = fareFut.get();

			fareDto.setAmount(fare.getAmount());
			fareDto.setCurrency(fare.getCurrency());
			fareDto.setOrigin(LocationMapper.INSTANCE.locationToDto(origin.get()));
			fareDto.setDestination(LocationMapper.INSTANCE.locationToDto(destination.get()));

		} catch (InterruptedException | ExecutionException e) {
			log.error("An error occurred while retriving fare values", e);
		}

		return fareDto;
	}

	@Async
	public CompletableFuture<Fare> getFare(String originCode, String destinationCode) {
		var URL = String.format("%s%s/%s/%s", travelApiProperties.getBaseUrl(), travelApiProperties.getFareEndPoint(),
				originCode, destinationCode);
		var fare = restTemplate.getForObject(URL, Fare.class);

		return CompletableFuture.completedFuture(fare);
	}
}
