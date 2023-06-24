package com.melniknow.sensor.validators;

import com.melniknow.sensor.models.Measurement;
import com.melniknow.sensor.services.SensorService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementAddValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementAddValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        var measurement = (Measurement) target;
        var sensorName = measurement.getSensor().getName();

        if (sensorService.findByName(sensorName).isEmpty())
            errors.rejectValue("sensor", "There is no sensor with that name!");
    }
}
