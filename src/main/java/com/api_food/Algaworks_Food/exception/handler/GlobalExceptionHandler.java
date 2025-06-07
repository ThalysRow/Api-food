package com.api_food.Algaworks_Food.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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
}