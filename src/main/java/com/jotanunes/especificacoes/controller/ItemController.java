package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.model.Item;
import com.jotanunes.especificacoes.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping
    public List<ItemResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> findById(@PathVariable Integer id) {
        ItemResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doc/{id}")
    public ResponseEntity<ItemDocResponse> getDocResponse(@PathVariable Integer id) {
        ItemDocResponse response = service.getDocResponse(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{idAmbiente}")
    public ResponseEntity<ItemResponse> create(@PathVariable Integer idAmbiente, @RequestBody ItemRequest data) {
        ItemResponse response = service.create(data, idAmbiente);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
