package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.item.CatalogoItemRequest;
import com.jotanunes.especificacoes.dto.item.CatalogoItemResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.CatalogoItemMapper;
import com.jotanunes.especificacoes.model.CatalogoAmbiente;
import com.jotanunes.especificacoes.model.CatalogoItem;
import com.jotanunes.especificacoes.repository.CatalogoAmbienteRepository;
import com.jotanunes.especificacoes.repository.CatalogoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoItemService {

    private final CatalogoItemRepository catalogoItemRepository;
    private final CatalogoItemMapper catalogoItemMapper;
    private final CatalogoAmbienteRepository catalogoAmbienteRepository;

    public CatalogoItemService(CatalogoItemRepository catalogoItemRepository, CatalogoItemMapper catalogoItemMapper, CatalogoAmbienteRepository catalogoAmbienteRepository) {
        this.catalogoItemRepository = catalogoItemRepository;
        this.catalogoItemMapper = catalogoItemMapper;
        this.catalogoAmbienteRepository = catalogoAmbienteRepository;
    }

    public List<CatalogoItemResponse> getAll() {
        return catalogoItemRepository.findAll()
                .stream()
                .map(catalogoItemMapper::toResponse)
                .toList();
    }

    public CatalogoItemResponse create(CatalogoItemRequest request) {
        CatalogoAmbiente catalogoAmbiente = catalogoAmbienteRepository.findById(request.idAmbiente())
                .orElseThrow(() -> new ResourceNotFoundException("Ambiente nao encontrado com id: " + request.idAmbiente()));
        CatalogoItem entity = catalogoItemMapper.toEntity(request);
        entity.setAmbiente(catalogoAmbiente);
        return catalogoItemMapper.toResponse(catalogoItemRepository.save(entity));
    }
}
