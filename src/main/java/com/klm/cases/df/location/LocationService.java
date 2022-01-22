package com.klm.cases.df.location;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.klm.cases.df.config.TravelApiProperties;

@Service
public class LocationService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TravelApiProperties travelApiProperties;

	public List<LocationDto> find(String term) {

		var URL = String.format("%s%s?term={term}", travelApiProperties.getBaseUrl(),
				travelApiProperties.getAirportsEndpoint());

		HashMap<String, Object> values = new HashMap<>();
		values.put("term", term);

		var locations = restTemplate.exchange(URL, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedModel<EntityModel<Location>>>() {
				}, values);
		
		PagedModel<EntityModel<Location>> body = locations.getBody();
		List<LocationDto> list = body.getContent()
									.stream()
									.map(resource -> LocationMapper.INSTANCE.locationToDto(resource.getContent()))
									.sorted((location1, location2) -> location1.getDescription().compareTo(location2.getDescription())).toList();

		return list;
	}

	@Async
	public CompletableFuture<Location> getLocation(String code) {
		var url = String.format("%s%s/%s", travelApiProperties.getBaseUrl(), travelApiProperties.getAirportsEndpoint(),
				code);
		Location location = restTemplate.getForObject(url, Location.class);

		return CompletableFuture.completedFuture(location);
	}

}
