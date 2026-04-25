package org.example.labo1umbrella.repository;

import org.example.labo1umbrella.model.Especimen;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EspecimenRepository {

    // Esta lista actúa como tu base de datos en memoria
    private final List<Especimen> dbEspecimenes = new ArrayList<>();

    // Método para guardar un nuevo espécimen en la lista
    public void guardar(Especimen esp) {
        dbEspecimenes.add(esp);
    }

    // Método para obtener todos los registros actuales
    public List<Especimen> obtenerTodos() {
        return new ArrayList<>(dbEspecimenes);
    }
}
