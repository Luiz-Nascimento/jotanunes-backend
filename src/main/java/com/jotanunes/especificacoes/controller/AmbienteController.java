package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.service.AmbienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ambientes", description = "Operações relacionadas a ambientes")
@RestController
@RequestMapping("/ambientes")
public class AmbienteController {

    private final AmbienteService service;

    public AmbienteController(AmbienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<AmbienteResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmbienteResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.findById(id));
    }
    @GetMapping("/doc/{id}")
    public ResponseEntity<AmbienteDocResponse> getDocResponse(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.getDocResponse(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<AmbienteResponse> create(@RequestBody @Valid AmbienteRequest data, @PathVariable Integer id) {
        AmbienteResponse ambienteResponse = service.create(data, id);
        return ResponseEntity.ok().body(ambienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
