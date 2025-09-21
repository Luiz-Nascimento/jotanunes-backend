package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.service.EmpreendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empreendimentos")
public class EmpreendimentoController {

    @Autowired
    EmpreendimentoService empreendimentoService;

    @GetMapping
    public List<EmpreendimentoResponse> findAll() {
        return empreendimentoService.findAll();
    }

    @PostMapping
    public ResponseEntity<EmpreendimentoResponse> create(@RequestBody EmpreendimentoRequest data) {
        EmpreendimentoResponse response = empreendimentoService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
