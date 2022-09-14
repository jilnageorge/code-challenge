package com.power.powerplant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.power.powerplant.dto.BatteryDTO;
import com.power.powerplant.dto.ResponseDTO;
import com.power.powerplant.dto.ResponseDTO.ResponseDTOBuilder;
import com.power.powerplant.model.Battery;
import com.power.powerplant.repository.PowerPlantRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PowerPlantService {
	
	@Autowired
	PowerPlantRepository powerPlantRepository;

	public List<BatteryDTO> createBatteries(List<BatteryDTO> batteries) {
		
		List<Battery> batteryEntities =  powerPlantRepository.saveAll(batteries.stream().map(x -> 
		Battery.builder().name(x.getName()).postcode(x.getPostcode()).wattCapacity(x.getWattCapacity()).build()).toList());
		
		return batteryEntities.stream().map(x -> 
		BatteryDTO.builder().name(x.getName()).postcode(x.getPostcode()).wattCapacity(x.getWattCapacity()).build()).toList();
	}

	public ResponseDTO getBatteriesByPostcodeRange(Integer start, Integer end) {
		
		List<Battery> batteryEntities = powerPlantRepository.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(start, end);
		
		ResponseDTOBuilder responseDTOBuilder = ResponseDTO.builder();
		responseDTOBuilder.batteries(batteryEntities.stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
				.map(x -> BatteryDTO.builder().name(x.getName()).postcode(x.getPostcode()).wattCapacity(x.getWattCapacity()).build()).toList());
		
		responseDTOBuilder.averageWattCapacity(batteryEntities.stream().mapToInt(Battery :: getWattCapacity).average().orElse(0.0));
		responseDTOBuilder.totalWattCapacity(batteryEntities.stream().mapToLong(Battery :: getWattCapacity).sum());
		
		return responseDTOBuilder.build();
	}

}
