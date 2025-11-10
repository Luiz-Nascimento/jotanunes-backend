package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.empreendimento.EspecificacaTecnicaDTO;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
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
            // 1. Busca os dados JÁ PRONTOS do Service.
            // O Service que cuida da transação e de carregar tudo do banco.
            EspecificacaTecnicaDTO dto = empreendimentoService.getDadosParaRelatorio(id);

            // 2. Chama o gerador de DOCX
            byte[] docxBytes = docGeneratorService.gerarDocx(dto);

            // 3. Prepara o nome do arquivo (sanitizado)
            String filename = "Especificacao_" + dto.nome().replaceAll("[^a-zA-Z0-9.-]", "_") + ".docx";

            // 4. Retorna o download
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                    .body(docxBytes);

        } catch (Exception e) {
            e.printStackTrace(); // Em produção, use Logger!
            return ResponseEntity.internalServerError().build();
        }
    }
}