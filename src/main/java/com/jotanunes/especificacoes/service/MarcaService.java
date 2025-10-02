package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.marca.MarcaRequest;
import com.jotanunes.especificacoes.dto.marca.MarcaResponse;
import com.jotanunes.especificacoes.mapper.MarcaMapper;
import com.jotanunes.especificacoes.model.Marca;
import com.jotanunes.especificacoes.repository.MarcaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaService {

    private final MarcaRepository repository;
    private final MarcaMapper mapper;

    public MarcaService(MarcaRepository repository, MarcaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MarcaResponse salvar(MarcaRequest dto) {
        Marca marca = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(marca));
    }

    public List<MarcaResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public MarcaResponse buscarPorId(Integer id) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
        return mapper.toDTO(marca);
    }

    public MarcaResponse atualizar(Integer id, MarcaRequest dto) {
        Marca marca = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
        marca.setNome(dto.getNome());
        return mapper.toDTO(repository.save(marca));
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}