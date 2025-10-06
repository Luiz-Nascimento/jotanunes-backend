package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.documento.DocumentoResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.DocumentoMapper;
import com.jotanunes.especificacoes.model.Documento;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.DocumentoRepository;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoService {

    private final DocumentoRepository repository;
    private final DocumentoMapper documentoMapper;
    private final EmpreendimentoRepository empreendimentoRepository;

    public DocumentoService(DocumentoRepository repository, DocumentoMapper documentoMapper, EmpreendimentoRepository empreendimentoRepository) {
        this.repository = repository;
        this.documentoMapper = documentoMapper;
        this.empreendimentoRepository = empreendimentoRepository;
    }

    public List<DocumentoResponse> findAll() {
        return repository.findAll().stream().map(documentoMapper::toDto).toList();
    }

    public DocumentoResponse create(Integer empreendimentoId) {
        Empreendimento empreendimento = empreendimentoRepository.findById(empreendimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + empreendimentoId));
        Documento documento = new Documento();
        documento.setEmpreendimento(empreendimento);
        Documento documentoSalvo = repository.save(documento);
        return documentoMapper.toDto(documentoSalvo);
    }


}
