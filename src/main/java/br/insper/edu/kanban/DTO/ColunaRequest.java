package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

public class ColunaRequest {
    @NotBlank
    @Size(max = 255)
    private String nome;

    @Positive
    private Integer limiteWip;

    public ColunaRequest() {}

    public ColunaRequest(String nome, Integer limiteWip) {
        this.nome = nome;
        this.limiteWip = limiteWip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getLimiteWip() {
        return limiteWip;
    }

    public void setLimiteWip(Integer limiteWip) {
        this.limiteWip = limiteWip;
    }
}
