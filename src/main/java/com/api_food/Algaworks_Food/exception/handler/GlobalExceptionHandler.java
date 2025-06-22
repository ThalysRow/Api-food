package com.api_food.Algaworks_Food.exception.handler;

import com.api_food.Algaworks_Food.exception.custom.*;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private String joinPath(List<Reference> references){
        return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
    }

    private MessageException.MessageExceptionBuilder createMessage(HttpStatusCode status, ProblemType type, String title, String detail, OffsetDateTime dateTime){
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
                    .dateTime(OffsetDateTime.now())
                    .build();

            body = message;
        }

        if (body instanceof String){

            MessageException message = MessageException.builder()
                    .status(statusCode.value())
                    .title((String) body)
                    .dateTime(OffsetDateTime.now())
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

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType type = ProblemType.UNREADABLE_MESSAGE;

        String detail = String.format("The property '%s' does not exist. " +
                "Remove this property and try again.", path);

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

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

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){

        ProblemType type = ProblemType.INVALID_PARAMETER;

        String detail = String.format("The URL parameter '%s', received the value '%s', "
                + "which is of an invalid type. Please correct it and provide a value compatible with the expected type %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException){
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<?> handleCityNotFoundException(CityNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(KitchenNotFoundException.class)
    public ResponseEntity<?> handleKitchenNotFoundException(KitchenNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<?> handleRestaurantNotFoundException(RestaurantNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(StateNotFoundException.class)
    public ResponseEntity<?> handleStateNotFoundException(StateNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        ProblemType type = ProblemType.BUSINESS_ERROR;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.CONFLICT;
        ProblemType type = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemType type = ProblemType.INVALID_DATA;

        List<String> details = ex.getBindingResult().getFieldErrors()
                .stream().map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        String detail = String.join(", ", details);

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PaymentMethodAlreadyExistsException.class)
    public ResponseEntity<?> handlePaymentMethodAlreadyExistsException(PaymentMethodAlreadyExistsException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.CONFLICT;
        ProblemType type = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PaymentMethodNotFoundException.class)
    public ResponseEntity<?> handlePaymentMethodNotFoundException(PaymentMethodNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(GroupNameAlreadyExistsException.class)
    public ResponseEntity<?> handleGroupNameAlreadyExistsException(GroupNameAlreadyExistsException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.CONFLICT;
        ProblemType type = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<?> handleGroupNotFoundException(GroupNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,WebRequest request){
        HttpStatusCode status = HttpStatus.CONFLICT;
        ProblemType type = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex,message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<?> handleInvalidCurrentPasswordException(InvalidCurrentPasswordException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.UNAUTHORIZED;
        ProblemType type = ProblemType.UNAUTHORIZED;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ProductNotFoundInRestaurantException.class)
    public ResponseEntity<?> handleProductNotFoundInRestaurantException(ProductNotFoundInRestaurantException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(PermissionNotFoundException.class)
    public ResponseEntity<?> handlePermissionNotFoundException(PermissionNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request){
        HttpStatusCode status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        MessageException message = createMessage(status, type, type.getTitle(), detail, OffsetDateTime.now()).build();

        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }
}