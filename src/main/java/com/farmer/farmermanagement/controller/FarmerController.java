package com.farmer.farmermanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmer.farmermanagement.dto.FarmerDto;
import com.farmer.farmermanagement.service.FarmerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

	private final FarmerService farmerService;

	@PostMapping
	public ResponseEntity<FarmerDto> createFarmer(@Valid @RequestBody FarmerDto farmerDto) {
		return ResponseEntity.ok(farmerService.createFarmer(farmerDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<FarmerDto> updateFarmer(@PathVariable Long id, @Valid @RequestBody FarmerDto farmerDto) {
		return ResponseEntity.ok(farmerService.updateFarmer(id, farmerDto));
	}

	@GetMapping
	public ResponseEntity<List<FarmerDto>> getAllFarmers() {
		return ResponseEntity.ok(farmerService.getAllFarmers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<FarmerDto> getFarmerById(@PathVariable Long id) {
		return ResponseEntity.ok(farmerService.getFarmerById(id));
	}

}
