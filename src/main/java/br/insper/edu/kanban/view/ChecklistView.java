package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.Checklist;
import br.insper.edu.kanban.model.ItemChecklist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChecklistView {
    private UUID id;
    private String titulo;
    private UUID cardId;
    private double percentualConcluido;
    private List<ItemChecklistView> itens;

    public ChecklistView() {}

    public ChecklistView(Checklist checklist) {
        this.id = checklist.getId();
        this.titulo = checklist.getTitulo();
        this.cardId = checklist.getCard().getId();
        this.percentualConcluido = checklist.percentualConcluido();
        this.itens = new ArrayList<>();
        for (ItemChecklist item : checklist.getItens()) {
            this.itens.add(new ItemChecklistView(item));
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public double getPercentualConcluido() {
        return percentualConcluido;
    }

    public void setPercentualConcluido(double percentualConcluido) {
        this.percentualConcluido = percentualConcluido;
    }

    public List<ItemChecklistView> getItens() {
        return itens;
    }

    public void setItens(List<ItemChecklistView> itens) {
        this.itens = itens;
    }
}
