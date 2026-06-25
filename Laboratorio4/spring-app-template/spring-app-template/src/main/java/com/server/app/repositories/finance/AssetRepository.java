package com.server.app.repositories.finance;

import com.server.app.entities.finance.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Integer> {

    Optional<Asset> findBySymbol(String symbol);
}
