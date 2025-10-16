package com.app.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.inventory.model.Item;
import com.app.inventory.repository.InventoryRepository;

@Service
public class InventoryService {
    // DI
    private final InventoryRepository repo;

    // konstruktor
    public InventoryService(InventoryRepository repo){
        this.repo = repo;
    }

    // method getItem
    public List<Item> getAllItems(){
        return repo.ambilSemua();
    }

    // simpan
    public Item addItem(Item item){
        return repo.simpan(item);
    }
}
