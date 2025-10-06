package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

}
