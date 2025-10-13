package com.jotanunes.especificacoes.controller;


import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.service.RevisaoItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/revisoes-itens")
public class RevisaoItemController {

    private final RevisaoItemService service;

    public RevisaoItemController(RevisaoItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<RevisaoItemResponse> listAll() {
        return service.findAll();
    }
}
