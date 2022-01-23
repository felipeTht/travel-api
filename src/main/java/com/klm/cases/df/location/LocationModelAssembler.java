package com.klm.cases.df.location;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;

@Service
public class LocationModelAssembler implements RepresentationModelAssembler<Location, LocationDto> {

	@Override
	public LocationDto toModel(Location entity) {
		return LocationMapper.INSTANCE.locationToDto(entity);
	}
	

}
