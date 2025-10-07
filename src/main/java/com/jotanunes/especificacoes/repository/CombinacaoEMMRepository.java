package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.CombinacaoEMM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombinacaoEMMRepository extends JpaRepository<CombinacaoEMM, Integer> {
    List<CombinacaoEMM> findByEmpreendimentoId(Integer empreendimentoId);
}
