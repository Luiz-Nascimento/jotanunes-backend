package com.jotanunes.especificacoes.service;

import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
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
import static org.mockito.Mockito.*;

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
        EmpreendimentoResponse expectedResponse = EmpreendimentoFactory.criarEmpreendimentoResponsePadrao();

        when(empreendimentoRepository.findById(1)).thenReturn(Optional.of(empreendimento));
        when(empreendimentoMapper.toDto(empreendimento)).thenReturn(expectedResponse);

        EmpreendimentoResponse response = empreendimentoService.getEmpreendimentoById(1);

        assertEquals(expectedResponse, response);

        verify(empreendimentoRepository).findById(1);
        verify(empreendimentoMapper).toDto(empreendimento);
    }

    @DisplayName("Deve lançar exceção quando empreendimento não for encontrado por ID")
    @Test
    public void deveLancarExcecaoQuandoEmpreendimentoNaoEncontrado() {
        when(empreendimentoRepository.findById(99)).thenReturn(Optional.empty());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> empreendimentoService.getEmpreendimentoById(99));
        assertTrue(ex.getMessage().contains("Empreendimento não encontrado com id: 99"));
        verify(empreendimentoRepository).findById(99);
    }

    @DisplayName("Deve criar um novo empreendimento com sucesso")
    @Test
    public void deveCriarUmNovoEmpreendimento() {
        EmpreendimentoRequest request = EmpreendimentoFactory.criarEmpreendimentoRequestPadrao();
        Empreendimento mappedEntity = EmpreendimentoFactory.criarEmpreendimentoMapeadoPadrao();
        Empreendimento persistedEntity = EmpreendimentoFactory.criarEmpreendimentoPadrao();
        EmpreendimentoResponse expectedResponse = EmpreendimentoFactory.criarEmpreendimentoResponsePadrao();

        when(empreendimentoMapper.requestToEntity(request)).thenReturn(mappedEntity);
        when(empreendimentoRepository.save(mappedEntity)).thenReturn(persistedEntity);
        when(empreendimentoMapper.toDto(persistedEntity)).thenReturn(expectedResponse);

        EmpreendimentoResponse response = empreendimentoService.createEmpreendimento(request);

        assertEquals(expectedResponse, response);
        verify(empreendimentoMapper).requestToEntity(request);
        verify(empreendimentoRepository).save(mappedEntity);
        verify(empreendimentoMapper).toDto(persistedEntity);


    }

    @DisplayName("Deve lançar exceção ao falhar na criação de empreendimento")
    @Test
    public void deveLancarExcecaoAoFalharCriacaoDeEmpreendimento() {
        EmpreendimentoRequest request = EmpreendimentoFactory.criarEmpreendimentoRequestPadrao();
        Empreendimento mappedEntity = EmpreendimentoFactory.criarEmpreendimentoMapeadoPadrao();

        when(empreendimentoMapper.requestToEntity(request)).thenReturn(mappedEntity);
        when(empreendimentoRepository.save(mappedEntity)).thenThrow(new RuntimeException());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            empreendimentoService.createEmpreendimento(request);
        });

        verify(empreendimentoMapper).requestToEntity(request);
        verify(empreendimentoRepository).save(mappedEntity);
        verify(empreendimentoMapper, never()).toDto(any(Empreendimento.class));
    }

    @DisplayName("Deve atualizar um empreendimento existente com sucesso")
    @Test
    public void deveAtualizarEmpreendimento() {
        EmpreendimentoRequest updateRequest = EmpreendimentoFactory.criarEmpreendimentoRequestAtualizado();
        Empreendimento existingEntity = EmpreendimentoFactory.criarEmpreendimentoPadrao();
        Empreendimento updatedEntity = EmpreendimentoFactory.criarEmpreendimentoAtualizado();
        EmpreendimentoResponse expectedResponse = EmpreendimentoFactory.criarEmpreendimentoResponseAtualizado();

        when(empreendimentoRepository.findById(1)).thenReturn(Optional.of(existingEntity));
        when(empreendimentoRepository.save(existingEntity)).thenReturn(updatedEntity);
        when(empreendimentoMapper.toDto(updatedEntity)).thenReturn(expectedResponse);

        EmpreendimentoResponse response = empreendimentoService.updateEmpreendimento(1, updateRequest);

        assertEquals(expectedResponse, response);
        verify(empreendimentoRepository).findById(1);
        verify(empreendimentoRepository).save(existingEntity);
        verify(empreendimentoMapper).toDto(updatedEntity);

    }

    @DisplayName("Deve deletar um empreendimento existente com sucesso")
    @Test
    public void deveDeletarUmEmpreendimento() {
        when(empreendimentoRepository.existsById(1)).thenReturn(true);
        doNothing().when(empreendimentoRepository).deleteById(1);

        empreendimentoService.deleteEmpreendimento(1);

        verify(empreendimentoRepository).existsById(1);
        verify(empreendimentoRepository).deleteById(1);
    }

    @DisplayName("Deve lançar exceção ao tentar deletar empreendimento inexistente")
    @Test
    public void deveLancarExcecaoAoDeletarEmpreendimentoInexistente() {
        when(empreendimentoRepository.existsById(99)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            empreendimentoService.deleteEmpreendimento(99);
        });

        assertTrue(exception.getMessage().contains("Empreendimento não encontrado com id: 99"));
        verify(empreendimentoRepository).existsById(99);
        verify(empreendimentoRepository, never()).deleteById(anyInt());
    }
}
