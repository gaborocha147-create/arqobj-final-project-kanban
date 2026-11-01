package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

public class ItemChecklistRequest {
    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @NotNull
    private Boolean concluido;

    public ItemChecklistRequest() {}

    public ItemChecklistRequest(String descricao, Boolean concluido) {
        this.descricao = descricao;
        this.concluido = concluido;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Boolean getConcluido() {
        return concluido;
    }
    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }
}
