package com.es.phoneshop.web.controller;

import com.es.core.model.ItemNotFoundException;
import com.es.core.order.OutOfStockException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = Logger.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<String> handleOutOfStockException(OutOfStockException e, WebRequest request) {
        logger.error(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("outOfStock");
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleOutOfStockException(ItemNotFoundException e, WebRequest request) {
        logger.error(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("notFound");
    }
}
