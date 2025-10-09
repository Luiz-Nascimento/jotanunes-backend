package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.RevisaoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevisaoItemRepository extends JpaRepository<RevisaoItem, Integer> {
}
