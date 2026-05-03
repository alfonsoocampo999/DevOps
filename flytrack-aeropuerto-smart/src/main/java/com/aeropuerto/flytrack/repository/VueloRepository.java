package com.aeropuerto.flytrack.repository;

import com.aeropuerto.flytrack.entity.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    Optional<Vuelo> findByNumeroVueloIgnoreCase(String numeroVuelo);
}
