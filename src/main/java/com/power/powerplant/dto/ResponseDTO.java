package com.power.powerplant.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
	
	@NotBlank
	private List<BatteryDTO> batteries;
	
	@NotBlank
	private Long totalWattCapacity;
	
	@NotBlank
	private Double averageWattCapacity;

}
