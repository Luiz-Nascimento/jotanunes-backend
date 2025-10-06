package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.documento.DocumentoResponse;
import com.jotanunes.especificacoes.service.DocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DocumentoResponse> findAll() {
        return service.findAll();
    }

    @PostMapping("/{id}")
    public ResponseEntity<DocumentoResponse> create(@PathVariable Integer id) {
        DocumentoResponse response = service.create(id);
        return ResponseEntity.ok(response);
    }
}
