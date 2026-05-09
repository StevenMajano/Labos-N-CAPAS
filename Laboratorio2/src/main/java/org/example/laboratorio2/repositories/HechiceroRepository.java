package org.example.laboratorio2.repositories;

import org.example.laboratorio2.entities.Hechicero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface HechiceroRepository extends JpaRepository<Hechicero, UUID> {
}
