package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.documento.DocumentoResponse;
import com.jotanunes.especificacoes.dto.documento.DocumentoResumidoResponse;
import com.jotanunes.especificacoes.enums.DocumentoStatus;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.DocumentoMapper;
import com.jotanunes.especificacoes.model.Documento;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.repository.DocumentoRepository;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public DocumentoResponse findById(Integer id) {
        return documentoMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado com id: " + id)));
    }

    public DocumentoResponse create(Integer empreendimentoId) {
        Empreendimento empreendimento = empreendimentoRepository.findById(empreendimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + empreendimentoId));
        Documento documento = new Documento();
        documento.setEmpreendimento(empreendimento);
        Documento documentoSalvo = repository.save(documento);
        return documentoMapper.toDto(documentoSalvo);
    }

//    public DocumentoResumidoResponse aprovarDocumento(Integer id) {
//        Documento documento = repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado com id: " + id));
//        if (documento.getStatus() == null || documento.getStatus().name().equals("PENDENTE")) {
//            for (documento.getEmpreendimento().getAmbientes())
//        } else {
//            throw new IllegalStateException("Apenas documentos com status PENDENTE podem ser aprovados.");
//        }
//        Documento documentoAtualizado = repository.save(documento);
//        return documentoMapper.toResumidoDto(documentoAtualizado);
//    }

    public void delete(Integer id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Documento não encontrado com id: " + id);
        }
        repository.deleteById(id);

    }



}
