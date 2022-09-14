package com.power.powerplant.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatteryDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private Integer postcode;
	
	@NotBlank
	private Integer wattCapacity;

}
