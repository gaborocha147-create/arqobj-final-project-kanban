package br.insper.edu.kanban.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColunaRepository extends JpaRepository<Coluna, UUID> {}
