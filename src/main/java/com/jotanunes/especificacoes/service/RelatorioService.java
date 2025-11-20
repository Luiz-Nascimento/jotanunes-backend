package com.jotanunes.especificacoes.service;


import com.jotanunes.especificacoes.dto.documento.DocumentoGeradoDTO;
import com.jotanunes.especificacoes.dto.empreendimento.EspecificacaTecnicaDTO;
import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
import com.jotanunes.especificacoes.exception.DocumentGenerationException;
import com.jotanunes.especificacoes.exception.EmpreendimentoBusinessLogicException;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RelatorioService {

    private final EmpreendimentoService empreendimentoService;
    private final DocGenerateService docGenerateService;
    private final EmpreendimentoRepository empreendimentoRepository;

    public RelatorioService(EmpreendimentoService empreendimentoService, DocGenerateService docGenerateService, EmpreendimentoRepository empreendimentoRepository) {
        this.empreendimentoService = empreendimentoService;
        this.docGenerateService = docGenerateService;
        this.empreendimentoRepository = empreendimentoRepository;
    }

    private static final MediaType DOCX_MEDIA_TYPE = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    public DocumentoGeradoDTO gerarEspecificacaoTecnica(Integer id) {
        Empreendimento empreendimento = empreendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: "+ id));
        if (empreendimento.getStatus() != EmpreendimentoStatus.APROVADO) {
            throw new EmpreendimentoBusinessLogicException("Empreendimento não aprovado! Falha na geração do documento");
        }
        EspecificacaTecnicaDTO dados = empreendimentoService.getDadosParaRelatorio(id);
        try {
            byte[] docxBytes = docGenerateService.gerarDocx(dados);

            String nomeDoDocumento = "Especificacao_" + dados.nome().replaceAll("[^a-zA-Z0-9.-]", "_") + ".docx";

            return new DocumentoGeradoDTO(docxBytes, nomeDoDocumento, DOCX_MEDIA_TYPE);
        } catch (IOException | InterruptedException e) {
            throw new DocumentGenerationException("Falhar ao gerar documento");
        }
    }

}
