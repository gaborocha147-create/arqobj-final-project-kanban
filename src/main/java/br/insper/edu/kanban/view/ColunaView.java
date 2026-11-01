package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.Coluna;

import java.util.UUID;

public class ColunaView {
    private UUID id;
    private String nome;
    private int posicao;
    private Integer limiteWip;
    private UUID quadroId;
    private int quantidadeCards;

    public ColunaView() {}

    public ColunaView(Coluna coluna) {
        this.id = coluna.getId();
        this.nome = coluna.getNome();
        this.posicao = coluna.getPosicao();
        this.limiteWip = coluna.getLimiteWip();
        this.quadroId = coluna.getQuadro().getId();
        this.quantidadeCards = coluna.quantidadeCards();
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

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public Integer getLimiteWip() {
        return limiteWip;
    }

    public void setLimiteWip(Integer limiteWip) {
        this.limiteWip = limiteWip;
    }

    public UUID getQuadroId() {
        return quadroId;
    }

    public void setQuadroId(UUID quadroId) {
        this.quadroId = quadroId;
    }

    public int getQuantidadeCards() {
        return quantidadeCards;
    }

    public void setQuantidadeCards(int quantidadeCards) {
        this.quantidadeCards = quantidadeCards;
    }
}
