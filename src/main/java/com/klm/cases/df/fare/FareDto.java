package com.klm.cases.df.fare;

import com.klm.cases.df.location.LocationDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FareDto {
	private LocationDto origin;
	private LocationDto destination;
	private double amount;
	private Currency currency;
}
