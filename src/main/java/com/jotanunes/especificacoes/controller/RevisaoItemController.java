package com.jotanunes.especificacoes.controller;


import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.service.RevisaoItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/revisoes-itens")
@Tag(name = "Revisão de Itens", description = "Operações relacionadas à auditoria de revisão de itens")
public class RevisaoItemController {

    private final RevisaoItemService service;

    public RevisaoItemController(RevisaoItemService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todas as revisões de itens",
            description = "Retorna uma lista de todas as revisões de itens realizadas no sistema."
    )
    @GetMapping
    public List<RevisaoItemResponse> listAll() {
        return service.findAll();
    }
}
