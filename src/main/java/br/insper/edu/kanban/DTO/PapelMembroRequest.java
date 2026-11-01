package br.insper.edu.kanban.DTO;

import br.insper.edu.kanban.model.MembroQuadro;

import jakarta.validation.constraints.*;

public class PapelMembroRequest {
    @NotNull
    private MembroQuadro.Papel papel;

    public PapelMembroRequest() {}

    public PapelMembroRequest(MembroQuadro.Papel papel) {
        this.papel = papel;
    }

    public MembroQuadro.Papel getPapel() {
        return papel;
    }

    public void setPapel(MembroQuadro.Papel papel) {
        this.papel = papel;
    }
}
