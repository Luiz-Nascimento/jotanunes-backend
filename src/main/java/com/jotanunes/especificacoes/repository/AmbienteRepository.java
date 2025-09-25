package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.Ambiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Integer> {

}
