package br.insper.edu.kanban.usuario;

import java.util.UUID;

public class UsuarioView {
    private UUID id;
    private String email;

    public UsuarioView() {}

    public UsuarioView(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
