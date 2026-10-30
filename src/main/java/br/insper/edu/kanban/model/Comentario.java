package br.insper.edu.kanban.model;

import br.insper.edu.kanban.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String texto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_card", nullable = false)
    private Card card;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario autor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    protected Comentario() {
    }

    Comentario(String texto, Usuario autor, Card card) {
        if (autor == null || card == null) {
            throw new IllegalArgumentException("Autor e card obrigatórios");
        }

        editar(texto);

        this.autor = autor;
        this.card = card;
        this.dataCriacao = dataAtualizacao;
    }

    public void editar(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Texto obrigatório");
        }

        if (texto.trim().length() > 2000) {
            throw new IllegalArgumentException("Texto muito longo");
        }

        this.texto = texto.trim();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public Card getCard() {
        return card;
    }

    public Usuario getAutor() {
        return autor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}