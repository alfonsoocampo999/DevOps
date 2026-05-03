package com.aeropuerto.flytrack.config;

import com.aeropuerto.flytrack.exception.EquipajeNoRegistradoException;
import com.aeropuerto.flytrack.exception.VueloNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VueloNotFoundException.class)
    public ResponseEntity<Map<String, String>> vueloNoEncontrado(VueloNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(EquipajeNoRegistradoException.class)
    public ResponseEntity<Map<String, String>> equipajeNoRegistrado(EquipajeNoRegistradoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }
}
