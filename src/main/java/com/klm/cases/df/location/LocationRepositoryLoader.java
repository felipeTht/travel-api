package com.klm.cases.df.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocationRepositoryLoader {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationRepository locationRepository;
	
	
	@PostConstruct
	public void loadDataAfterStartup() {
		log.info("Load Locations");
		loadDataFromExternalService();
	}
	
	@Scheduled(cron = "0 0 0/4 1/1 * ?")
	public void loadDataScheduled() {
		log.info("Load scheduled service for locations");
		loadDataFromExternalService();
	}
	
	private void loadDataFromExternalService() {
		log.info("Check if service is up");
		if (locationService.isLocationsApiRunning()) {
			
			log.info("Delete all data from DB");
			
			locationRepository.deleteAll();
			
			long page = 1;
			long maxPage = 0;
			
			var locations = locationService.getLocations(null, 1);
			try {
				ResponseEntity<PagedModel<EntityModel<Location>>> responseEntity = locations.get();
				maxPage = responseEntity.getBody().getMetadata().getTotalPages();
				
				saveLocations(responseEntity);
				
				locationRepository.saveAll(responseEntity.getBody().getContent().stream().map(resource -> resource.getContent()).toList());
				
			} catch (InterruptedException | ExecutionException e) {
				log.error("An error occured while get first page of locations", e);
			} 
			
			List<CompletableFuture<ResponseEntity<PagedModel<EntityModel<Location>>>>> completableList = new ArrayList<>();
			
			while (page < maxPage) {
				page++;
				completableList.add(locationService.getLocations(null, page));
			}
			
			CompletableFuture<List<ResponseEntity<PagedModel<EntityModel<Location>>>>> list = allOfCompletables(completableList);
			
			try {
				
				log.info("Call all locations from API");
				List<ResponseEntity<PagedModel<EntityModel<Location>>>> responses = list.get();
				log.info("Save all locations to DB");
				responses.parallelStream().forEach(response -> saveLocations(response));
				
			} catch (InterruptedException | ExecutionException e) {
				log.error("An error occured while readind all responses", e);
			}
		}
		
	}
	
	private <T> CompletableFuture<List<T>> allOfCompletables(List<CompletableFuture<T>> compleTableList) {
		CompletableFuture<Void> allFuturesResult = CompletableFuture.allOf(compleTableList.toArray(new CompletableFuture[compleTableList.size()]));
		return allFuturesResult.thenApply(v ->
		compleTableList.stream().
                map(future -> future.join()).            
                collect(Collectors.<T>toList()));
	}
	
	
	private void saveLocations(ResponseEntity<PagedModel<EntityModel<Location>>> responseEntity) {
		if (!Objects.isNull(responseEntity) && !Objects.isNull(responseEntity.getBody()) && !Objects.isNull(responseEntity.getBody().getContent())) {
			locationRepository.saveAll(responseEntity.getBody().getContent().stream().map(resource -> resource.getContent()).toList());
		}
		
	}
}
