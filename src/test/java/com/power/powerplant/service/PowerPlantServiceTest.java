package com.power.powerplant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.power.powerplant.dto.BatteryDTO;
import com.power.powerplant.dto.ResponseDTO;
import com.power.powerplant.model.Battery;
import com.power.powerplant.repository.PowerPlantRepository;

class PowerPlantServiceTest extends MockitoExtension {
	
	@Mock
	PowerPlantRepository powerPlantRepository;
	
	PowerPlantService powerPlantService;
	
	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
	    powerPlantService = new PowerPlantService(powerPlantRepository);
	 }
	
	@Test
	@DisplayName("Test should pass when single battery is saved successfully")
	@Order(1)
	void shouldCreateBattery() {
		
		Battery battery = getBattery();
		BatteryDTO batteryDTO = getBatteryDTO();

		when(powerPlantRepository.saveAll(any())).thenReturn(List.of(battery));

		List<BatteryDTO> outputList = powerPlantService.createBatteries(List.of(batteryDTO));
		assertEquals(outputList.get(0).getName(), batteryDTO.getName());		
	}
	
	@Test
	@DisplayName("Test should pass when multiple batteries are saved successfully")
	@Order(2)
	void shouldCreateBatteries() {
		
		List<Battery> batteries = getBatteries();
		List<BatteryDTO> batteryDTOs = getBatteryDTOs();

		when(powerPlantRepository.saveAll(any())).thenReturn(batteries);

		List<BatteryDTO> outputList = powerPlantService.createBatteries(batteryDTOs);
		assertTrue(outputList.size() == batteryDTOs.size() && outputList.containsAll(batteryDTOs));		
	}
	
	@Test
	@DisplayName("Test should pass when single battery is retreived based on postcode range successfully")
	@Order(3)
	void shouldGetBatteriesByPostcodeRangeSingle() {
		Battery battery = getBattery();
		BatteryDTO batteryDTO = getBatteryDTO();

		when(powerPlantRepository
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(
						6000,6005))
		.thenReturn(List.of(battery));
		ResponseDTO responseDTO = powerPlantService.getBatteriesByPostcodeRange(6000,6005);
		assertEquals(responseDTO.getBatteries().get(0).getName(), batteryDTO.getName());
	}
	
	@Test
	@DisplayName("Test should pass when multiple batteries are retreived based on postcode range successfully")
	@Order(4)
	void shouldGetBatteriesByPostcodeRangeMultiple() {
		List<Battery> batteries = getBatteriesRange();
		List<BatteryDTO> batteryDTOs = getBatteryDTOsRange();

		when(powerPlantRepository
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(
						6000,7000))
		.thenReturn(batteries);
		ResponseDTO responseDTO = powerPlantService.getBatteriesByPostcodeRange(6000,7000);
		assertEquals(responseDTO.getBatteries(), batteryDTOs);
	}
	
	@Test
	@DisplayName("Test should pass when no batteries are retreived based on postcode range successfully")
	@Order(5)
	void shouldNotGetBatteriesByPostcodeRangeMultiple() {
				
		when(powerPlantRepository
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(
						2000,3000))
		.thenReturn(List.of());
		ResponseDTO responseDTO = powerPlantService.getBatteriesByPostcodeRange(2000,3000);
		assertTrue(responseDTO.getBatteries().isEmpty());
	}
	
	@Test
	@DisplayName("Test should pass when batteries are retreived based on postcode range and sorted alphabetically")
	@Order(6)
	void shouldGetBatteriesByPostcodeRangeSorted() {
		List<Battery> batteries = getBatteries();
		List<BatteryDTO> batteryDTOs = getBatteryDTOs();

		when(powerPlantRepository
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(
						2000,7000))
		.thenReturn(batteries);
		ResponseDTO responseDTO = powerPlantService.getBatteriesByPostcodeRange(2000,7000);
		
		assertEquals(responseDTO.getBatteries(), batteryDTOs);
	}
	
	@Test
	@DisplayName("Test should pass when batteries are retreived with correct total watt capacity")
	@Order(7)
	void shouldGetBatteriesByPostcodeRangeTotalWattCapacity() {
		List<Battery> batteries = getBatteries();
		
		Long expectedTotal = 1200L;
		
		when(powerPlantRepository
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(
						2000,7000))
		.thenReturn(batteries);
		ResponseDTO responseDTO = powerPlantService.getBatteriesByPostcodeRange(2000,7000);
		assertEquals(responseDTO.getTotalWattCapacity(), expectedTotal);
	}
	
	@Test
	@DisplayName("Test should pass when batteries are retreived with correct average watt capacity")
	@Order(8)
	void shouldGetBatteriesByPostcodeRangeAverageWattCapacity() {
		List<Battery> batteries = getBatteries();
		
		Double expectedAverage = 400.0;
		
		when(powerPlantRepository
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(
						2000,7000))
		.thenReturn(batteries);
		ResponseDTO responseDTO = powerPlantService.getBatteriesByPostcodeRange(2000,7000);
		assertEquals(responseDTO.getAverageWattCapacity(), expectedAverage);
	}
	
	@Test
	@DisplayName("Test should pass when response recieved with 0 total and average watt capacity")
	@Order(9)
	void shouldGetBatteriesByPostcodeRangeZeroAvgAndTotal() {
		
		ResponseDTO expectedresponseDTO = ResponseDTO.builder().averageWattCapacity(0.0).totalWattCapacity(0L).build();
				
		when(powerPlantRepository
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(
						2000,7000))
		.thenReturn(List.of());
		ResponseDTO responseDTO = powerPlantService.getBatteriesByPostcodeRange(2000,7000);
		assertEquals(expectedresponseDTO.getAverageWattCapacity(), responseDTO.getAverageWattCapacity());
		assertEquals(expectedresponseDTO.getTotalWattCapacity(), responseDTO.getTotalWattCapacity());
		
	}

	private List<Battery> getBatteriesRange() {
		List<Battery> batteryList = new ArrayList<Battery>();
		batteryList.add(Battery.builder().id(1002L).name("Duracell")
			        .postcode(6800).wattCapacity(100).build());
		batteryList.add(Battery.builder().id(1004L).name("Exide")
		        .postcode(6010).wattCapacity(700).build());
		return batteryList;
	}

	private List<BatteryDTO> getBatteryDTOsRange() {
		List<BatteryDTO> batteryDTOList = new ArrayList<BatteryDTO>();
		batteryDTOList.add(BatteryDTO.builder().name("Duracell")
			        .postcode(6800).wattCapacity(100).build());
		batteryDTOList.add(BatteryDTO.builder().name("Exide")
		        .postcode(6010).wattCapacity(700).build());
		return batteryDTOList;
	}

	private List<BatteryDTO> getBatteryDTOs() {
		List<BatteryDTO> batteryDTOList = new ArrayList<BatteryDTO>();
		batteryDTOList.add(BatteryDTO.builder().name("Duracell")
			        .postcode(6800).wattCapacity(100).build());
		batteryDTOList.add(BatteryDTO.builder().name("Energizer")
		        .postcode(5700).wattCapacity(500).build());
		batteryDTOList.add(BatteryDTO.builder().name("Exide")
		        .postcode(6010).wattCapacity(600).build());
		return batteryDTOList;
	}

	private List<Battery> getBatteries() {

		List<Battery> batteryList = new ArrayList<Battery>();
		batteryList.add(Battery.builder().id(1004L).name("Exide")
		        .postcode(6010).wattCapacity(600).build());
		batteryList.add(Battery.builder().id(1002L).name("Duracell")
			        .postcode(6800).wattCapacity(100).build());
		batteryList.add(Battery.builder().id(1003L).name("Energizer")
		        .postcode(5700).wattCapacity(500).build());
		
		return batteryList;
	}

	private BatteryDTO getBatteryDTO() {
		return BatteryDTO.builder().name("Nippon")
		        .postcode(6000).wattCapacity(1000).build();
	}

	private Battery getBattery() {
		return Battery.builder().id(1001L).name("Nippon")
			        .postcode(6000).wattCapacity(1000).build();
	}

}
