package com.aeropuerto.flytrack.exception;

public class EquipajeNoRegistradoException extends RuntimeException {

    public EquipajeNoRegistradoException(String numeroVuelo, String numeroEquipaje) {
        super("El equipaje " + numeroEquipaje + " no está registrado en el sistema para el vuelo "
                + numeroVuelo + ". Verifique el número de etiqueta.");
    }
}
