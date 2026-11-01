package br.insper.edu.kanban.DTO;

import jakarta.validation.constraints.*;

public class ComentarioRequest {
    @NotBlank
    @Size(max = 2000)
    private String texto;
    public ComentarioRequest() {}
    public ComentarioRequest(String texto) {
        this.texto = texto;
    }
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
