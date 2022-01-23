package com.klm.cases.df.location;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Locations", indexes = @Index(columnList = "description"))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	@Id
	private String code;
	private String name;
	private String description;
	@Transient
	private Coordinates coordinates;
	@Transient
	private Location parent;
	@Transient
	private Set<Location> children;

}
