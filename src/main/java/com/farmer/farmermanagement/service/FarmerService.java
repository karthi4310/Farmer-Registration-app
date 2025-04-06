package com.farmer.farmermanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmer.farmermanagement.dto.FarmerDto;
import com.farmer.farmermanagement.entity.Farmer;
import com.farmer.farmermanagement.exception.FarmerNotFoundException;
import com.farmer.farmermanagement.mapper.AddressMapper;
import com.farmer.farmermanagement.mapper.BankDetailsMapper;
import com.farmer.farmermanagement.mapper.CropMapper;
import com.farmer.farmermanagement.mapper.FarmerMapper;
import com.farmer.farmermanagement.mapper.LandDetailsMapper;
import com.farmer.farmermanagement.repository.FarmerRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FarmerService {

	private final FarmerRepository farmerRepository;
	private final FarmerMapper farmerMapper;
	private final CropMapper cropMapper;
	private final AddressMapper addressMapper;
	private final BankDetailsMapper bankDetailsMapper;
	private final LandDetailsMapper landDetailsMapper;

	@Transactional
	public FarmerDto createFarmer(FarmerDto farmerDto) {
		Farmer farmer = farmerMapper.toEntity(farmerDto);

		// Back-references for bi-directional relationships
		if (farmer.getCrops() != null) {
			farmer.getCrops().forEach(crop -> crop.setFarmer(farmer));
		}

		if (farmer.getLandDetails() != null) {
			farmer.getLandDetails().setFarmer(farmer);
		}

		if (farmer.getBankDetails() != null) {
			farmer.getBankDetails().setFarmer(farmer);
		}

		Farmer savedFarmer = farmerRepository.save(farmer);
		return farmerMapper.toDto(savedFarmer);
	}

	@Transactional
	public FarmerDto updateFarmer(Long id, FarmerDto farmerDto) {
		Farmer existing = farmerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Farmer not found with ID: " + id));

		// Manually update fields (you can extract this to a helper if needed)
		existing.setFirstName(farmerDto.getFirstName());
		existing.setMiddleName(farmerDto.getMiddleName());
		existing.setLastName(farmerDto.getLastName());
		existing.setPhoneNumber(farmerDto.getPhoneNumber());
		existing.setDateOfBirth(farmerDto.getDateOfBirth());
		existing.setGender(farmerDto.getGender());
		existing.setEducation(farmerDto.getEducation());
		existing.setDocument(farmerDto.getDocument());
		existing.setPortalAccess(farmerDto.getPortalAccess());
		existing.setPortalRole(farmerDto.getPortalRole());
		existing.setFarmerType(farmerDto.getFarmerType());

		// Update embeddables directly
		existing.setAddress(addressMapper.toEntity(farmerDto.getAddress()));
		existing.setBankDetails(bankDetailsMapper.toEntity(farmerDto.getBankDetails()));
		existing.setLandDetails(landDetailsMapper.toEntity(farmerDto.getLandDetails()));

		// Handle crops with back-reference
		if (farmerDto.getCrops() != null) {
			existing.getCrops();
			existing.getCrops().addAll(farmerDto.getCrops().stream().map(cropDto -> {
				var crop = cropMapper.toEntity(cropDto);
				crop.setFarmer(existing); // maintain bidirectional link
				return crop;
			}).toList());
		}

		Farmer saved = farmerRepository.save(existing);
		return farmerMapper.toDto(saved);
	}

	@Transactional
	public List<FarmerDto> getAllFarmers() {
		return farmerRepository.findAll().stream().map(farmerMapper::toDto).collect(Collectors.toList());
	}

	@Transactional
	public FarmerDto getFarmerById(Long id) {
		Farmer farmer = farmerRepository.findById(id)
				.orElseThrow(() -> new FarmerNotFoundException("Farmer not found with ID: " + id));
		return farmerMapper.toDto(farmer);
	}

	@Transactional
	public void deleteFarmer(Long id) {
		if (!farmerRepository.existsById(id)) {
			throw new EntityNotFoundException("Farmer not found with ID: " + id);
		}
		farmerRepository.deleteById(id);
	}
}
