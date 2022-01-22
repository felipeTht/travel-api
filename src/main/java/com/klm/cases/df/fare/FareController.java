package com.klm.cases.df.fare;

import org.springframework.beans.factory.annotation.Autowired;
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
public class FareController {
	
	@Autowired
	private FareService fareService;

	@GetMapping("/fares")
	public ResponseEntity<FareDto> find(@RequestParam("origin") final String origin,
			@RequestParam("destination") final String destination) {
		
		return new ResponseEntity<FareDto>(fareService.calculate(origin, destination), null, HttpStatus.OK);
	}

}
