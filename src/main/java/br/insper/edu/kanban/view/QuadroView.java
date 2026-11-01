package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.Quadro;

import java.util.UUID;

public class QuadroView {
    private UUID id;
    private String nome;
    private String descricao;
    private int quantidadeCards;

    public QuadroView() {}

    public QuadroView(Quadro quadro) {
        this.id = quadro.getId();
        this.nome = quadro.getNome();
        this.descricao = quadro.getDescricao();
        this.quantidadeCards = quadro.quantidadeCards();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidadeCards() {
        return quantidadeCards;
    }

    public void setQuantidadeCards(int quantidadeCards) {
        this.quantidadeCards = quantidadeCards;
    }
}
