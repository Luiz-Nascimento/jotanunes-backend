package com.jotanunes.especificacoes.controller.openapi;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.CombinacaoEMMResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteResponse;
import com.jotanunes.especificacoes.dto.empreendimento.*;
import com.jotanunes.especificacoes.dto.revisaoItens.RevisaoItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

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
    ResponseEntity<EspecificacaTecnicaDTO> findByIdAsDocument(Integer id);

    @Operation(
            summary = "Retornar todos os ambientes de um empreendimento",
            description = "Retorna todos os ambientes associados ao empreendimento com ID especificado"
    )
    List<AmbienteResponse> listAmbientes(Integer id);

    @Operation(
            summary = "Retorna dados das combinações de material e marcas do empreendimento",
            description = "Retorna conjunto de todas combinações de material e marcas do empreendimento com ID especificado"
    )
    List<CombinacaoEMMResponse> findCombinacoes(Integer id);

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

    @Operation(
            summary = "Submete um empreendimento para revisão",
            description = "Muda o status do empreendimento para pendente, necessita permissão de operacional"
    )
    ResponseEntity<Void> submeter(Integer id);

    @Operation(
            summary = "Aprova um empreendimento",
            description = "Muda o status do empreendimento para aprovado, necessita permissão de gestor"
    )
    ResponseEntity<Void> aprovar(Integer id);

    @Operation(
            summary = "Reprova um empreendimento",
            description = "Muda o status do empreendimento para reprovado, necessita permissão de gestor"
    )
    ResponseEntity<Void> reprovar(Integer id);

    @Operation(
            summary = "Adiciona uma observação sobre o empreendimento",
            description = "Adiciona um texto de observação na lista de observações"
    )
    ResponseEntity<EmpreendimentoResponse> adicionarObservacao(Integer id, EmpreendimentoObservacao data);

    @Operation(
            summary = "Atualiza uma observação sobre o empreendimento",
            description = "Atualiza uma observação sobre o empreendimento, especificando sua posição na lista"
    )
    ResponseEntity<EmpreendimentoResponse> atualizarObservacao(Integer id, int index, EmpreendimentoObservacao data);

    @Operation(
            summary = "Deleta uma observação sobre o empreendimento",
            description = "Remove uma observação apartir de seu índice na lista de observações"
    )
    ResponseEntity<EmpreendimentoResponse> removerObservacao(Integer id, int index);

    @Operation(
            summary = "Limpa todas observações sobre o empreendimento",
            description = "Remove todas as observações sobre o empreendimento"
    )
    ResponseEntity<EmpreendimentoResponse> limparObservacoes(Integer id);

    @Operation(
            summary = "Gera um documento de especificação técnica no formato DOCX",
            description = "Apartir do id do empreendimento, faz o download e geração do documento sobre o empreendimento"
    )
    ResponseEntity<byte[]> downloadAsDocx(Integer id);

    @Operation(
            summary = "Lista revisões de itens por empreendimento"
    )
    List<RevisaoItemResponse> findRevisoes(Integer id);

    @Operation(
            summary = "Remove observações de um empreendimento por índice",
            description = "Apartir do ID do empreendimento, e um conjunto de índices válidos, as observações com índice" +
                    " especificado são removidas"
    )
    ResponseEntity<EmpreendimentoResponse> removerObservacoes(Integer id, Set<Integer> indexes);

}
