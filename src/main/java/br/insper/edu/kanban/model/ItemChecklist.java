package br.insper.edu.kanban.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class ItemChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(nullable = false)
    private boolean concluido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_checklist", nullable = false)
    private Checklist checklist;

    protected ItemChecklist() {
    }

    ItemChecklist(String descricao, Checklist checklist) {
        editar(descricao, false);

        if (checklist == null) {
            throw new IllegalArgumentException("Checklist obrigatório");
        }

        this.checklist = checklist;
    }

    public void editar(String descricao, boolean concluido) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição obrigatória");
        }

        if (descricao.trim().length() > 1000) {
            throw new IllegalArgumentException("Descrição muito longa");
        }

        this.descricao = descricao.trim();
        this.concluido = concluido;
    }

    public void concluir() {
        concluido = true;
    }

    public void reabrir() {
        concluido = false;
    }

    public UUID getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public Checklist getChecklist() {
        return checklist;
    }
}
