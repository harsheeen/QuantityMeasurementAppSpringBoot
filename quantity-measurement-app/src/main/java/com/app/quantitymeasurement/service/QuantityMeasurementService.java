package com.app.quantitymeasurement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.app.quantitymeasurement.dto.QuantityResponseDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.Unit;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

@Service
public class QuantityMeasurementService {
	private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementService.class);

    @Autowired
    private QuantityMeasurementRepository repository;

    // =========================
    // 🔹 CONVERT
    // =========================
    public QuantityResponseDTO convert(QuantityRequestDTO dto) {

        Unit inputUnit = Unit.valueOf(dto.getInputUnit().toUpperCase());
        Unit targetUnit = Unit.valueOf(dto.getTargetUnit().toUpperCase());

        validateUnits(inputUnit, targetUnit);

        double base = inputUnit.toBase(dto.getInputValue());
        double result = targetUnit.fromBase(base);
        
        logger.info("Successfully converted data");

        return buildResponse(dto, result, targetUnit.name(), "CONVERT");
    }

    // =========================
    // 🔹 COMPARE
    // =========================
    public QuantityResponseDTO compare(QuantityRequestDTO dto) {

    	Unit inputUnit;
    	Unit targetUnit;

    	try {
    	    inputUnit = Unit.valueOf(dto.getInputUnit().toUpperCase());
    	    targetUnit = Unit.valueOf(dto.getTargetUnit().toUpperCase());
    	} catch (IllegalArgumentException e) {
    	    throw new RuntimeException("Invalid unit provided");
    	}

        validateUnits(inputUnit, targetUnit);

        double v1 = inputUnit.toBase(dto.getInputValue());
        double v2 = targetUnit.toBase(dto.getTargetValue());

        boolean isEqual = Math.abs(v1 - v2) < 0.0001;
        
        logger.info("Successfully compared data");
        
        return buildResponse(dto, isEqual ? 1 : 0, "BOOLEAN", "COMPARE");
    }

    // =========================
    // 🔹 ADD
    // =========================
    public QuantityResponseDTO add(QuantityRequestDTO dto) {

        Unit inputUnit = Unit.valueOf(dto.getInputUnit().toUpperCase());
        Unit targetUnit = Unit.valueOf(dto.getTargetUnit().toUpperCase());

        validateUnits(inputUnit, targetUnit);

        double sum = inputUnit.toBase(dto.getInputValue())
                + targetUnit.toBase(dto.getTargetValue());

        double result = targetUnit.fromBase(sum);
        
        logger.info("Successfully added data");

        return buildResponse(dto, result, targetUnit.name(), "ADD");
    }

    // =========================
    // 🔹 SUBTRACT
    // =========================
    public QuantityResponseDTO subtract(QuantityRequestDTO dto) {

        Unit inputUnit = Unit.valueOf(dto.getInputUnit().toUpperCase());
        Unit targetUnit = Unit.valueOf(dto.getTargetUnit().toUpperCase());

        validateUnits(inputUnit, targetUnit);

        double diff = inputUnit.toBase(dto.getInputValue())
                - targetUnit.toBase(dto.getTargetValue());

        double result = targetUnit.fromBase(diff);
        
        logger.info("Successfully subtracted data");

        return buildResponse(dto, result, targetUnit.name(), "SUBTRACT");
    }

    // =========================
    // 🔹 COMMON VALIDATION
    // =========================
    private void validateUnits(Unit input, Unit target) {
        if (input.getType() != target.getType()) {
            throw new RuntimeException("Incompatible unit types");
        }
    }

    // =========================
    // 🔹 COMMON RESPONSE BUILDER
    // =========================
    private QuantityResponseDTO buildResponse(
            QuantityRequestDTO dto,
            double resultValue,
            String resultUnit,
            String operation) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setInputValue(dto.getInputValue());
        entity.setInputUnit(dto.getInputUnit());
        entity.setInputMeasurementType(dto.getInputMeasurementType());

        entity.setTargetValue(dto.getTargetValue());
        entity.setTargetUnit(dto.getTargetUnit());
        entity.setTargetMeasurementType(dto.getTargetMeasurementType());

        entity.setResultValue(resultValue);
        entity.setResultUnit(resultUnit);
        entity.setOperation(operation);
        entity.setError(false);

        QuantityMeasurementEntity saved = repository.save(entity);

        return mapToResponse(saved);
    }

    // =========================
    // 🔹 GET APIs
    // =========================
    public List<QuantityResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<QuantityResponseDTO> getByOperation(String operation) {
        return repository.findByOperation(operation)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<QuantityResponseDTO> getErrors() {
        return repository.findByErrorTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // 🔹 ENTITY → DTO
    // =========================
    private QuantityResponseDTO mapToResponse(QuantityMeasurementEntity saved) {

        QuantityResponseDTO response = new QuantityResponseDTO();

        response.setId(saved.getId());
        response.setInputValue(saved.getInputValue());
        response.setInputUnit(saved.getInputUnit());
        response.setResultValue(saved.getResultValue());
        response.setResultUnit(saved.getResultUnit());
        response.setOperation(saved.getOperation());
        response.setError(saved.isError());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }

    // =========================
    // 🔹 DTO FLOW 
    // =========================
    public QuantityMeasurementDTO convert(QuantityInputDTO input) {

        QuantityMeasurementDTO response = new QuantityMeasurementDTO();

        try {
            Unit from = Unit.valueOf(input.getThisQuantityDTO().getUnit().toUpperCase());
            Unit to = Unit.valueOf(input.getThatQuantityDTO().getUnit().toUpperCase());

            double base = from.toBase(input.getThisQuantityDTO().getValue());
            double result = to.fromBase(base);

            response.setResultValue(result);
            response.setResultUnit(to.name());
            response.setOperation("CONVERT");
            response.setError(false);

        } catch (Exception e) {
            response.setError(true);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
    
    public QuantityResponseDTO addWithTargetUnit(QuantityRequestDTO dto) {

        Unit u1 = Unit.valueOf(dto.getInputUnit().toUpperCase());
        Unit u2 = Unit.valueOf(dto.getTargetUnit().toUpperCase());

        validateUnits(u1, u2);

        double sum = u1.toBase(dto.getInputValue())
                   + u2.toBase(dto.getTargetValue());

        double result = u2.fromBase(sum);

        return buildResponse(dto, result, u2.name(), "ADD_WITH_TARGET_UNIT");
    }
    
    public QuantityResponseDTO subtractWithTargetUnit(QuantityRequestDTO dto) {

        Unit u1 = Unit.valueOf(dto.getInputUnit().toUpperCase());
        Unit u2 = Unit.valueOf(dto.getTargetUnit().toUpperCase());

        validateUnits(u1, u2);

        double diff = u1.toBase(dto.getInputValue())
                    - u2.toBase(dto.getTargetValue());

        double result = u2.fromBase(diff);

        return buildResponse(dto, result, u2.name(), "SUBTRACT_WITH_TARGET_UNIT");
    }
    
    public QuantityResponseDTO divide(QuantityRequestDTO dto) {

        Unit u1 = Unit.valueOf(dto.getInputUnit().toUpperCase());
        Unit u2 = Unit.valueOf(dto.getTargetUnit().toUpperCase());

        validateUnits(u1, u2);

        double v1 = u1.toBase(dto.getInputValue());
        double v2 = u2.toBase(dto.getTargetValue());

        if (v2 == 0) {
            throw new RuntimeException("Cannot divide by zero");
        }

        double result = v1 / v2;

        return buildResponse(dto, result, "SCALAR", "DIVIDE");
    }
    
    public List<QuantityResponseDTO> getByType(String type) {

        return repository.findAll()
                .stream()
                .filter(e -> e.getInputMeasurementType().equalsIgnoreCase(type))
                .map(this::mapToResponse)
                .toList();
    }
    
    public Long getCount(String operation) {
        return (long) repository.findByOperation(operation).size();
    }
}