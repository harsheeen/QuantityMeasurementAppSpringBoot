package com.app.quantitymeasurement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.app.quantitymeasurement.dto.QuantityResponseDTO;
import com.app.quantitymeasurement.service.QuantityMeasurementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quantity")
public class QuantityMeasurementController {

    @Autowired
    private QuantityMeasurementService service;

    @PostMapping("/convert")
    public QuantityResponseDTO convert(@RequestBody QuantityRequestDTO dto) {
        return service.convert(dto);
    }

    @PostMapping("/compare")
    public QuantityResponseDTO compare(@RequestBody QuantityRequestDTO dto) {
        return service.compare(dto);
    }

    @PostMapping("/add")
    public QuantityResponseDTO add(@RequestBody QuantityRequestDTO dto) {
        return service.add(dto);
    }

    @PostMapping("/subtract")
    public QuantityResponseDTO subtract(@RequestBody QuantityRequestDTO dto) {
        return service.subtract(dto);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hi from Jenkins CI/CD Deployment!";
    }

    @GetMapping("/all")
    public List<QuantityResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/operation/{operation}")
    public List<QuantityResponseDTO> getByOperation(@PathVariable String operation) {
        return service.getByOperation(operation);
    }

    @GetMapping("/errors")
    public List<QuantityResponseDTO> getErrors() {
        return service.getErrors();
    }
    
 // ================= ADD WITH TARGET UNIT =================
    @PostMapping("/add-with-target-unit")
    public QuantityResponseDTO addWithTargetUnit(@RequestBody QuantityRequestDTO dto) {
        return service.addWithTargetUnit(dto);
    }

    // ================= SUBTRACT WITH TARGET UNIT =================
    @PostMapping("/subtract-with-target-unit")
    public QuantityResponseDTO subtractWithTargetUnit(@RequestBody QuantityRequestDTO dto) {
        return service.subtractWithTargetUnit(dto);
    }

    // ================= DIVIDE =================
    @PostMapping("/divide")
    public QuantityResponseDTO divide(@RequestBody QuantityRequestDTO dto) {
        return service.divide(dto);
    }

    // ================= HISTORY BY TYPE =================
    @GetMapping("/history/type/{type}")
    public List<QuantityResponseDTO> getByType(@PathVariable String type) {
        return service.getByType(type);
    }

    // ================= COUNT =================
    @GetMapping("/count/{operation}")
    public Long getCount(@PathVariable String operation) {
        return service.getCount(operation);
    }

    // Optional flow
//    @PostMapping("/v1/convert")
//    public QuantityMeasurementDTO convertV1(@RequestBody QuantityInputDTO input) {
//        return service.convert(input);
//    }
}
