package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.Ambiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Integer> {
    List<Ambiente> findByEmpreendimentoId(Integer id);
    long countByEmpreendimentoId(Integer empreendimentoId);
}
