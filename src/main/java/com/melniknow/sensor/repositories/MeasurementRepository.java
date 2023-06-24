package com.melniknow.sensor.repositories;

import com.melniknow.sensor.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    @Query(value = "SELECT count(*) FROM measurement WHERE raining = true", nativeQuery = true)
    int getRainyEntryCount();
}
