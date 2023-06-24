package com.melniknow.sensor.services;

import com.melniknow.sensor.models.Measurement;
import com.melniknow.sensor.repositories.MeasurementRepository;
import com.melniknow.sensor.util.MeasurementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    public final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName())
                .orElseThrow(MeasurementException::new));

        measurement.setMeasurementDateTime(LocalDateTime.now());
    }

    public int getRainyEntryCount() {
        return measurementRepository.getRainyEntryCount();
    }
}
