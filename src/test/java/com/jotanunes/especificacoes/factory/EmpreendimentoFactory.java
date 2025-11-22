
package com.jotanunes.especificacoes.factory;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoUpdate;
import com.jotanunes.especificacoes.enums.*;
import com.jotanunes.especificacoes.model.Empreendimento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmpreendimentoFactory {

    private EmpreendimentoFactory() {}

    public static Empreendimento criarEmpreendimentoPadrao() {
        Empreendimento empreendimento = new Empreendimento();
        empreendimento.setId(1);
        empreendimento.setSegmento(SegmentoEmpreendimento.TORRE_PC_MAIS_VIVER);
        empreendimento.setNome("Empreendimento Teste");
        empreendimento.setLocalizacao("Localização Teste");
        empreendimento.setDescricao("Descrição do Empreendimento Teste");
        return empreendimento;
    }

    public static EmpreendimentoRequest criarEmpreendimentoRequestPadrao() {
        return new EmpreendimentoRequest(
                SegmentoEmpreendimento.TORRE_PC_MAIS_VIVER,
                "Empreendimento Teste",
                "Localização Teste",
                "Descrição do Empreendimento Teste"
        );
    }

    public static Empreendimento criarEmpreendimentoMapeadoPadrao() {
        Empreendimento empreendimento = new Empreendimento();
        empreendimento.setSegmento(SegmentoEmpreendimento.TORRE_PC_MAIS_VIVER);
        empreendimento.setNome("Empreendimento Teste");
        empreendimento.setLocalizacao("Localização Teste");
        empreendimento.setDescricao("Descrição do Empreendimento Teste");
        return empreendimento;
    }

    public static EmpreendimentoResponse criarEmpreendimentoResponsePadrao() {
        List<String> observacoes = new ArrayList<>();
        return new EmpreendimentoResponse(
                1,
                SegmentoEmpreendimento.TORRE_PC_MAIS_VIVER,
                "Empreendimento Teste",
                EmpreendimentoStatus.PENDENTE,
                "Localização Teste",
                "Descrição do Empreendimento Teste",
                observacoes,
                "admin",
                LocalDateTime.now(),
                1,
                10,
                6,
                5
        );
    }

    public static EmpreendimentoUpdate criarEmpreendimentoUpdate() {
        return new EmpreendimentoUpdate(
                "Empreendimento Atualizado",
                "Localização Atualizada",
                "Descrição do Empreendimento Atualizado"
        );
    }

    public static Empreendimento criarEmpreendimentoAtualizado() {
        Empreendimento empreendimento = new Empreendimento();
        empreendimento.setId(1);
        empreendimento.setNome("Empreendimento Atualizado");
        empreendimento.setLocalizacao("Localização Atualizada");
        empreendimento.setDescricao("Descrição do Empreendimento Atualizado");
        return empreendimento;
    }

    public static EmpreendimentoResponse criarEmpreendimentoResponseAtualizado() {
        List<String> observacoes = new ArrayList<>();
        return new EmpreendimentoResponse(
                1,
                SegmentoEmpreendimento.TORRE_PC_MAIS_VIVER,
                "Empreendimento Atualizado",
                EmpreendimentoStatus.PENDENTE,
                "Localização Atualizada",
                "Descrição do Empreendimento Atualizado",
                observacoes,
                "admin",
                LocalDateTime.now(),
                10,
                50,
                10,
                10
        );
    }
}