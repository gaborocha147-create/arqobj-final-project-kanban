package br.insper.edu.kanban.model;

import jakarta.persistence.*;

@Entity
public class ItemChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição obrigatória");
        }

        if (descricao.trim().length() > 1000) {
            throw new IllegalArgumentException("Descrição muito longa");
        }

        if (checklist == null) {
            throw new IllegalArgumentException("Checklist obrigatório");
        }

        this.descricao = descricao.trim();
        this.checklist = checklist;
    }

    public void concluir() {
        concluido = true;
    }

    public void reabrir() {
        concluido = false;
    }

    public Long getId() {
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