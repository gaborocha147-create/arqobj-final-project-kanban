package br.insper.edu.kanban.usuario;

import jakarta.validation.constraints.*;

public class CadastroUsuarioRequest {
    @NotBlank
    @Email
    @Size(max = 254)
    private String email;

    @NotBlank
    @Size(max = 72)
    private String senha;

    public CadastroUsuarioRequest() {}

    public CadastroUsuarioRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
