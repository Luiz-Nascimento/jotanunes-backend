package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.marca.MarcaRequest;
import com.jotanunes.especificacoes.dto.marca.MarcaResponse;
import com.jotanunes.especificacoes.model.Marca;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {

    public Marca toEntity(MarcaRequest dto) {
        return new Marca(dto.getNome());
    }

    public MarcaResponse toDTO(Marca marca) {
        return new MarcaResponse(marca.getId(), marca.getNome());
    }
}