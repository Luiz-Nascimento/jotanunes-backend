package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.service.AmbienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambientes")
public class AmbienteController {

    @Autowired
    private AmbienteService ambienteService;

    @GetMapping
    public List<AmbienteResponse> findAll() {
        return ambienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmbienteResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(ambienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AmbienteResponse> create(@RequestBody @Valid AmbienteRequest data) {
        AmbienteResponse ambienteResponse = ambienteService.create(data);
        return ResponseEntity.ok().body(ambienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        ambienteService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
