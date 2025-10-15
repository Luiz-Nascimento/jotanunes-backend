package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.CatalogoAmbienteResponse;
import com.jotanunes.especificacoes.service.CatalogoAmbienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo-ambientes")
public class CatalogoAmbienteController {

    private final CatalogoAmbienteService service;

    public CatalogoAmbienteController(CatalogoAmbienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<CatalogoAmbienteResponse> getAllCatalogoAmbientes() {
        return service.getAllCatalogoAmbientes();
    }

    @PostMapping
    public ResponseEntity<CatalogoAmbienteResponse> createCatalogoAmbiente(@RequestBody CatalogoAmbienteRequest request) {
        CatalogoAmbienteResponse response = service.createCatalogoAmbiente(request);
        return ResponseEntity.ok(response);
    }
}
