package com.aeropuerto.flytrack.config;

import com.aeropuerto.flytrack.entity.Equipaje;
import com.aeropuerto.flytrack.entity.Vuelo;
import com.aeropuerto.flytrack.repository.EquipajeRepository;
import com.aeropuerto.flytrack.repository.VueloRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final VueloRepository vueloRepository;
    private final EquipajeRepository equipajeRepository;

    public DataLoader(VueloRepository vueloRepository, EquipajeRepository equipajeRepository) {
        this.vueloRepository = vueloRepository;
        this.equipajeRepository = equipajeRepository;
    }

    @Override
    public void run(String... args) {
        if (vueloRepository.count() == 0) {
            vueloRepository.save(v(
                    "AV123", "Bogotá",
                    LocalDateTime.of(2025, 5, 15, 14, 30),
                    "B23", "RETRASADO", null,
                    LocalDateTime.of(2025, 5, 15, 15, 30)
            ));
            vueloRepository.save(v(
                    "AV456", "Medellín",
                    LocalDateTime.of(2025, 5, 15, 16, 0),
                    "C12", "A TIEMPO", null, null
            ));
            vueloRepository.save(v(
                    "AV789", "Cali",
                    LocalDateTime.of(2025, 5, 15, 18, 45),
                    "A05", "CAMBIO DE PUERTA", "D03", null
            ));
            vueloRepository.save(v(
                    "AV999", "Cartagena",
                    LocalDateTime.of(2025, 5, 15, 20, 0),
                    "E08", "A TIEMPO", null, null
            ));
            vueloRepository.save(v(
                    "AV555", "Santa Marta",
                    LocalDateTime.of(2025, 5, 16, 8, 0),
                    "B12", "CANCELADO", null, null
            ));
        }

        if (equipajeRepository.count() == 0 && vueloRepository.count() > 0) {
            equipajeRepository.save(e("AV123", "EQ-7788"));
            equipajeRepository.save(e("AV123", "EQ-8899"));
            equipajeRepository.save(e("AV456", "EQ-45601"));
            equipajeRepository.save(e("AV789", "EQ-78901"));
            equipajeRepository.save(e("AV999", "EQ-99901"));
            equipajeRepository.save(e("AV555", "EQ-55501"));
        }
    }

    private static Equipaje e(String numeroVuelo, String numeroEquipaje) {
        Equipaje x = new Equipaje();
        x.setNumeroVuelo(numeroVuelo);
        x.setNumeroEquipaje(numeroEquipaje);
        return x;
    }

    private static Vuelo v(
            String numero, String destino,
            LocalDateTime hora, String puerta, String estado,
            String nuevaPuerta, LocalDateTime nuevoHorario
    ) {
        Vuelo x = new Vuelo();
        x.setNumeroVuelo(numero);
        x.setDestino(destino);
        x.setHoraLlegada(hora);
        x.setPuertaEmbarque(puerta);
        x.setEstado(estado);
        x.setNuevaPuerta(nuevaPuerta);
        x.setNuevoHorario(nuevoHorario);
        return x;
    }
}
