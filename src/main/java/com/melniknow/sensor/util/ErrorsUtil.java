package com.melniknow.sensor.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult) {
        var errorMsg = new StringBuilder();
        var errors = bindingResult.getFieldErrors();

        for (FieldError error : errors)
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage() == null
                            ? error.getCode()
                            : error.getDefaultMessage())
                    .append(";");


        throw new MeasurementException(errorMsg.toString());
    }
}
