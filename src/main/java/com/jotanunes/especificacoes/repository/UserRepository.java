package com.jotanunes.especificacoes.repository;

import com.jotanunes.especificacoes.enums.NivelAcesso;
import com.jotanunes.especificacoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.email FROM User u WHERE u.nivelAcesso = 'GESTOR' AND u.ativo = true")
    List<String> findEmailGestoresAtivos();

    List<User> findByNivelAcessoAndAtivoTrue(NivelAcesso nivelAcesso);
}
