package com.klm.cases.df.location;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.klm.cases.df.config.TravelApiProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocationService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TravelApiProperties travelApiProperties;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private PagedResourcesAssembler<Location> pagedResourcesAssembler;

	@Autowired
	private LocationModelAssembler locationModelAssembler;

	private final String[] acceptedColumns = { "code", "name", "description" };

	public List<LocationDto> find(String term) {

		var URL = String.format("%s%s?term={term}", travelApiProperties.getBaseUrl(),
				travelApiProperties.getAirportsEndpoint());

		HashMap<String, Object> values = new HashMap<>();
		values.put("term", term);

		log.info("Calling service");

		var locations = restTemplate.exchange(URL, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedModel<EntityModel<Location>>>() {
				}, values);

		log.info("Response");

		PagedModel<EntityModel<Location>> body = locations.getBody();
		List<LocationDto> list = body.getContent().stream()
				.map(resource -> LocationMapper.INSTANCE.locationToDto(resource.getContent()))
				.sorted((location1, location2) -> location1.getDescription().compareTo(location2.getDescription()))
				.toList();

		log.info("List of elements" + list.size());

		return list;
	}

	@Async
	public CompletableFuture<Location> getLocation(String code) {
		var url = String.format("%s%s/%s", travelApiProperties.getBaseUrl(), travelApiProperties.getAirportsEndpoint(),
				code);
		Location location = restTemplate.getForObject(url, Location.class);

		return CompletableFuture.completedFuture(location);
	}

	@Async
	public CompletableFuture<ResponseEntity<PagedModel<EntityModel<Location>>>> getLocations(String term, long page) {
		var URL = String.format("%s%s?term={term}&page={page}", travelApiProperties.getBaseUrl(),
				travelApiProperties.getAirportsEndpoint());
		HashMap<String, Object> values = new HashMap<>();
		values.put("term", term);
		values.put("page", page);

		var locations = restTemplate.exchange(URL, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedModel<EntityModel<Location>>>() {
				}, values);

		return CompletableFuture.completedFuture(locations);
	}

	public boolean isLocationsApiRunning() {
		var URL = String.format("%s%s", travelApiProperties.getBaseUrl(), travelApiProperties.getAirportsEndpoint());

		ResponseEntity<Object> exchange;

		try {
			exchange = restTemplate.exchange(URL, HttpMethod.GET, null, Object.class);

		} catch (RestClientException e) {
			log.error("Mock Service is down");
			return false;
		}

		return exchange.getStatusCode().equals(HttpStatus.OK);
	}

	public PagedModel<LocationDto> paginate(String term, int page, int size, String sort, String direction) {

		String sortColumn = Arrays.asList(acceptedColumns).contains(sort) ? sort : "description";
		Direction sortDirection = direction.toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortColumn));
		Page<Location> list;

		if (StringUtils.hasText(term)) {
			list = locationRepository.findByDescriptionContainingIgnoreCase(term, pageable);
		} else {
			list = locationRepository.findAll(pageable);
		}

		return pagedResourcesAssembler.toModel(list, locationModelAssembler);

	}

}
