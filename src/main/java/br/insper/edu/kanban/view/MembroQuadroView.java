package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.MembroQuadro;
import br.insper.edu.kanban.usuario.UsuarioView;

import java.time.LocalDateTime;
import java.util.UUID;

public class MembroQuadroView {
    private UUID id;
    private UsuarioView usuario;
    private UUID quadroId;
    private MembroQuadro.Papel papel;
    private LocalDateTime dataEntrada;

    public MembroQuadroView() {}

    public MembroQuadroView(MembroQuadro membroQuadro) {
        this.id = membroQuadro.getId();
        this.usuario = new UsuarioView(membroQuadro.getUsuario());
        this.quadroId = membroQuadro.getQuadro().getId();
        this.papel = membroQuadro.getPapel();
        this.dataEntrada = membroQuadro.getDataEntrada();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UsuarioView getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioView usuario) {
        this.usuario = usuario;
    }

    public UUID getQuadroId() {
        return quadroId;
    }

    public void setQuadroId(UUID quadroId) {
        this.quadroId = quadroId;
    }

    public MembroQuadro.Papel getPapel() {
        return papel;
    }

    public void setPapel(MembroQuadro.Papel papel) {
        this.papel = papel;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
