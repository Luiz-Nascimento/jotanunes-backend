package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.empreendimento.EspecificacaTecnicaDTO;
import com.jotanunes.especificacoes.service.DocumentService;
import com.jotanunes.especificacoes.service.EmpreendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documentos")
public class DocumentController {

    @Autowired
    private EmpreendimentoService empreendimentoService;
    @Autowired
    private DocumentService docGeneratorService;

    @GetMapping("/{id}/especificacao-tecnica")
    public ResponseEntity<byte[]> downloadDocx(@PathVariable Integer id) {
        try {
            EspecificacaTecnicaDTO dto = empreendimentoService.getDadosParaRelatorio(id);

            byte[] docxBytes = docGeneratorService.gerarDocx(dto);

            String filename = "Especificacao_" + dto.nome().replaceAll("[^a-zA-Z0-9.-]", "_") + ".docx";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                    .body(docxBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}