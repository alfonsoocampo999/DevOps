package com.aeropuerto.flytrack.repository;

import com.aeropuerto.flytrack.entity.Equipaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipajeRepository extends JpaRepository<Equipaje, Long> {

    Optional<Equipaje> findByNumeroVueloIgnoreCaseAndNumeroEquipajeIgnoreCase(String numeroVuelo, String numeroEquipaje);
}
