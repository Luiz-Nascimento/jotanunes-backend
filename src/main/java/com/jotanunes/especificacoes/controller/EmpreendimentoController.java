package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.service.EmpreendimentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Empreendimentos", description = "Operações relacionadas a empreendimentos.")
@RestController
@RequestMapping("/empreendimentos")
public class EmpreendimentoController {

    private final EmpreendimentoService empreendimentoService;

    private static final Logger logger = LoggerFactory.getLogger(EmpreendimentoController.class);

    public EmpreendimentoController(EmpreendimentoService empreendimentoService) {
        this.empreendimentoService = empreendimentoService;
    }

    @GetMapping
    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpreendimentoResponse> findById(@PathVariable Integer id) {
        EmpreendimentoResponse response = empreendimentoService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/doc/{id}")
    public ResponseEntity<EmpreendimentoDocResponse> getDocResponse(@PathVariable Integer id) {
        EmpreendimentoDocResponse response = empreendimentoService.getDocResponse(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<EmpreendimentoResponse> create(@RequestBody @Valid EmpreendimentoRequest data) {
        EmpreendimentoResponse response = empreendimentoService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
    @PutMapping("/{id}")
     public ResponseEntity<EmpreendimentoResponse> update(@PathVariable Integer id, @RequestBody @Valid EmpreendimentoRequest data) {
        EmpreendimentoResponse response = empreendimentoService.update(id, data);
        return ResponseEntity.ok().body(response); //quando o usuario quiser corrigir o documento reprovado pelo gestor
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        empreendimentoService.delete(id);
        logger.info("User: {} deletou o empreendimento {}", auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
