package br.insper.edu.kanban.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuadroRepository extends JpaRepository<Quadro, UUID> {
    List<Quadro> findByMembrosUsuarioEmailOrderById(String email);
}
