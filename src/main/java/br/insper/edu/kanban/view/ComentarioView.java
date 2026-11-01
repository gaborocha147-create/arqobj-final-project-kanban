package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.Comentario;
import br.insper.edu.kanban.usuario.UsuarioView;

import java.time.LocalDateTime;
import java.util.UUID;

public class ComentarioView {
    private UUID id;
    private String texto;
    private UUID cardId;
    private UsuarioView autor;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public ComentarioView() {}

    public ComentarioView(Comentario comentario) {
        this.id = comentario.getId();
        this.texto = comentario.getTexto();
        this.cardId = comentario.getCard().getId();
        this.autor = new UsuarioView(comentario.getAutor());
        this.dataCriacao = comentario.getDataCriacao();
        this.dataAtualizacao = comentario.getDataAtualizacao();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public UsuarioView getAutor() {
        return autor;
    }

    public void setAutor(UsuarioView autor) {
        this.autor = autor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
