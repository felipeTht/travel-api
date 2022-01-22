package com.klm.cases.df.location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = LocationController.class)
public class LocationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private LocationService locationService;

	@Test
	public void itShouldReturnListWithLocationsIfLocationFounds() throws Exception {
		List<LocationDto> locations = new ArrayList<LocationDto>();
		locations.add(LocationDto.builder().code("FCO").build());

		when(locationService.find("rome")).thenReturn(locations);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/locations?term=rome")).andDo(print())
				.andExpect(status().isOk()).andReturn();

		List<LocationDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<LocationDto>>() {
				});

		assertThat(actual.size()).isGreaterThan(0);

		assertThat(actual.get(0).getCode()).isEqualTo(locations.get(0).getCode());

	}

	@Test
	public void itShouldReturn404IflocationNotFound() throws Exception {

		when(locationService.find("tokio")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/locations?term=tokio")).andDo(print())
				.andExpect(status().isNotFound());
	}

}
