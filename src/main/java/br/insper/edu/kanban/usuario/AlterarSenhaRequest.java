package br.insper.edu.kanban.usuario;

import jakarta.validation.constraints.*;

public class AlterarSenhaRequest {
    @NotBlank
    @Size(max = 72)
    private String senhaAtual;

    @NotBlank
    @Size(max = 72)
    private String novaSenha;

    public AlterarSenhaRequest() {}

    public AlterarSenhaRequest(String senhaAtual, String novaSenha) {
        this.senhaAtual = senhaAtual;
        this.novaSenha = novaSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
