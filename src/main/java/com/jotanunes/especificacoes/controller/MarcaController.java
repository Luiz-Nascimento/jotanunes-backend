package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.marca.MarcaRequest;
import com.jotanunes.especificacoes.dto.marca.MarcaResponse;
import com.jotanunes.especificacoes.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Marcas", description = "Operações relacionadas à marcas.")
@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaService service;

    public MarcaController(MarcaService service) {
        this.service = service;
    }

    @Operation(
            summary = "Criar uma nova marca",
            description = "Cria uma nova marca com os dados fornecidos"
    )
    @PostMapping
    public ResponseEntity<MarcaResponse> create(@RequestBody @Valid MarcaRequest dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(
            summary = "Retornar dados de todas as marcas",
            description = "Retorna dados de todas as marcas cadastradas"
    )
    @GetMapping
    public ResponseEntity<List<MarcaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Retornar dados de uma marca",
            description = "Retorna dados da marca com ID especificado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Atualizar dados de uma marca",
            description = "Atualiza os dados da marca com ID especificado"
    )
    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponse> update(@PathVariable Integer id, @RequestBody @Valid MarcaRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(
            summary = "Deletar uma marca",
            description = "Deleta a marca com ID especificado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}