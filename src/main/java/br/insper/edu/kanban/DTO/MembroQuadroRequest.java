package br.insper.edu.kanban.DTO;

import br.insper.edu.kanban.model.MembroQuadro;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class MembroQuadroRequest {
    @NotBlank
    @Email
    @Size(max = 254)
    private String email;
    @NotNull
    private MembroQuadro.Papel papel;

    public MembroQuadroRequest() {}

    public MembroQuadroRequest(String email, MembroQuadro.Papel papel) {
        this.email = email;
        this.papel = papel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MembroQuadro.Papel getPapel() {
        return papel;
    }

    public void setPapel(MembroQuadro.Papel papel) {
        this.papel = papel;
    }
}
