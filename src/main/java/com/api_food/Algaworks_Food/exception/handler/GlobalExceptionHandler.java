package com.api_food.Algaworks_Food.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
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

    private MessageException.MessageExceptionBuilder createMessage(HttpStatusCode status, ProblemType type, String title, String datail, LocalDateTime dateTime){
        return MessageException.builder()
                .status(status.value())
                .type(type.getUri())
                .title(title)
                .detail(datail)
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
}