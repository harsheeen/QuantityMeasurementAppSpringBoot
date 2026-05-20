package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityMeasurementRepository 
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    // Custom methods (auto-generated queries)
    List<QuantityMeasurementEntity> findByOperation(String operation);

    List<QuantityMeasurementEntity> findByErrorTrue();
    
    long countByOperation(String operation);
}