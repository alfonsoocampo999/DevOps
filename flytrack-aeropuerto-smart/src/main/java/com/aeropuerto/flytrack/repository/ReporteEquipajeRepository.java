package com.aeropuerto.flytrack.repository;

import com.aeropuerto.flytrack.entity.ReporteEquipaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteEquipajeRepository extends JpaRepository<ReporteEquipaje, Long> {

    boolean existsByNumeroReporte(String numeroReporte);
}
