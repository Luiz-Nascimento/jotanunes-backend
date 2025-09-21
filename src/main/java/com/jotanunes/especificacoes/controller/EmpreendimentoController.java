package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.service.EmpreendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
