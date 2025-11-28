package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.RevisaoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevisaoItemRepository extends JpaRepository<RevisaoItem, Integer> {
    List<RevisaoItem> findByItemAmbienteEmpreendimentoId(Integer idEmpreendimento);
}
