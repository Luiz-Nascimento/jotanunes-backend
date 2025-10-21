package com.jotanunes.especificacoes.dto.empreendimento;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;

import java.util.List;
import java.util.Set;

public record EmpreendimentoDocResponse(String nome, String localizacao, String descricao, List<String> observacoes,
                                        Set<AmbienteDocResponse> ambientes,
                                        List<MaterialMarcasNomeResponse> marcasMaterial) {
}
