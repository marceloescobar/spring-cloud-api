package com.mescobar.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mescobar.inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	 Optional<Inventory> findBySkuCode(String skuCode);
}
