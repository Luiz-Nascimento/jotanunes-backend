package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.item.CatalogoItemRequest;
import com.jotanunes.especificacoes.dto.item.CatalogoItemResponse;
import com.jotanunes.especificacoes.service.CatalogoItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo-itens")
@Tag(name = "Catálogo de Itens", description = "Operações relacionadas ao catálogo de itens")
public class CatalogoItemController {

    private final CatalogoItemService service;

    public CatalogoItemController(CatalogoItemService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todos os itens do catálogo",
            description = "Retorna uma lista de todos os itens disponíveis no catálogo."
    )
    @GetMapping
    public List<CatalogoItemResponse> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Criar um novo item no catálogo",
            description = "Adiciona um novo item ao catálogo de itens."
    )
    @PostMapping
    public ResponseEntity<CatalogoItemResponse> create(@RequestBody @Valid CatalogoItemRequest request) {
        CatalogoItemResponse response = service.create(request);
        return ResponseEntity.status(201).body(response);
    }
}
