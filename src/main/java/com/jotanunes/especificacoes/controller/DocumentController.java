package com.jotanunes.especificacoes.controller;

import com.jotanunes.especificacoes.dto.empreendimento.EspecificacaTecnicaDTO;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documentos")
public class DocumentController {

    @Autowired
    private EmpreendimentoRepository empreendimentoRepository;
    @Autowired
    private EmpreendimentoMapper empreendimentoMapper;
    @Autowired
    private DocumentService docGeneratorService;

    @GetMapping("/{id}/docx")
    public ResponseEntity<byte[]> downloadDocx(@PathVariable Integer id) {
        try {
            // 1. Buscar dados (seu fluxo normal)
            Empreendimento empreendimento = empreendimentoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));

            // 2. Mapear para o DTO que o relatório usa
            EspecificacaTecnicaDTO dto = empreendimentoMapper.toEspecificacaoTecnica(empreendimento);

            // 3. Chamar o serviço gerador
            byte[] docxBytes = docGeneratorService.gerarDocx(dto);

            // 4. Preparar nome do arquivo para download
            String filename = "Especificacao_" + empreendimento.getNome().replaceAll("\\s+", "_") + ".docx";

            // 5. Retornar como download
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                    .body(docxBytes);

        } catch (Exception e) {
            e.printStackTrace(); // Melhor usar um logger aqui
            return ResponseEntity.internalServerError().build();
        }
    }
}