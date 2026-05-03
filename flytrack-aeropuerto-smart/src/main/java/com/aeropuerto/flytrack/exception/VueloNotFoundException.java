package com.aeropuerto.flytrack.exception;

public class VueloNotFoundException extends RuntimeException {

    public VueloNotFoundException(String numeroVuelo) {
        super("No encontramos el vuelo " + numeroVuelo + ". Verifique el número e intente de nuevo.");
    }
}
