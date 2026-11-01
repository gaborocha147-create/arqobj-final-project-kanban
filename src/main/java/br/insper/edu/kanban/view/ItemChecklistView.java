package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.ItemChecklist;

import java.util.UUID;

public class ItemChecklistView {
    private UUID id;
    private String descricao;
    private boolean concluido;
    private UUID checklistId;

    public ItemChecklistView() {}

    public ItemChecklistView(ItemChecklist itemChecklist) {
        this.id = itemChecklist.getId();
        this.descricao = itemChecklist.getDescricao();
        this.concluido = itemChecklist.isConcluido();
        this.checklistId = itemChecklist.getChecklist().getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public UUID getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(UUID checklistId) {
        this.checklistId = checklistId;
    }
}
