package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMRequest;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasIdsResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        return repository.findAll().stream().map(emmMapper::toCombinacaoEMMResponse).toList();
    }

    //Metodo para criar combinações EMM,
    //Referenciado um id de empreendimento
    //Um id de material
    //Um set de marcas
    //public void createCombinations(Integer empreendimentoID, List<MaterialMarcasRequest> materialMarcasList) {
    //  Procurar empreendimento com id correspondente
    // Cria uma lista vazia com as combinações a serem salvas
    //  Iterar lista do response
    //  Verificar id do material para referenciar
    //  Iterar lista de marcas de dentro do response
    //  Verificar o id da marca
    //  Criar uma combinação: Referenciado o id do empreendimento, o id do material e o id da marca
    //  Adiciona combinação na lista
    //  Continuar até acabar ou jogar uma exceção
    //  Usa um saveAll para salvar as combinações no banco
    public List<CombinacaoEMMResponse> createCombinacoes(Integer empreendimentoID, List<CombinacaoEMMRequest> requests) {
        Empreendimento empreendimento = empreendimentoRepository.findById(empreendimentoID)
                .orElseThrow(() -> new ResourceNotFoundException("Empreendimento não encontrado com id: " + empreendimentoID));
        List<CombinacaoEMM> combinacoes = new ArrayList<>();
        for (CombinacaoEMMRequest material: requests) {
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
            responses.add(emmMapper.toCombinacaoEMMResponse(combinacaoEMM));
        }
        return responses;
    }

    //Metodo para listar combinações de um empreendimento especifico apartir do id
    // Uma lista que irá retornar:
    // Irá retornar id do material
    // Lista com id das marcas
    // Fluxo:
    // Buscar registros de combinações com empreendimento especificado
    // Adiciona-las numa lista
    // Agrupar por material
    // mapear para dto
    // retorna a lista de dto
    public List<MaterialMarcasIdsResponse> findMaterialMarcasIdsByEmpreendimentoId(Integer empreendimentoID) {
        List<CombinacaoEMM> registros = repository.findByEmpreendimentoId(empreendimentoID);
        Map<Integer, Set<Integer>> agrupamento = registros.stream()
                .collect(Collectors.groupingBy(combinacao ->
                        combinacao.getMaterial().getId(),
                        Collectors.mapping(combinacao -> combinacao.getMarca().getId(),
                                Collectors.toSet())));

        return agrupamento.entrySet().stream()
                .map(entry -> new MaterialMarcasIdsResponse(entry.getKey(), entry.getValue()))
                .toList();
    }


    //Metodo para listar combinacoes de um empreendimento especifico apartir do nome
    // Uma lista que irá retornar:
    // Irá retornar nome do material
    // Lista com nome das marcas

    public List<MaterialMarcasNomeResponse> findMaterialMarcasNomeByEmpreendimentoId(Integer empreendimentoID) {
        List<CombinacaoEMM> registros = repository.findByEmpreendimentoId(empreendimentoID);
        Map<String, Set<String>> agrupamento = registros.stream()
                .collect(Collectors.groupingBy(combinacao ->
                                combinacao.getMaterial().getNome(),
                        Collectors.mapping(combinacao -> combinacao.getMarca().getNome(),
                                Collectors.toSet())));

        return agrupamento.entrySet().stream()
                .map(entry -> new MaterialMarcasNomeResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

}
