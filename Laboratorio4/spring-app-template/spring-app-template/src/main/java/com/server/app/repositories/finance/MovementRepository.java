package com.server.app.repositories.finance;

import com.server.app.entities.finance.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Integer> {

    List<Movement> findByAccountUserId(Integer userId);
}