package com.api_food.Algaworks_Food.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private String joinPath(List<Reference> references){
        return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
    }

    private MessageException.MessageExceptionBuilder createMessage(HttpStatusCode status, ProblemType type, String title, String detail, LocalDateTime dateTime){
        return MessageException.builder()
                .status(status.value())
                .type(type.getUri())
                .title(title)
                .detail(detail)
                .dateTime(dateTime);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if(body == null){

            MessageException message = MessageException.builder()
                    .status(statusCode.value())
                    .title(ex.getMessage())
                    .dateTime(LocalDateTime.now())
                    .build();

            body = message;
        }

        if (body instanceof String){

            MessageException message = MessageException.builder()
                    .status(statusCode.value())
                    .title((String) body)
                    .dateTime(LocalDateTime.now())
                    .build();

            body = message;
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType type = ProblemType.UNREADABLE_MESSAGE;

        String detail = String.format("Property '%s' received the value '%s' " +
                "which is not of the expected type. Please correct it and provide a value of type '%s'.",
                path, ex.getValue(), ex.getTargetType().getSimpleName()
                );

        MessageException message = createMessage(status, type, type.getTitle(), detail, LocalDateTime.now()).build();

        return handleExceptionInternal(ex, message, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType type = ProblemType.UNREADABLE_MESSAGE;

        String detail = String.format("The property '%s' does not exist. " +
                "Remove this property and try again.", path);

        MessageException message = createMessage(status, type, type.getTitle(), detail, LocalDateTime.now()).build();

        return handleExceptionInternal(ex, message, headers, status, request);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException){
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException || rootCause instanceof IgnoredPropertyException || rootCause instanceof UnrecognizedPropertyException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemType type = ProblemType.UNREADABLE_MESSAGE;
        String detail = "One or more request fields are invalid. Please check the syntax";

        MessageException message = createMessage(status, type, type.getTitle(), detail, LocalDateTime.now()).build();

        return handleExceptionInternal(ex, message, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){

        ProblemType type = ProblemType.INVALID_PARAMETER;

        String detail = String.format("The URL parameter '%s', received the value '%s', "
                + "which is of an invalid type. Please correct it and provide a value compatible with the expected type %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        MessageException message = createMessage(status, type, type.getTitle(), detail, LocalDateTime.now()).build();

        return handleExceptionInternal(ex, message, headers, status, request);
    }
}