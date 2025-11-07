package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.controller.openapi.EmpreendimentoControllerOpenApi;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.empreendimento.*;
import com.jotanunes.especificacoes.service.AmbienteService;
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
public class EmpreendimentoController implements EmpreendimentoControllerOpenApi {

    private final EmpreendimentoService empreendimentoService;
    private final AmbienteService ambienteService;

    private static final Logger logger = LoggerFactory.getLogger(EmpreendimentoController.class);

    public EmpreendimentoController(EmpreendimentoService empreendimentoService, AmbienteService ambienteService) {
        this.empreendimentoService = empreendimentoService;
        this.ambienteService = ambienteService;
    }

    @Override
    @GetMapping
    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EmpreendimentoResponse> findById(@PathVariable Integer id) {
        EmpreendimentoResponse response = empreendimentoService.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/{id}/documento")
    public ResponseEntity<EmpreendimentoDocResponse> findByIdAsDocument(@PathVariable Integer id) {
        EmpreendimentoDocResponse response = empreendimentoService.findByIdAsDocument(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/{id}/ambientes")
    public List<AmbienteResponse> listAmbientes(@PathVariable Integer id) {
        return ambienteService.listByEmpreendimento(id);
    }

    @Override
    @PostMapping
    public ResponseEntity<EmpreendimentoResponse> create(@RequestBody @Valid EmpreendimentoRequest data) {
        EmpreendimentoResponse response = empreendimentoService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/copiar/{id}")
    public ResponseEntity<EmpreendimentoResponse> copy(@RequestBody @Valid EmpreendimentoRequest data, @PathVariable Integer id) {
        EmpreendimentoResponse response = empreendimentoService.copy(data, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PutMapping("/{id}")
     public ResponseEntity<EmpreendimentoResponse> update(@PathVariable Integer id, @RequestBody @Valid EmpreendimentoUpdate data) {
        EmpreendimentoResponse response = empreendimentoService.update(id, data);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/observacao/{id}")
    public ResponseEntity<EmpreendimentoResponse> adicionarObservacao(@PathVariable Integer id, @RequestBody @Valid EmpreendimentoObservacao data) {
        EmpreendimentoResponse response = empreendimentoService.adicionarObservacao(id, data);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/aprovar/{id}")
    public ResponseEntity<Void> forceAprovacao(@PathVariable Integer id) {
        empreendimentoService.forceAprovacao(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        empreendimentoService.delete(id);
        logger.info("User: {} deletou o empreendimento {}", auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
