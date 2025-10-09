package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.item.ItemDocResponse;
import com.jotanunes.especificacoes.dto.item.ItemRequest;
import com.jotanunes.especificacoes.dto.item.ItemResponse;
import com.jotanunes.especificacoes.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Itens", description = "Operações relacionadas a itens de um ambiente")
@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping
    public List<ItemResponse> listAllItens() {
        return service.getAllItens();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable Integer id) {
        ItemResponse response = service.getItemById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doc/{id}")
    public ResponseEntity<ItemDocResponse> getItemDocResponse(@PathVariable Integer id) {
        ItemDocResponse response = service.getItemDocResponse(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{idAmbiente}")
    public ResponseEntity<ItemResponse> createItem(@PathVariable Integer idAmbiente, @RequestBody ItemRequest data) {
        ItemResponse response = service.createItem(data, idAmbiente);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
