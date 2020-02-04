package com.wbo.TwitterManager.exeption;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author b.walid
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<JsonResponse> handleResourceNotFound(final ResourceNotFoundException exception,
            final HttpServletRequest request) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setErrorMsg(exception.getMessage());
        jsonResponse.setErrorCode("404");
        jsonResponse.setStatus(JsonResponse.STATUS.FAILED.name());
        return ResponseEntity.ok().body(jsonResponse);
    }

    @ExceptionHandler(ValidationExeption.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<JsonResponse> validationException(final Exception exception,
            final HttpServletRequest request) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setErrorMsg(exception.getMessage());
        jsonResponse.setErrorCode("NOTVALID");
        jsonResponse.setStatus(JsonResponse.STATUS.FAILED.name());
        return ResponseEntity.ok().body(jsonResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<JsonResponse> handleException(final Exception exception,
            final HttpServletRequest request) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setErrorMsg(exception.getMessage());
        jsonResponse.setErrorCode("500");
        jsonResponse.setStatus(JsonResponse.STATUS.FAILED.name());
        return ResponseEntity.ok().body(jsonResponse);
    }

}
