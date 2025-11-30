package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.historicoRevisoes WHERE i.ambiente.id = :id")
    List<Item> findByAmbienteId(Integer id);
    long countByAmbiente_Empreendimento_Id(Integer idEmpreendimento);
}
