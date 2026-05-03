package com.aeropuerto.flytrack.controller;

import com.aeropuerto.flytrack.dto.ItinerarioResponse;
import com.aeropuerto.flytrack.dto.NotificacionResponse;
import com.aeropuerto.flytrack.dto.PuertaResponse;
import com.aeropuerto.flytrack.service.VueloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vuelos")
public class VueloController {

    private final VueloService vueloService;

    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping("/{numeroVuelo}")
    public ItinerarioResponse itinerario(@PathVariable String numeroVuelo) {
        return vueloService.obtenerItinerario(numeroVuelo);
    }

    @GetMapping("/{numeroVuelo}/notificacion")
    public NotificacionResponse notificacion(@PathVariable String numeroVuelo) {
        return vueloService.obtenerNotificacion(numeroVuelo);
    }

    @GetMapping("/{numeroVuelo}/puerta")
    public PuertaResponse puerta(@PathVariable String numeroVuelo) {
        return vueloService.obtenerPuerta(numeroVuelo);
    }
}
