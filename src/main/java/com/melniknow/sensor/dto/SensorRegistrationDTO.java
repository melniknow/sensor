package com.melniknow.sensor.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorRegistrationDTO {
    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 3, max = 30, message = "Name of the sensor must be from 3 to 30 characters!")
    private String name;
}
