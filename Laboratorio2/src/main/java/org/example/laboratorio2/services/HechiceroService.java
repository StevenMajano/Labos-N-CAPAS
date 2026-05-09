package org.example.laboratorio2.services;

import org.example.laboratorio2.entities.Hechicero;
import org.example.laboratorio2.repositories.HechiceroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class HechiceroService {

    @Autowired
    private HechiceroRepository repository;

    // Obtener todos los hechiceros
    public List<Hechicero> listarTodos() {
        return repository.findAll();
    }

    // Guardar o actualizar un hechicero
    public Hechicero guardar(Hechicero hechicero) {
        return repository.save(hechicero);
    }

    // Buscar por ID con manejo de nulos
    public Hechicero buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hechicero no encontrado con el ID: " + id));
    }

    // Eliminar un hechicero
    public void eliminar(UUID id) {
        Hechicero hechicero = buscarPorId(id);
        repository.delete(hechicero);
    }
}
