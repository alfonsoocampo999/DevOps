package com.aeropuerto.flytrack.service;

import com.aeropuerto.flytrack.dto.ItinerarioResponse;
import com.aeropuerto.flytrack.dto.NotificacionResponse;
import com.aeropuerto.flytrack.dto.PuertaResponse;
import com.aeropuerto.flytrack.entity.Vuelo;
import com.aeropuerto.flytrack.exception.VueloNotFoundException;
import com.aeropuerto.flytrack.repository.VueloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class VueloService {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    private final VueloRepository vueloRepository;

    public VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    @Transactional(readOnly = true)
    public ItinerarioResponse obtenerItinerario(String numeroVuelo) {
        Vuelo v = buscar(numeroVuelo);
        LocalDateTime horaMostrada = v.getNuevoHorario() != null ? v.getNuevoHorario() : v.getHoraLlegada();
        return new ItinerarioResponse(
                v.getNumeroVuelo(),
                v.getDestino(),
                horaMostrada,
                v.getPuertaEmbarque(),
                v.getEstado()
        );
    }

    @Transactional(readOnly = true)
    public NotificacionResponse obtenerNotificacion(String numeroVuelo) {
        Vuelo v = buscar(numeroVuelo);
        return new NotificacionResponse(construirMensajeNotificacion(v));
    }

    @Transactional(readOnly = true)
    public PuertaResponse obtenerPuerta(String numeroVuelo) {
        Vuelo v = buscar(numeroVuelo);
        boolean cambioPuerta = "CAMBIO DE PUERTA".equals(v.getEstado())
                && v.getNuevaPuerta() != null
                && !v.getNuevaPuerta().isBlank();
        if (cambioPuerta) {
            return new PuertaResponse(
                    "Puerta actualizada: " + v.getNuevaPuerta(),
                    v.getNuevaPuerta()
            );
        }
        return new PuertaResponse(
                "Puerta asignada: " + v.getPuertaEmbarque(),
                v.getPuertaEmbarque()
        );
    }

    private String construirMensajeNotificacion(Vuelo v) {
        String nv = v.getNumeroVuelo();
        if ("A TIEMPO".equals(v.getEstado())) {
            return "No hay cambios para el vuelo " + nv + ". El vuelo está a tiempo.";
        }
        if ("RETRASADO".equals(v.getEstado()) && v.getNuevoHorario() != null) {
            return "El vuelo " + nv + " tiene RETRASO, nueva hora " + v.getNuevoHorario().toLocalTime().format(TIME_FMT) + ".";
        }
        if ("CANCELADO".equals(v.getEstado())) {
            return "El vuelo " + nv + " ha sido CANCELADO. Comuníquese con su aerolínea.";
        }
        if ("CAMBIO DE PUERTA".equals(v.getEstado()) && v.getNuevaPuerta() != null) {
            return "El vuelo " + nv + " tiene CAMBIO DE PUERTA: pase de " + v.getPuertaEmbarque()
                    + " a la puerta " + v.getNuevaPuerta() + ".";
        }
        return "No hay cambios adicionales registrados para el vuelo " + nv + ".";
    }

    private Vuelo buscar(String numeroVuelo) {
        return vueloRepository.findByNumeroVueloIgnoreCase(numeroVuelo)
                .orElseThrow(() -> new VueloNotFoundException(numeroVuelo));
    }
}
