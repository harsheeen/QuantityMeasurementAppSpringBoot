package com.app.quantitymeasurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuantityRequestDTO {

    @NotNull
    private Double inputValue;

    public Double getInputValue() {
		return inputValue;
	}

	public void setInputValue(Double inputValue) {
		this.inputValue = inputValue;
	}

	public String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	public String getInputMeasurementType() {
		return inputMeasurementType;
	}

	public void setInputMeasurementType(String inputMeasurementType) {
		this.inputMeasurementType = inputMeasurementType;
	}

	public Double getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(Double targetValue) {
		this.targetValue = targetValue;
	}

	public String getTargetUnit() {
		return targetUnit;
	}

	public void setTargetUnit(String targetUnit) {
		this.targetUnit = targetUnit;
	}

	public String getTargetMeasurementType() {
		return targetMeasurementType;
	}

	public void setTargetMeasurementType(String targetMeasurementType) {
		this.targetMeasurementType = targetMeasurementType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@NotBlank
    private String inputUnit;

    @NotBlank
    private String inputMeasurementType;

    private Double targetValue;

    @NotBlank
    private String targetUnit;

    @NotBlank
    private String targetMeasurementType;

    @NotBlank
    private String operation;
}