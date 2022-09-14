package com.power.powerplant.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.power.powerplant.dto.BatteryDTO;
import com.power.powerplant.dto.ResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PowerPlant APIs")
public interface PowerPlantApi {
	
	@PostMapping("/batteries")
	@Operation(summary = "Create batteries",
     		   description = "This API accepts list of batteries each containing name,postcode and wattcapacity.")
	@ApiResponse(
		      responseCode = "200",
		      description = "The response contains the details of the saved batteries.",
		      content = @Content(schema = @Schema(implementation = BatteryDTO.class)))
	List<BatteryDTO> createBatteries(@RequestBody List<BatteryDTO> batteries);
	
	@GetMapping("batteries/postcode/start/{start}/end/{end}")
	  @Operation(summary = "Get batteries by postcode range",
	      description = "This API accepts a postcode range and returns the list of batteries with statistics")
	  @ApiResponse(
	      responseCode = "200",
	      description = "The response contains list of batteries in the postcode range sorted alphabetically along with statistics",
	      content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
	  ResponseDTO getBatteriesByPostcodeRange(@PathVariable Integer start, @PathVariable Integer end);

}
