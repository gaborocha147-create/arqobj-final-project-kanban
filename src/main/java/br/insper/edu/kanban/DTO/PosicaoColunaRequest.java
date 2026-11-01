package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

public class PosicaoColunaRequest {
    @NotNull @PositiveOrZero private Integer posicao;

    public PosicaoColunaRequest() {}

    public PosicaoColunaRequest(Integer posicao) {
        this.posicao = posicao;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }
}
