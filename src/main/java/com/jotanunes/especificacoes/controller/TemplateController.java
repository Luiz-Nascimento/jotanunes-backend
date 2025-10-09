package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.service.EmpreendimentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Template", description = "Operações relacionadas à construção de templates htmls")
@Controller
public class TemplateController {

    private final EmpreendimentoService empreendimentoService;

    public TemplateController(EmpreendimentoService empreendimentoService) {
        this.empreendimentoService = empreendimentoService;
    }

    @GetMapping("/template/{empreendimentoId}")
    public String getTemplate(@PathVariable Integer empreendimentoId, Model model) {
        EmpreendimentoDocResponse data = empreendimentoService.getEmpreendimentoDocResponse(empreendimentoId);
        model.addAttribute("template", data);
        return "template";

    }
}
