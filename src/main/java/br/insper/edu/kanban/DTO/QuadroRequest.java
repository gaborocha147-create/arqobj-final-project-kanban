package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

public class QuadroRequest {
    @NotBlank
    @Size(max = 255)
    private String nome;

    @Size(max = 255)
    private String descricao;

    public QuadroRequest() {}

    public QuadroRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
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
}
