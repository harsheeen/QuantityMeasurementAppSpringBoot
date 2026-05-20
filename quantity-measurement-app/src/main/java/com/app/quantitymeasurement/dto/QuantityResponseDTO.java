package com.app.quantitymeasurement.dto;

import java.time.LocalDateTime;

public class QuantityResponseDTO {

    private Long id;

    private double inputValue;
    private String inputUnit;

    private double resultValue;
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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	private String resultUnit;

    private String operation;

    private boolean error;
    private String errorMessage;

    private LocalDateTime createdAt;
}
