package com.melniknow.sensor.dto;

import com.melniknow.sensor.models.Measurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MeasurementFindAllDTO {
    List<Measurement> measurementList;
}
