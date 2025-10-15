package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.CatalogoAmbiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoAmbienteRepository extends JpaRepository<CatalogoAmbiente, Integer> {
}
