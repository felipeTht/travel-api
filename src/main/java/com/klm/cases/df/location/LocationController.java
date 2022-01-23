package com.klm.cases.df.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class LocationController {
	
	@Autowired
	private LocationService locationsevice;
		
	@GetMapping("/locations")
	public ResponseEntity<List<LocationDto>> find(@RequestParam("term") final String term) {		
		return new ResponseEntity<List<LocationDto>>(locationsevice.find(term), null, HttpStatus.OK);
	}
	
	@GetMapping("/locations/paginated")
	public ResponseEntity<PagedModel<LocationDto>> paginate(@RequestParam("term") final String term,
													  @RequestParam("page") final int page,
													  @RequestParam("size") final int size) {		
		return new ResponseEntity<PagedModel<LocationDto>>(locationsevice.paginate(term, page, size), null, HttpStatus.OK);
	}
}
