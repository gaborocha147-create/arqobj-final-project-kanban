package br.insper.edu.kanban.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_quadro", "nome"}))
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 7)
    private String cor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_quadro", nullable = false)
    private Quadro quadro;

    protected Etiqueta() {}

    public Etiqueta(String nome, String cor, Quadro quadro) {
        this.quadro = quadro;
        editar(nome, cor);
    }

    public void editar(String nome, String cor) {
        if (nome == null || nome.isBlank() || nome.trim().length() > 255)
            throw new IllegalArgumentException(
                    "Nome da etiqueta obrigatório, com até 255 caracteres");
        if (cor == null || !cor.matches("#[0-9a-fA-F]{6}"))
            throw new IllegalArgumentException("Cor deve usar o formato #RRGGBB");
        this.nome = nome.trim();
        this.cor = cor;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public Quadro getQuadro() {
        return quadro;
    }
}
