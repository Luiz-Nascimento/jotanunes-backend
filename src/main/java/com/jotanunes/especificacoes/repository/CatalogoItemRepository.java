package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.CatalogoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogoItemRepository extends JpaRepository<CatalogoItem, Integer> {
     List<CatalogoItem> findByAmbienteId(Integer ambienteId);
}
