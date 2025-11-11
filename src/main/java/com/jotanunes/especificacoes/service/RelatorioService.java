package com.jotanunes.especificacoes.service;


import com.jotanunes.especificacoes.dto.documento.DocumentoGeradoDTO;
import com.jotanunes.especificacoes.dto.empreendimento.EspecificacaTecnicaDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RelatorioService {

    private final EmpreendimentoService empreendimentoService;
    private final DocGenerateService docGenerateService;

    public RelatorioService(EmpreendimentoService empreendimentoService, DocGenerateService docGenerateService) {
        this.empreendimentoService = empreendimentoService;
        this.docGenerateService = docGenerateService;
    }

    private static final MediaType DOCX_MEDIA_TYPE = MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    public DocumentoGeradoDTO gerarEspecificacaoTecnica(Integer id) throws IOException, InterruptedException {

        EspecificacaTecnicaDTO dados = empreendimentoService.getDadosParaRelatorio(id);

        byte[] docxBytes = docGenerateService.gerarDocx(dados);

        String nomeDoDocumento = "Especificacao_" + dados.nome().replaceAll("[^a-zA-Z0-9.-]", "_") + ".docx";

        return new DocumentoGeradoDTO(docxBytes, nomeDoDocumento, DOCX_MEDIA_TYPE);

    }

}
