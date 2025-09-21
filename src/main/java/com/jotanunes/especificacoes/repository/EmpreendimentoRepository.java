package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.Empreendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpreendimentoRepository extends JpaRepository<Empreendimento, Integer> {

}
