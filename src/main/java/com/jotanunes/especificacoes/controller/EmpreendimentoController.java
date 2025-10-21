package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoUpdate;
import com.jotanunes.especificacoes.service.EmpreendimentoService;
import io.swagger.v3.oas.annotations.Operation;
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
            summary = "Retornar dados de todos empreendimentos",
            description = "Retorna dados de todos empreendimentos cadastrados"
    )
    @GetMapping
    public List<EmpreendimentoResponse> listEmpreendimentos() {
        return empreendimentoService.getAllEmpreendimentos();
    }

    @Operation(
            summary = "Retornar dados de um empreendimento",
            description = "Retorna dados do empreendimento com ID especificado "
    )
    @GetMapping("/{id}")
    public ResponseEntity<EmpreendimentoResponse> getEmpreendimento(@PathVariable Integer id) {
        EmpreendimentoResponse response = empreendimentoService.getEmpreendimentoById(id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "Retornar dados de um empreendimento, formatados para documento",
            description = "Retorna dados do empreendimento com ID especificado formatados para documento"
    )
    @GetMapping("/doc/{id}")
    public ResponseEntity<EmpreendimentoDocResponse> getEmpreendimentoDocResponse(@PathVariable Integer id) {
        EmpreendimentoDocResponse response = empreendimentoService.getEmpreendimentoDocResponse(id);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}/ambientes")
    @Operation(
            summary = "Retornar todos os ambientes de um empreendimento",
            description = "Retorna todos os ambientes associados ao empreendimento com ID especificado"
    )
    public List<AmbienteResponse> getAmbientesByEmpreendimentoId(@PathVariable Integer id) {
        return empreendimentoService.getAmbientesByEmpreendimentoId(id);
    }

    @Operation(
            summary = "Criação de um novo empreendimento",
            description = "Cria um novo empreendimento apartir das informações fornecidas no JSON"
    )
    @PostMapping
    public ResponseEntity<EmpreendimentoResponse> createEmpreendimento(@RequestBody @Valid EmpreendimentoRequest data) {
        EmpreendimentoResponse response = empreendimentoService.createEmpreendimento(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Atualiza um empreendimento",
            description = "Atualiza um empreendimento especificado apartir das informações fornecidas no JSON"
    )
    @PutMapping
     public ResponseEntity<EmpreendimentoResponse> updateEmpreendimento(@RequestBody @Valid EmpreendimentoUpdate data) {
        EmpreendimentoResponse response = empreendimentoService.updateEmpreendimento(data);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "Deleta um empreendimento",
            description = "Deleta um empreendimento apartir de seu ID. Necessita de role de ADMIN"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpreendimento(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        empreendimentoService.deleteEmpreendimento(id);
        logger.info("User: {} deletou o empreendimento {}", auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}
