package br.insper.edu.kanban.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Quadro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "quadro", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("posicao")
    private List<Coluna> colunas = new ArrayList<>();

    @OneToMany(mappedBy = "quadro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MembroQuadro> membros = new ArrayList<>();

    @OneToMany(mappedBy = "quadro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etiqueta> etiquetas = new ArrayList<>();

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public Quadro() {}

    public Quadro(String nome, String descricao) {
        renomear(nome);
        this.descricao = descricao;
    }

    public Coluna adicionarColuna(String nome, Integer limiteWip) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da coluna é obrigatório");
        }
        for (Coluna coluna : colunas) {
            if (coluna.getNome().equalsIgnoreCase(nome.trim())) {
                throw new IllegalStateException("Já existe uma coluna chamada " + nome.trim());
            }
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
        int total = 0;
        for (Coluna coluna : colunas) {
            total += coluna.quantidadeCards();
        }
        return total;
    }

    public void renomear(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do quadro é obrigatório");
        }
        this.nome = nome.trim();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        renomear(nome);
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
