package com.melniknow.sensor.dto;

import com.melniknow.sensor.models.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SensorFindAllDTO {
    private List<Sensor> sensorList;
}
