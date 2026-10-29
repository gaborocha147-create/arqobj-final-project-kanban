package br.insper.edu.kanban.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Quadro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "quadro", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("posicao")
    private List<Coluna> colunas = new ArrayList<>();

    public Quadro() {}

    public Quadro(String nome, String descricao) {
        renomear(nome);
        this.descricao = descricao;
    }

    public Coluna adicionarColuna(String nome, Integer limiteWip) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da coluna é obrigatório");
        }
        boolean repetido = colunas.stream().anyMatch(c -> c.getNome().equalsIgnoreCase(nome.trim()));
        if (repetido) {
            throw new IllegalStateException("Já existe uma coluna chamada " + nome.trim() + " neste quadro");
        }

        Coluna coluna = new Coluna(nome.trim(), colunas.size(), limiteWip);
        coluna.setQuadro(this);
        colunas.add(coluna);
        return coluna;
    }

    public void removerColuna(Coluna coluna) {
        if (!coluna.pertenceAo(this)) {
            throw new IllegalArgumentException("A coluna não pertence a este quadro");
        }
        if (coluna.quantidadeCards() > 0) {
            throw new IllegalStateException("Só é possível remover colunas vazias");
        }
        colunas.remove(coluna);
        for (int i = 0; i < colunas.size(); i++) {
            colunas.get(i).setPosicao(i);
        }
    }

    public int quantidadeCards() {
        return colunas.stream().mapToInt(Coluna::quantidadeCards).sum();
    }

    public void renomear(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do quadro é obrigatório");
        }
        this.nome = nome.trim();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Coluna> getColunas() {
        return colunas;
    }

    public void setColunas(List<Coluna> colunas) {
        this.colunas = colunas;
    }
}
