package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.service.AmbienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ambientes", description = "Operações relacionadas a ambientes do empreendimento")
@RestController
@RequestMapping("/ambientes")
public class AmbienteController {

    private final AmbienteService service;

    public AmbienteController(AmbienteService service) {
        this.service = service;
    }
    @Operation(
            summary = "Buscar todos ambientes",
            description = "Retorna dados de todos ambientes cadastrados"
    )
    @GetMapping
    public List<AmbienteResponse> listAllAmbientes() {
        return service.getAllAmbientes();
    }

    @Operation(
            summary = "Buscar ambiente por id",
            description = "Retorna dados do ambiente com ID especificado"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AmbienteResponse> getAmbiente(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.getAmbienteById(id));
    }
    @GetMapping("/doc/{id}")
    public ResponseEntity<AmbienteDocResponse> getAmbienteDocResponse(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.getAmbienteDocResponse(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<AmbienteResponse> createAmbiente(@RequestBody @Valid AmbienteRequest data, @PathVariable Integer id) {
        AmbienteResponse ambienteResponse = service.createAmbiente(data, id);
        return ResponseEntity.ok().body(ambienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmbiente(@PathVariable Integer id) {
        service.deleteAmbiente(id);
        return ResponseEntity.noContent().build();
    }


}
