package org.example.labo1umbrella;

import org.example.labo1umbrella.model.*;
import org.example.labo1umbrella.repository.EspecimenRepository;
import org.example.labo1umbrella.service.EspecimenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EspecimenRepository repository;
    private final EspecimenService service;

    public DataInitializer(EspecimenRepository repository, EspecimenService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Cargamos especímenes de prueba
        repository.guardar(new Especimen("Licker", VirusBase.T_VIRUS, 4, "Cabeza", EstadoActual.EN_LIBERTAD, Ubicacion.COMISARIA, "Lab Central"));
        repository.guardar(new Especimen("Hunter", VirusBase.T_VIRUS, 3, "Cuello", EstadoActual.EN_LIBERTAD, Ubicacion.MANSION, "Lab Bio"));
        repository.guardar(new Especimen("Tyrant", VirusBase.T_VIRUS, 5, "Corazón expuesto", EstadoActual.EN_LIBERTAD, Ubicacion.LABORATORIO, "Lab Delta"));
        repository.guardar(new Especimen("Zombie", VirusBase.T_VIRUS, 1, "Cabeza", EstadoActual.ELIMINADO, Ubicacion.PUEBLO, "Aula 101"));

        // 2. Ejemplo de ejecución del filtro y salida con el tag [S.T.A.R.S-REPORT]
        System.out.println("\n--- INICIANDO REPORTE DE AMENAZAS ---");

        List<Especimen> enLibertad = service.filtrarPorEstado(EstadoActual.EN_LIBERTAD);

        enLibertad.forEach(e -> {
            System.out.printf("[S.T.A.R.S-REPORT] Nombre: %s | Nivel de Peligro: %d | Punto Débil: %s%n",
                    e.getNombre(), e.getNivelPeligro(), e.getPuntoDebilFisico());
        });

        // 3. Mostrar virus activos sin repetición
        System.out.println("\n[S.T.A.R.S-REPORT] VIRUS ACTIVOS DETECTADOS:");
        service.obtenerVirusActivosSinRepeticion().forEach(v -> System.out.println("- " + v));
    }
}
