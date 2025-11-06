package com.jotanunes.especificacoes.controller.openapi;

import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Empreendimentos", description = "Operações relacionadas a empreendimentos.")
public interface EmpreendimentoControllerOpenApi {

    @Operation(
            summary = "Retornar dados de todos empreendimentos",
            description = "Retorna dados de todos empreendimentos cadastrados"
    )
    List<EmpreendimentoResponse> findAll();

    @Operation(
            summary = "Retornar dados de um empreendimento",
            description = "Retorna dados do empreendimento com ID especificado "
    )
    ResponseEntity<EmpreendimentoResponse> findById(Integer id);

    @Operation(
            summary = "Retornar dados de um empreendimento, formatados para documento",
            description = "Retorna dados do empreendimento com ID especificado formatados para documento"
    )
    ResponseEntity<EmpreendimentoDocResponse> findByIdAsDocument(Integer id);

    @Operation(
            summary = "Retornar todos os ambientes de um empreendimento",
            description = "Retorna todos os ambientes associados ao empreendimento com ID especificado"
    )
    List<AmbienteResponse> listAmbientes(Integer id);

    @Operation(
            summary = "Criação de um novo empreendimento",
            description = "Cria um novo empreendimento apartir das informações fornecidas no JSON"
    )
    ResponseEntity<EmpreendimentoResponse> create(EmpreendimentoRequest data);

    @Operation(
            summary = "Cria um novo empreendimento no padrão de um empreendimento aprovado",
            description = "Cria um novo empreendimento com ambientes e itens padrões de um empreendimento especificado aprovado"
    )
    ResponseEntity<EmpreendimentoResponse> copy(EmpreendimentoRequest data, Integer id);

    @Operation(
            summary = "Atualiza um empreendimento",
            description = "Atualiza um empreendimento especificado apartir das informações fornecidas no JSON"
    )
    ResponseEntity<EmpreendimentoResponse> update(Integer id, EmpreendimentoUpdate data);

    @Operation(
            summary = "Força a aprovação um empreendimento por completo",
            description = "Endpoint para testes, aprova todo um empreendimento sem necessidade de revisões"
    )
    ResponseEntity<Void> forceAprovacao(Integer id);

    @Operation(
            summary = "Deleta um empreendimento",
            description = "Deleta um empreendimento apartir de seu ID. Necessita de role de ADMIN"
    )
    ResponseEntity<Void> delete(Integer id);

}
