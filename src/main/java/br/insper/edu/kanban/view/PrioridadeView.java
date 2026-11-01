package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.Prioridade;

public class PrioridadeView {
    private Prioridade valor;

    public PrioridadeView() {}

    public PrioridadeView(Prioridade valor) {
        this.valor = valor;
    }

    public Prioridade getValor() {
        return valor;
    }

    public void setValor(Prioridade valor) {
        this.valor = valor;
    }
}
