package br.insper.edu.kanban.DTO;

import br.insper.edu.kanban.model.MembroQuadro;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class MembroQuadroRequest {
    @NotNull
    private UUID usuarioId;
    @NotNull
    private MembroQuadro.Papel papel;

    public MembroQuadroRequest() {}

    public MembroQuadroRequest(UUID usuarioId, MembroQuadro.Papel papel) {
        this.usuarioId = usuarioId;
        this.papel = papel;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public MembroQuadro.Papel getPapel() {
        return papel;
    }

    public void setPapel(MembroQuadro.Papel papel) {
        this.papel = papel;
    }
}
