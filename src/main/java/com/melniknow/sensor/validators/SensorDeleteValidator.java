package com.melniknow.sensor.validators;

import com.melniknow.sensor.models.Sensor;
import com.melniknow.sensor.services.SensorService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorDeleteValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorDeleteValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        var sensor = (Sensor) o;

        if (sensorService.findByName(sensor.getName()).isEmpty())
            errors.rejectValue("name", "There is no sensor with that name!");
    }
}
