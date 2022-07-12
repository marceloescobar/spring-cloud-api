package com.mescobar.inventory.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mescobar.inventory.model.Inventory;
import com.mescobar.inventory.repository.InventoryRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

	 private final InventoryRepository inventoryRepository;
	 
	 @GetMapping("/{skuCode}")
	    public Boolean isInStock(@PathVariable("skuCode") String skuCode) {
	        log.info("Checking stock for product with skucode - " + skuCode);

	        Optional<Inventory> optionalInventory = inventoryRepository.findBySkuCode(skuCode);
	        if(!optionalInventory.isPresent()){
	            new RuntimeException("Cannot Find Product by sku code " + skuCode);
	        }
	        return optionalInventory.get().getStock() > 0;
	    }
}
