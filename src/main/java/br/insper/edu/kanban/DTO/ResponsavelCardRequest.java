package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class ResponsavelCardRequest {
    private UUID usuarioId;

    public ResponsavelCardRequest() {}

    public ResponsavelCardRequest(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }
}
