package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class MoverCardRequest {
    @NotNull
    private UUID colunaId;

    public MoverCardRequest() {}

    public MoverCardRequest(UUID colunaId) {
        this.colunaId = colunaId;
    }

    public UUID getColunaId() {
        return colunaId;
    }

    public void setColunaId(UUID colunaId) {
        this.colunaId = colunaId;
    }
}
