package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.material.MaterialRequest;
import com.jotanunes.especificacoes.dto.material.MaterialResponse;
import com.jotanunes.especificacoes.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Materiais", description = "Operações relacionada à materiais.")
@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) { this.service = service; }

    @Operation(
            summary = "Criar um novo material",
            description = "Cria um novo material com os dados fornecidos"
    )
    @PostMapping
    public ResponseEntity<MaterialResponse> create(@RequestBody @Valid MaterialRequest dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(
            summary = "Retornar dados de todos os materiais",
            description = "Retorna dados de todos os materiais cadastrados"
    )
    @GetMapping
    public ResponseEntity<List<MaterialResponse>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @Operation(
            summary = "Retornar dados de um material",
            description = "Retorna dados do material com ID especificado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Atualizar dados de um material",
            description = "Atualiza os dados do material com ID especificado"
    )
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> update(@PathVariable Integer id, @RequestBody @Valid MaterialRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(
            summary = "Deletar um material",
            description = "Deleta o material com ID especificado"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}