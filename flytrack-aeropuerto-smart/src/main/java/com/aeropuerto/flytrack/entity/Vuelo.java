package com.aeropuerto.flytrack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "vuelo")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroVuelo;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDateTime horaLlegada;

    @Column(nullable = false)
    private String puertaEmbarque;

    @Column(nullable = false)
    private String estado;

    private String nuevaPuerta;

    private LocalDateTime nuevoHorario;

    public Long getId() {
        return id;
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public void setNumeroVuelo(String numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalDateTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(String puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNuevaPuerta() {
        return nuevaPuerta;
    }

    public void setNuevaPuerta(String nuevaPuerta) {
        this.nuevaPuerta = nuevaPuerta;
    }

    public LocalDateTime getNuevoHorario() {
        return nuevoHorario;
    }

    public void setNuevoHorario(LocalDateTime nuevoHorario) {
        this.nuevoHorario = nuevoHorario;
    }
}
