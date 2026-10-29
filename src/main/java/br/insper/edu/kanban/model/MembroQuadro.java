package br.insper.edu.kanban.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class MembroQuadro {
    private String id;
    private String usuario;
    private Quadro quadro;
    private String papel;
    private LocalDateTime dataEntrada;

    public MembroQuadro(String usuario, Quadro quadro, String papel) {
        this.id = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.quadro = quadro;
        this.papel = papel;
        this.dataEntrada = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Quadro getQuadro() {
        return quadro;
    }

    public void setQuadro(Quadro quadro) {
        this.quadro = quadro;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void alterarPapel(String novoPapel) {
        if (novoPapel == null ||
                (!novoPapel.equals("ADMIN") && !novoPapel.equals("MEMBRO"))) {
            throw new IllegalArgumentException("Papel inválido");
        }

        this.papel = novoPapel;
    }
}