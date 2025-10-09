package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.ItemChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemChecklistRepository extends JpaRepository<ItemChecklist, Integer> {
}
