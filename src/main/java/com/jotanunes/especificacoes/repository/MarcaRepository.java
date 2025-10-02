package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
}