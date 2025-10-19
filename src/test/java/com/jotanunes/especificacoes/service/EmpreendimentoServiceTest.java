package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.exception.ResourceNotFoundException;
import com.jotanunes.especificacoes.mapper.AmbienteMapper;
import com.jotanunes.especificacoes.mapper.EmpreendimentoMapper;
import com.jotanunes.especificacoes.model.Empreendimento;
import com.jotanunes.especificacoes.factory.EmpreendimentoFactory;
import com.jotanunes.especificacoes.repository.EmpreendimentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpreendimentoServiceTest {

    @Mock
    private EmpreendimentoRepository empreendimentoRepository;

    @Mock
    private EmpreendimentoMapper empreendimentoMapper;

    @Mock
    private AmbienteMapper ambienteMapper;

    @InjectMocks
    private EmpreendimentoService empreendimentoService;

    @DisplayName("Deve retornar response de empreendimento por ID com sucesso")
    @Test
    public void deveRetornarEmpreendimentoPorId() {
        Empreendimento empreendimento = EmpreendimentoFactory.criarEmpreendimentoPadrao();
        when(empreendimentoRepository.findById(1)).thenReturn(Optional.of(empreendimento));
        when(empreendimentoMapper.toDto(empreendimento)).thenReturn(new EmpreendimentoResponse(empreendimento.getId(),
                empreendimento.getNome(), empreendimento.getStatus(), empreendimento.getLocalizacao(),
                empreendimento.getDescricao(), empreendimento.getObservacoes()));
        EmpreendimentoResponse response = empreendimentoService.getEmpreendimentoById(1);
        assertEquals(1, response.id());
        assertEquals("Empreendimento Teste", response.nome());
        assertEquals(empreendimento.getStatus(), response.status());
        assertEquals("Localização Teste", response.localizacao());
        assertEquals("Descrição do Empreendimento Teste", response.descricao());
        assertEquals("Observações do Empreendimento Teste", response.observacoes());
        Mockito.verify(empreendimentoRepository).findById(1);
        Mockito.verify(empreendimentoMapper).toDto(empreendimento);
    }

    @DisplayName("Deve lançar ResourceNotFoundException com mensagem correta quando empreendimento não for encontrado por ID")
    @Test
    public void deveLancarExcecaoQuandoEmpreendimentoNaoEncontrado() {
        when(empreendimentoRepository.findById(99)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> empreendimentoService.getEmpreendimentoById(99));
        assertTrue(ex.getMessage().contains("Empreendimento não encontrado com id: 99"));
        Mockito.verify(empreendimentoRepository).findById(99);
    }


}
