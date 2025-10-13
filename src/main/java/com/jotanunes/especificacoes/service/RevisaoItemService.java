package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import com.jotanunes.especificacoes.mapper.RevisaoItemMapper;
import com.jotanunes.especificacoes.repository.RevisaoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevisaoItemService {

    private final RevisaoItemRepository repository;

    private final RevisaoItemMapper revisaoItemMapper;

    public RevisaoItemService(RevisaoItemRepository repository, RevisaoItemMapper revisaoItemMapper) {
        this.repository = repository;
        this.revisaoItemMapper = revisaoItemMapper;
    }

    public List<RevisaoItemResponse> findAll(){
        return repository.findAll().stream().map(revisaoItemMapper::toDto).toList();
    }


}
