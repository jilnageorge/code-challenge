package com.power.powerplant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.power.powerplant.api.PowerPlantApi;
import com.power.powerplant.dto.BatteryDTO;
import com.power.powerplant.dto.ResponseDTO;
import com.power.powerplant.service.PowerPlantService;

@RestController
public class PowerPlantController implements PowerPlantApi{

	@Autowired
	PowerPlantService powerPlantService;
	
	@Override
	public List<BatteryDTO> createBatteries(List<BatteryDTO> batteries) {
		
		return powerPlantService.createBatteries(batteries);
	}

	@Override
	public ResponseDTO getBatteriesByPostcodeRange(Integer start, Integer end) {
		return powerPlantService.getBatteriesByPostcodeRange(start, end);
	}
	

}
