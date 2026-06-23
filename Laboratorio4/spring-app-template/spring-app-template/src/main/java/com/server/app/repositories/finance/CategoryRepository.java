package com.server.app.repositories.finance;

import com.server.app.entities.finance.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}