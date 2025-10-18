package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteResponse;
import com.jotanunes.especificacoes.service.CatalogoAmbienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo-ambientes")
@Tag(name = "Catálogo de Ambientes", description = "Operações relacionadas ao catálogo de ambientes")
public class CatalogoAmbienteController {

    private final CatalogoAmbienteService service;

    public CatalogoAmbienteController(CatalogoAmbienteService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todos os ambientes do catálogo",
            description = "Retorna uma lista de todos os ambientes disponíveis no catálogo."
    )
    @GetMapping
    public List<CatalogoAmbienteResponse> getAllCatalogoAmbientes() {
        return service.getAllCatalogoAmbientes();
    }

    @Operation(
            summary = "Criar um novo ambiente no catálogo",
            description = "Adiciona um novo ambiente ao catálogo de ambientes."
    )
    @PostMapping
    public ResponseEntity<CatalogoAmbienteResponse> createCatalogoAmbiente(@RequestBody @Valid CatalogoAmbienteRequest request) {
        CatalogoAmbienteResponse response = service.createCatalogoAmbiente(request);
        return ResponseEntity.ok(response);
    }
}
