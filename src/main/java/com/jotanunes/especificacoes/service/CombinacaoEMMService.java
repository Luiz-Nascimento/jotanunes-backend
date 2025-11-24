package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.*;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.CombinacaoEMMMapper;
import com.jotanunes.especificacoes.model.CombinacaoEMM;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.model.Marca;
import com.jotanunes.especificacoes.model.Material;
import com.jotanunes.especificacoes.repository.CombinacaoEMMRepository;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import com.jotanunes.especificacoes.repository.MarcaRepository;
import com.jotanunes.especificacoes.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CombinacaoEMMService {

    private final CombinacaoEMMRepository repository;
    private final EmpreendimentoRepository empreendimentoRepository;
    private final MaterialRepository materialRepository;
    private final MarcaRepository marcaRepository;
    private final CombinacaoEMMMapper emmMapper;

    public CombinacaoEMMService(CombinacaoEMMRepository repository, EmpreendimentoRepository empreendimentoRepository, MaterialRepository materialRepository, MarcaRepository marcaRepository, CombinacaoEMMMapper emmMapper) {
        this.repository = repository;
        this.empreendimentoRepository = empreendimentoRepository;
        this.materialRepository = materialRepository;
        this.marcaRepository = marcaRepository;
        this.emmMapper = emmMapper;
    }

    public List<CombinacaoEMMResponse> findAll() {
        return emmMapper.toDtoList(repository.findAll());
    }


    public CombinacaoEMMResponse findById(Integer id) {
        CombinacaoEMM combinacaoEMM = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Combinação naõ encontrada com id: "+ id));
        return emmMapper.toDto(combinacaoEMM);
    }

    public CombinacaoEMMResponse create(Integer empreendimentoID, CombinacaoEMMRequest request) {
        Empreendimento empreendimento = empreendimentoRepository.findById(empreendimentoID)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + empreendimentoID));
        Material material = materialRepository.findById(request.materialID())
                .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado com id: " + request.materialID()));
        Marca marca = marcaRepository.findById(request.marcaID())
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontradao com id: " + request.marcaID()));

        CombinacaoEMM combinacaoEMM = new CombinacaoEMM(empreendimento, material, marca);
        return emmMapper.toDto(combinacaoEMM);
    }
    public List<CombinacaoEMMResponse> createCombinacoes(Integer empreendimentoID, List<CombinacaoEMMBulkRequest> requests) {
        Empreendimento empreendimento = empreendimentoRepository.findById(empreendimentoID)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + empreendimentoID));
        List<CombinacaoEMM> combinacoes = new ArrayList<>();
        for (CombinacaoEMMBulkRequest material: requests) {
            Material materialEncontrado = materialRepository.findById(material.materialID())
                    .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado com id: " + material.materialID()));
            for(Integer marcaID: material.marcasID()) {
                Marca marca = marcaRepository.findById(marcaID)
                        .orElseThrow(() -> new ResourceNotFoundException("Marca não encontradao com id: " + marcaID));
                CombinacaoEMM combinacao = new CombinacaoEMM(empreendimento, materialEncontrado, marca);
                combinacoes.add(combinacao);
            }
        }
        List<CombinacaoEMMResponse> responses = new ArrayList<>();
        for (CombinacaoEMM combinacaoEMM: repository.saveAll(combinacoes)) {
            responses.add(emmMapper.toDto(combinacaoEMM));
        }
        return responses;
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Combinação EMM não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }


}
