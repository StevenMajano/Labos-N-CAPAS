package org.example.laboratorio2.controllers;

import org.example.laboratorio2.entities.Hechicero;
import org.example.laboratorio2.services.HechiceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sorcerers")
public class HechiceroController {

    @Autowired
    private HechiceroService service;

    // Registrar hechicero (POST)
    @PostMapping
    public ResponseEntity<Hechicero> registrar(@RequestBody Hechicero hechicero) {
        return ResponseEntity.ok(service.guardar(hechicero));
    }

    // Obtener todos (GET)
    @GetMapping
    public ResponseEntity<List<Hechicero>> obtenerTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // Obtener por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Hechicero> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Eliminar (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
