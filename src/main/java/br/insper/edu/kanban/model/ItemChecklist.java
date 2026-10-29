package br.insper.edu.kanban.model;

import java.util.UUID;

public class ItemChecklist {
    private String id;
    private String titulo;
    private String cor;
    private boolean concluido;
    private int ordem;

    public ItemChecklist(String titulo, String cor, int ordem) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.cor = cor;
        this.concluido = false;
        this.ordem = ordem;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public void marcarComoConcluido() {
        this.concluido = true;
    }

    public void marcarComoPendente() {
        this.concluido = false;
    }
}