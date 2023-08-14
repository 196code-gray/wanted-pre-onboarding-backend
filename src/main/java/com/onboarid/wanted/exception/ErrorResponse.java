package com.onboarid.wanted.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> fieldErrors;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ConstraintViolationError> violationErrors;


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private CustomData customData;


    private ErrorResponse(List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors) {
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }
    private ErrorResponse(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    private ErrorResponse(CustomData data){
        this.customData = data;
    }


    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(FieldError.of(bindingResult), null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResponse(null, ConstraintViolationError.of(violations));
    }

    public static ErrorResponse of(ExceptionCode exceptionCode){
        return new ErrorResponse(exceptionCode.getStatusCode(), exceptionCode.getMessage());
    }

    public static ErrorResponse of(HttpStatus httpStatus){

        return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase());
    }

    public static ErrorResponse of(Integer status, String message){
        return new ErrorResponse(status, message);
    }

    public static ErrorResponse of(ErrorResponse info, Map<String, String> message) {
        return new ErrorResponse(new ErrorResponse.CustomData(info, message));
    }
    @Getter
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ?
                                    "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    public static class ConstraintViolationError {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        private ConstraintViolationError(String propertyPath, Object rejectedValue,
                                         String reason) {
            this.propertyPath = propertyPath;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<ConstraintViolationError> of(
                Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream()
                    .map(constraintViolation
                            -> new ConstraintViolationError(
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getInvalidValue().toString(),
                            constraintViolation.getMessage()
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    public static class CustomData {
        private final ErrorResponse info;
        private final Map<String, String> message;

        public CustomData(ErrorResponse info, Map<String, String> message) {
            this.info = info;
            this.message = message;
        }
    }
}
