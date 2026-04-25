package org.example.labo1umbrella.service;

import org.example.labo1umbrella.model.Especimen;
import org.example.labo1umbrella.model.VirusBase;
import org.example.labo1umbrella.model.EstadoActual;
import org.example.labo1umbrella.repository.EspecimenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecimenService {

    private final EspecimenRepository repository;

    // Inyección de Dependencias por Constructor
    public EspecimenService(EspecimenRepository repository) {
        this.repository = repository;
    }

    // 1. Filtrado por virus base
    public List<Especimen> filtrarPorVirus(VirusBase virus) {
        return repository.obtenerTodos().stream()
                .filter(e -> e.getVirusBase() == virus)
                .collect(Collectors.toList());
    }

    // 2. Filtrado por estado actual
    public List<Especimen> filtrarPorEstado(EstadoActual estado) {
        return repository.obtenerTodos().stream()
                .filter(e -> e.getEstadoActual() == estado)
                .collect(Collectors.toList());
    }

    // 3. Virus activos sin repetición (usando distinct)
    public List<VirusBase> obtenerVirusActivosSinRepeticion() {
        return repository.obtenerTodos().stream()
                .filter(e -> e.getEstadoActual() == EstadoActual.EN_LIBERTAD)
                .map(Especimen::getVirusBase)
                .distinct()
                .collect(Collectors.toList());
    }
}
