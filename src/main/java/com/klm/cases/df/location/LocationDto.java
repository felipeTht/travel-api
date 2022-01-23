package com.klm.cases.df.location;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Relation(collectionRelation = "locations")
@EqualsAndHashCode(callSuper = false)
public class LocationDto extends RepresentationModel<LocationDto> {
	
	private String code;
	private String name;
	private String description;
}
