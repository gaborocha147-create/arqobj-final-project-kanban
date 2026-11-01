package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.Etiqueta;

import java.util.UUID;

public class EtiquetaView {
    private UUID id;
    private String nome;
    private String cor;
    private UUID quadroId;

    public EtiquetaView() {}

    public EtiquetaView(Etiqueta etiqueta) {
        this.id = etiqueta.getId();
        this.nome = etiqueta.getNome();
        this.cor = etiqueta.getCor();
        this.quadroId = etiqueta.getQuadro().getId();
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public UUID getQuadroId() {
        return quadroId;
    }

    public void setQuadroId(UUID quadroId) {
        this.quadroId = quadroId;
    }
}
