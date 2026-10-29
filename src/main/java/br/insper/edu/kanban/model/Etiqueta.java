package br.insper.edu.kanban.model;

import java.util.List;

public class Etiqueta {
    private int id;
    private String nome;
    private String cor;

    public Etiqueta(int id, String nome, String cor) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
    }

    public static List<Etiqueta> etiquetasPadrao() {
        return List.of(
                new Etiqueta(1, "Muito Baixa", "#4CAF50"),
                new Etiqueta(2, "Baixa", "#2196F3"),
                new Etiqueta(3, "Média", "#FFC107"),
                new Etiqueta(4, "Alta", "#FF9800"),
                new Etiqueta(5, "Urgente", "#F44336")
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}