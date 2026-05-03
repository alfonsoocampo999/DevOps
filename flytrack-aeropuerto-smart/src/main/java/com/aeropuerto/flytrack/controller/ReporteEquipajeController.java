package com.aeropuerto.flytrack.controller;

import com.aeropuerto.flytrack.dto.ReporteCreateRequest;
import com.aeropuerto.flytrack.dto.ReporteCreateResponse;
import com.aeropuerto.flytrack.service.ReporteEquipajeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteEquipajeController {

    private final ReporteEquipajeService reporteEquipajeService;

    public ReporteEquipajeController(ReporteEquipajeService reporteEquipajeService) {
        this.reporteEquipajeService = reporteEquipajeService;
    }

    @PostMapping
    public ReporteCreateResponse crear(@RequestBody ReporteCreateRequest body) {
        return reporteEquipajeService.crear(body);
    }
}
