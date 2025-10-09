package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.service.EmpreendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Buscar todos empreendimentos",
            description = "Retorna dados de todos empreendimentos cadastrados"
    )
    @GetMapping
    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoService.findAll();
    }

    @Operation(
            summary = "Buscar empreendimento por ID",
            description = "Retorna dados do empreendimento com ID especificado "
    )
    @GetMapping("/{id}")
    public ResponseEntity<EmpreendimentoResponse> findById(@PathVariable Integer id) {
        EmpreendimentoResponse response = empreendimentoService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "Buscar dados enxutos de um empreendimento por ID",
            description = "Retorna dados enxutos prontos para serem preenchidos num documento"
    )
    @GetMapping("/doc/{id}")
    public ResponseEntity<EmpreendimentoDocResponse> getDocResponse(@PathVariable Integer id) {
        EmpreendimentoDocResponse response = empreendimentoService.getDocResponse(id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "Criação de um novo empreendimento",
            description = "Cria um novo empreendimento apartir das informações fornecidas no JSON"
    )
    @PostMapping
    public ResponseEntity<EmpreendimentoResponse> create(@RequestBody @Valid EmpreendimentoRequest data) {
        EmpreendimentoResponse response = empreendimentoService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualiza um empreendimento",
            description = "Atualiza um empreendimento especificado com ID, apartir das informações fornecidas no JSON"
    )
    @PutMapping("/{id}")
     public ResponseEntity<EmpreendimentoResponse> update(@PathVariable Integer id, @RequestBody @Valid EmpreendimentoRequest data) {
        EmpreendimentoResponse response = empreendimentoService.update(id, data);
        return ResponseEntity.ok().body(response); //quando o usuario quiser corrigir o documento reprovado pelo gestor
    }

    @Operation(
            summary = "Deleta um empreendimento",
            description = "Deleta um empreendimento apartir de seu ID. Necessita de role de ADMIN"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        empreendimentoService.delete(id);
        logger.info("User: {} deletou o empreendimento {}", auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
