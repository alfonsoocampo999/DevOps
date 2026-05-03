package com.aeropuerto.flytrack.dto;

import java.time.LocalDateTime;

public record ItinerarioResponse(
        String numeroVuelo,
        String destino,
        LocalDateTime horaSalida,
        String puertaEmbarque,
        String estado
) {}
