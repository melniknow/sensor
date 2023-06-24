package com.melniknow.sensor.controllers;

import com.melniknow.sensor.dto.SensorDeleteDTO;
import com.melniknow.sensor.dto.SensorRegistrationDTO;
import com.melniknow.sensor.dto.SensorFindAllDTO;
import com.melniknow.sensor.models.Sensor;
import com.melniknow.sensor.services.SensorService;
import com.melniknow.sensor.util.MeasurementErrorResponse;
import com.melniknow.sensor.util.MeasurementException;
import com.melniknow.sensor.validators.SensorDeleteValidator;
import com.melniknow.sensor.validators.SensorRegistrationValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.melniknow.sensor.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorRegistrationValidator sensorRegistrationValidator;
    private final SensorDeleteValidator sensorDeleteValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper,
                            SensorRegistrationValidator sensorRegistrationValidator,
                            SensorDeleteValidator sensorDeleteValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorRegistrationValidator = sensorRegistrationValidator;
        this.sensorDeleteValidator = sensorDeleteValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorRegistrationDTO sensorRegistrationDTO,
                                                   BindingResult bindingResult) {
        var sensorToAdd = modelMapper.map(sensorRegistrationDTO, Sensor.class);
        sensorRegistrationValidator.validate(sensorToAdd, bindingResult);

        if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);

        sensorService.register(sensorToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("show_all")
    public ResponseEntity<SensorFindAllDTO> showAll() {
        return new ResponseEntity<>(new SensorFindAllDTO(sensorService.findAll()), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@RequestBody @Valid SensorDeleteDTO sensorDeleteDTO,
                                             BindingResult bindingResult) {
        var sensorForDelete = modelMapper.map(sensorDeleteDTO, Sensor.class);
        sensorDeleteValidator.validate(sensorForDelete, bindingResult);

        if (bindingResult.hasErrors()) returnErrorsToClient(bindingResult);

        sensorService.deleteByName(sensorForDelete.getName());
        return ResponseEntity.ok(HttpStatus.OK);
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
