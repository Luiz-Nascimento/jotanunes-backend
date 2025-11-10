package com.jotanunes.especificacoes.dto.empreendimento;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasDocResponse;
import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasNomeResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;

import java.util.List;

public record EspecificacaTecnicaDTO(String nome, String localizacao, String descricao,
                                     List<AmbienteDocResponse> privativos,
                                     List<AmbienteDocResponse> areaComum,
                                     List<MaterialMarcasDocResponse> marcasMaterial,
                                     List<String> observacoes) {
}
