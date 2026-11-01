package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

public class ChecklistRequest {
    @NotBlank
    @Size(max = 255)
    private String titulo;

    public ChecklistRequest() {}

    public ChecklistRequest(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
