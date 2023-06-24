package com.melniknow.sensor.controllers;

import com.melniknow.sensor.dto.MeasurementAddDTO;
import com.melniknow.sensor.dto.MeasurementFindAllDTO;
import com.melniknow.sensor.dto.MeasurementGetRainyEntryCountDTO;
import com.melniknow.sensor.models.Measurement;
import com.melniknow.sensor.services.MeasurementService;
import com.melniknow.sensor.util.MeasurementErrorResponse;
import com.melniknow.sensor.util.MeasurementException;
import com.melniknow.sensor.validators.MeasurementAddValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.melniknow.sensor.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementAddValidator measurementAddValidator;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, ModelMapper modelMapper,
                                  MeasurementAddValidator measurementAddValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementAddValidator = measurementAddValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementAddDTO measurementAddDTO,
                                          BindingResult bindingResult) {
        var measurementToAdd = modelMapper.map(measurementAddDTO, Measurement.class);
        measurementAddValidator.validate(measurementToAdd, bindingResult);

        if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);

        measurementService.save(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MeasurementFindAllDTO> findAll() {
        return new ResponseEntity<>(new MeasurementFindAllDTO(measurementService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/rainyDaysEntry")
    public ResponseEntity<MeasurementGetRainyEntryCountDTO> getRainyEntryCount() {
        return new ResponseEntity<>(new MeasurementGetRainyEntryCountDTO(measurementService.getRainyEntryCount()),
                HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        var response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
