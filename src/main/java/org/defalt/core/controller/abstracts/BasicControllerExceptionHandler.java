package org.defalt.core.controller.abstracts;

import org.defalt.core.context.auth.exception.UserNotRegisteredException;
import org.defalt.core.controller.exception.ExceptionResponse;
import org.defalt.core.controller.exception.ExceptionResponseBuilder;
import org.defalt.core.entity.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


public interface BasicControllerExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    default ResponseEntity<ExceptionResponse> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ExceptionResponseBuilder.build(e));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    default ResponseEntity<ExceptionResponse> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponseBuilder.build(e));
    }
    @ExceptionHandler(UserNotRegisteredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    default ResponseEntity<ExceptionResponse> handleUserNotRegistered(UserNotRegisteredException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponseBuilder.build(e));
    }
}
