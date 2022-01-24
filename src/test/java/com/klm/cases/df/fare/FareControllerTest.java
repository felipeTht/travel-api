package com.klm.cases.df.fare;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klm.cases.df.location.LocationDto;
import com.klm.cases.df.metrics.MetricService;


@WebMvcTest(value = FareController.class)
public class FareControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private FareService fareService;
	
	@MockBean
	private MetricService metricService;

	@Test
	public void itShouldReturnLocationAndFare() throws Exception {
		FareDto fareDto = FareDto.builder().currency(Currency.EUR).amount(600)
				.origin(LocationDto.builder().code("FCO").build())
				.destination(LocationDto.builder().code("TXL").build()).build();
		when(fareService.calculate("FCO", "TXL")).thenReturn(fareDto);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/fares?origin=FCO&destination=TXL")).andDo(print())
				.andExpect(status().isOk()).andReturn();
		
		
		FareDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<FareDto>() {
				});

		assertThat(actual.getOrigin().getCode()).isEqualTo("FCO");
		assertThat(actual.getAmount()).isEqualTo(600);
		
	}
}
