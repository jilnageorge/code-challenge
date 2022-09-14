package com.power.powerplant.controller;

import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power.powerplant.PowerPlantApplicationTests;
import com.power.powerplant.dto.BatteryDTO;
import com.power.powerplant.dto.ResponseDTO;
import com.power.powerplant.service.PowerPlantService;

class PowerPlantControllerTest extends PowerPlantApplicationTests{
	
	MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@MockBean
	PowerPlantService powerPlantService;
	
	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders
		        .webAppContextSetup(this.webApplicationContext)
		        .build();
	 }
	
	@Test
	@DisplayName("Test should pass when batteries are saved successfully")
	void shouldCreateBatteries() throws JsonProcessingException, Exception {
		
		List<BatteryDTO> batteries = getBatteryDTOs();
	    when(powerPlantService.createBatteries(batteries)).thenReturn(batteries);

	    mockMvc.perform(MockMvcRequestBuilders.post("/batteries")
	                .content(new ObjectMapper().writeValueAsString((batteries)))
	                .characterEncoding(StandardCharsets.UTF_8.displayName())
	                .contentType(MediaType.APPLICATION_JSON))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	  @Test
	  @DisplayName("Test should pass when batteries are retreived based on postcode range successfully")
	  void shouldGetBatteriesByPostcodeRange() throws Exception {
	    ResponseDTO responseDTO = ResponseDTO.builder().batteries(getBatteryDTOs())
	    		.averageWattCapacity(400.0).totalWattCapacity(1200L).build();
	    when(powerPlantService.getBatteriesByPostcodeRange(2000, 7000)).thenReturn(responseDTO);

	    mockMvc.perform(
	    		MockMvcRequestBuilders.get("/batteries/postcode/start/{start}/end/{end}", 2000, 7000)
	                .characterEncoding(StandardCharsets.UTF_8.displayName())
	                .contentType(MediaType.APPLICATION_JSON))
	        .andDo(MockMvcResultHandlers.print())
	        .andExpect(MockMvcResultMatchers.status().isOk());
	  }
	
	private List<BatteryDTO> getBatteryDTOs() {
		List<BatteryDTO> batteryDTOList = new ArrayList<BatteryDTO>();
		batteryDTOList.add(BatteryDTO.builder().name("Duracell")
			        .postcode(6800).wattCapacity(100).build());
		batteryDTOList.add(BatteryDTO.builder().name("Energizer")
		        .postcode(5700).wattCapacity(500).build());
		batteryDTOList.add(BatteryDTO.builder().name("Exide")
		        .postcode(6010).wattCapacity(700).build());
		return batteryDTOList;
	}

}
