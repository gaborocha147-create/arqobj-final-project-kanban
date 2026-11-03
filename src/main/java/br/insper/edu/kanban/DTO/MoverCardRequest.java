package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class MoverCardRequest {
    @NotNull
    private UUID colunaId;

    @PositiveOrZero
    private Integer posicao;

    public MoverCardRequest() {}

    public MoverCardRequest(UUID colunaId, Integer posicao) {
        this.colunaId = colunaId;
        this.posicao = posicao;
    }

    public UUID getColunaId() {
        return colunaId;
    }

    public void setColunaId(UUID colunaId) {
        this.colunaId = colunaId;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }
}
