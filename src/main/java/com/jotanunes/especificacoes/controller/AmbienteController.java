package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteRequest;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.service.AmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<AmbienteResponse> create(@RequestBody AmbienteRequest data) {
        AmbienteResponse ambienteResponse = ambienteService.create(data);
        return ResponseEntity.ok().body(ambienteResponse);
    }


}
