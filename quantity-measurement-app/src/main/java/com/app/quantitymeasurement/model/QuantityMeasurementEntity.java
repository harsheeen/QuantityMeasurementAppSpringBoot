package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements")
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getInputValue() {
		return inputValue;
	}

	public void setInputValue(double inputValue) {
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

	public double getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(double targetValue) {
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

	public double getResultValue() {
		return resultValue;
	}

	public void setResultValue(double resultValue) {
		this.resultValue = resultValue;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // INPUT
    private double inputValue;
    private String inputUnit;
    private String inputMeasurementType;

    private double targetValue;
    private String targetUnit;
    private String targetMeasurementType;

    // OPERATION
    private String operation;

    // RESULT
    private double resultValue;
    private String resultUnit;

    // STATUS
    private boolean error;
    private String errorMessage;

    // TIMESTAMPS
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Lifecycle Hooks
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); // ensures both set initially
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}