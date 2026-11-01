package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

public class EtiquetaRequest {
    @NotBlank
    @Size(max = 255)
    private String nome;

    @NotNull
    @Pattern(regexp = "#[0-9a-fA-F]{6}")
    private String cor;
    public EtiquetaRequest() {}
    public EtiquetaRequest(String nome, String cor) {
        this.nome = nome;
        this.cor = cor;
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
