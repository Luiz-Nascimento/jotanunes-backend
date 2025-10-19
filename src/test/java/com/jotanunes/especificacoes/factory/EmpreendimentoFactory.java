package com.jotanunes.especificacoes.factory;

import com.jotanunes.especificacoes.model.Empreendimento;

public class EmpreendimentoFactory {

    private EmpreendimentoFactory() {}

    public static Empreendimento criarEmpreendimentoPadrao() {
        Empreendimento empreendimento = new Empreendimento();
        empreendimento.setId(1);
        empreendimento.setNome("Empreendimento Teste");
        empreendimento.setLocalizacao("Localização Teste");
        empreendimento.setDescricao("Descrição do Empreendimento Teste");
        empreendimento.setObservacoes("Observações do Empreendimento Teste");
        return empreendimento;
    }
}
