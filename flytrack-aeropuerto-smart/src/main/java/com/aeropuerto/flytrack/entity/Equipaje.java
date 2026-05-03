package com.aeropuerto.flytrack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Equipaje registrado en sistema (p. ej. facturación / lectura de etiqueta) vinculado a un vuelo.
 */
@Entity
@Table(
        name = "equipaje",
        uniqueConstraints = @UniqueConstraint(columnNames = {"numero_vuelo", "numero_equipaje"})
)
public class Equipaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_vuelo", nullable = false, length = 32)
    private String numeroVuelo;

    @Column(name = "numero_equipaje", nullable = false, length = 64)
    private String numeroEquipaje;

    public Long getId() {
        return id;
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public void setNumeroVuelo(String numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
    }

    public String getNumeroEquipaje() {
        return numeroEquipaje;
    }

    public void setNumeroEquipaje(String numeroEquipaje) {
        this.numeroEquipaje = numeroEquipaje;
    }
}
