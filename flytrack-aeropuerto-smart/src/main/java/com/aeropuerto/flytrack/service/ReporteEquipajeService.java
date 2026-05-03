package com.aeropuerto.flytrack.service;

import com.aeropuerto.flytrack.dto.ReporteCreateRequest;
import com.aeropuerto.flytrack.dto.ReporteCreateResponse;
import com.aeropuerto.flytrack.entity.ReporteEquipaje;
import com.aeropuerto.flytrack.exception.EquipajeNoRegistradoException;
import com.aeropuerto.flytrack.exception.VueloNotFoundException;
import com.aeropuerto.flytrack.repository.EquipajeRepository;
import com.aeropuerto.flytrack.repository.ReporteEquipajeRepository;
import com.aeropuerto.flytrack.repository.VueloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ReporteEquipajeService {

    private final ReporteEquipajeRepository reporteEquipajeRepository;
    private final VueloRepository vueloRepository;
    private final EquipajeRepository equipajeRepository;

    public ReporteEquipajeService(
            ReporteEquipajeRepository reporteEquipajeRepository,
            VueloRepository vueloRepository,
            EquipajeRepository equipajeRepository
    ) {
        this.reporteEquipajeRepository = reporteEquipajeRepository;
        this.vueloRepository = vueloRepository;
        this.equipajeRepository = equipajeRepository;
    }

    @Transactional
    public ReporteCreateResponse crear(ReporteCreateRequest request) {
        if (request.numeroVuelo() == null || request.numeroVuelo().isBlank()) {
            throw new IllegalArgumentException("El número de vuelo es obligatorio.");
        }
        if (request.numeroEquipaje() == null || request.numeroEquipaje().isBlank()) {
            throw new IllegalArgumentException("El número de equipaje es obligatorio.");
        }
        if (request.descripcion() == null || request.descripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción del problema es obligatoria.");
        }

        String vuelo = request.numeroVuelo().trim();
        String etiqueta = request.numeroEquipaje().trim();

        vueloRepository.findByNumeroVueloIgnoreCase(vuelo)
                .orElseThrow(() -> new VueloNotFoundException(vuelo));

        equipajeRepository.findByNumeroVueloIgnoreCaseAndNumeroEquipajeIgnoreCase(vuelo, etiqueta)
                .orElseThrow(() -> new EquipajeNoRegistradoException(vuelo, etiqueta));

        ReporteEquipaje r = new ReporteEquipaje();
        r.setNumeroVuelo(vuelo);
        r.setNumeroEquipaje(etiqueta);
        r.setDescripcionProblema(request.descripcion().trim());
        r.setNumeroReporte(generarNumeroReporteUnico());

        reporteEquipajeRepository.save(r);

        String msg = "Reporte #" + r.getNumeroReporte() + " registrado con éxito";
        return new ReporteCreateResponse(r.getNumeroReporte(), msg);
    }

    private String generarNumeroReporteUnico() {
        for (int i = 0; i < 20; i++) {
            int n = ThreadLocalRandom.current().nextInt(10_000, 100_000);
            String candidate = "REP-" + n;
            if (!reporteEquipajeRepository.existsByNumeroReporte(candidate)) {
                return candidate;
            }
        }
        String fallback = "REP-" + System.currentTimeMillis();
        if (!reporteEquipajeRepository.existsByNumeroReporte(fallback)) {
            return fallback;
        }
        return "REP-" + System.nanoTime();
    }
}
