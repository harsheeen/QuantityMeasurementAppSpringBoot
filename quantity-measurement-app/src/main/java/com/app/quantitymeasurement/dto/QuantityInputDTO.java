package com.app.quantitymeasurement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class QuantityInputDTO {

    @Valid
    @NotNull(message = "First quantity cannot be null")
    private QuantityDTO thisQuantityDTO;

    @Valid
    @NotNull(message = "Second quantity cannot be null")
    private QuantityDTO thatQuantityDTO;

    private QuantityDTO targetQuantityDTO;

    public QuantityDTO getThisQuantityDTO() {
        return thisQuantityDTO;
    }

    public void setThisQuantityDTO(QuantityDTO thisQuantityDTO) {
        this.thisQuantityDTO = thisQuantityDTO;
    }

    public QuantityDTO getThatQuantityDTO() {
        return thatQuantityDTO;
    }

    public void setThatQuantityDTO(QuantityDTO thatQuantityDTO) {
        this.thatQuantityDTO = thatQuantityDTO;
    }

    public QuantityDTO getTargetQuantityDTO() {
        return targetQuantityDTO;
    }

    public void setTargetQuantityDTO(QuantityDTO targetQuantityDTO) {
        this.targetQuantityDTO = targetQuantityDTO;
    }
}