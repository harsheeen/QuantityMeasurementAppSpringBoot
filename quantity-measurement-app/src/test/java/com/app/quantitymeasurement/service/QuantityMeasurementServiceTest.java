package com.app.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.quantitymeasurement.dto.QuantityRequestDTO;
import com.app.quantitymeasurement.dto.QuantityResponseDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

@ExtendWith(MockitoExtension.class)
public class QuantityMeasurementServiceTest {

    @Mock
    private QuantityMeasurementRepository repository;

    @InjectMocks
    private QuantityMeasurementService service;

    @Test
    void testConvertFeetToInches() {

        QuantityRequestDTO dto = new QuantityRequestDTO();
        dto.setInputValue(1.0);
        dto.setInputUnit("FEET");
        dto.setInputMeasurementType("LENGTH");
        dto.setTargetUnit("INCHES");
        dto.setTargetMeasurementType("LENGTH");

        when(repository.save(any(QuantityMeasurementEntity.class)))
                .thenAnswer(invocation -> {
                    QuantityMeasurementEntity e = invocation.getArgument(0);
                    e.setId(1L);
                    return e;
                });

        QuantityResponseDTO response = service.convert(dto);

        assertEquals(12.0, response.getResultValue());
        assertEquals("INCHES", response.getResultUnit());
    }
}