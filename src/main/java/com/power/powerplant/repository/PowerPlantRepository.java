package com.power.powerplant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.power.powerplant.model.Battery;

@Repository
public interface PowerPlantRepository extends JpaRepository<Battery, Long>{

	List<Battery> findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqual(Integer start, Integer end);

}
