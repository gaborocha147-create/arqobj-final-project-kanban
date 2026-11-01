package br.insper.edu.kanban.DTO;

import br.insper.edu.kanban.model.Prioridade;

import jakarta.validation.constraints.*;

public class PrioridadeCardRequest {
    @NotNull
    private Prioridade prioridade;

    public PrioridadeCardRequest() {}

    public PrioridadeCardRequest(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
}
