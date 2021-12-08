/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mthree.guessthenumber.controllers;

import com.mthree.guessthenumber.service.GameFinishedException;
import com.mthree.guessthenumber.service.InvalidGameIdException;
import com.mthree.guessthenumber.service.InvalidGuessFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author john
 */
@ControllerAdvice
@RestController
public class GuessControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidGameIdException.class)
    public final ResponseEntity<Error> handleInvalidGameId(
            InvalidGameIdException ex, WebRequest request) {

        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidGuessFormatException.class)
    public final ResponseEntity<Error> handleInvalidGuess(
            InvalidGuessFormatException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(GameFinishedException.class)
    public final ResponseEntity<Error> handleGameFinished(
            GameFinishedException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
