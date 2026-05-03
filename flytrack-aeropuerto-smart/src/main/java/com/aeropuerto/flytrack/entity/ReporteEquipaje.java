package com.aeropuerto.flytrack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "reporte_equipaje")
public class ReporteEquipaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroVuelo;

    @Column(nullable = false)
    private String numeroEquipaje;

    @Column(nullable = false, length = 2000)
    private String descripcionProblema;

    @Column(nullable = false)
    private LocalDateTime fechaReporte;

    @Column(nullable = false, unique = true)
    private String numeroReporte;

    @PrePersist
    void prePersist() {
        if (fechaReporte == null) {
            fechaReporte = LocalDateTime.now();
        }
    }

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

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getNumeroReporte() {
        return numeroReporte;
    }

    public void setNumeroReporte(String numeroReporte) {
        this.numeroReporte = numeroReporte;
    }
}
