package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.item.CatalogoItemRequest;
import com.jotanunes.especificacoes.dto.item.CatalogoItemResponse;
import com.jotanunes.especificacoes.service.CatalogoItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo-itens")
public class CatalogoItemController {

    private final CatalogoItemService service;

    public CatalogoItemController(CatalogoItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<CatalogoItemResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<CatalogoItemResponse> create(@RequestBody CatalogoItemRequest request) {
        CatalogoItemResponse response = service.create(request);
        return ResponseEntity.status(201).body(response);
    }
}
