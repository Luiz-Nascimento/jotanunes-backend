
package com.jotanunes.especificacoes.factory;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.enums.EmpreendimentoStatus;
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

    public static EmpreendimentoRequest criarEmpreendimentoRequestPadrao() {
        return new EmpreendimentoRequest(
                "Empreendimento Teste",
                "Localização Teste",
                "Descrição do Empreendimento Teste",
                "Observações do Empreendimento Teste"
        );
    }

    public static Empreendimento criarEmpreendimentoMapeadoPadrao() {
        Empreendimento empreendimento = new Empreendimento();
        empreendimento.setNome("Empreendimento Teste");
        empreendimento.setLocalizacao("Localização Teste");
        empreendimento.setDescricao("Descrição do Empreendimento Teste");
        empreendimento.setObservacoes("Observações do Empreendimento Teste");
        return empreendimento;
    }

    public static EmpreendimentoResponse criarEmpreendimentoResponsePadrao() {
        return new EmpreendimentoResponse(
                1,
                "Empreendimento Teste",
                EmpreendimentoStatus.PENDENTE,
                "Localização Teste",
                "Descrição do Empreendimento Teste",
                "Observações do Empreendimento Teste"
        );
    }

    public static EmpreendimentoRequest criarEmpreendimentoRequestAtualizado() {
        return new EmpreendimentoRequest(
                "Empreendimento Atualizado",
                "Localização Atualizada",
                "Descrição do Empreendimento Atualizado",
                "Observações do Empreendimento Atualizado"
        );
    }

    public static Empreendimento criarEmpreendimentoAtualizado() {
        Empreendimento empreendimento = new Empreendimento();
        empreendimento.setId(1);
        empreendimento.setNome("Empreendimento Atualizado");
        empreendimento.setLocalizacao("Localização Atualizada");
        empreendimento.setDescricao("Descrição do Empreendimento Atualizado");
        empreendimento.setObservacoes("Observações do Empreendimento Atualizado");
        return empreendimento;
    }

    public static EmpreendimentoResponse criarEmpreendimentoResponseAtualizado() {
        return new EmpreendimentoResponse(
                1,
                "Empreendimento Atualizado",
                EmpreendimentoStatus.PENDENTE,
                "Localização Atualizada",
                "Descrição do Empreendimento Atualizado",
                "Observações do Empreendimento Atualizado"
        );
    }
}