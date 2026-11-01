package br.insper.edu.kanban.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembroQuadroRepository extends JpaRepository<MembroQuadro, UUID> {
    Optional<MembroQuadro> findByQuadroIdAndUsuarioEmail(UUID quadroId, String email);
    boolean existsByQuadroIdAndUsuarioId(UUID quadroId, UUID usuarioId);
    long countByQuadroIdAndPapel(UUID quadroId, MembroQuadro.Papel papel);
    List<MembroQuadro> findByQuadroIdOrderById(UUID quadroId);
}
