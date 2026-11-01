package br.insper.edu.kanban.model;

import jakarta.persistence.*;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Coluna {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    private int posicao;

    private Integer limiteWip;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_quadro")
    private Quadro quadro;

    @OneToMany(mappedBy = "coluna", cascade = CascadeType.ALL)
    @OrderBy("id")
    private List<Card> cards = new ArrayList<>();

    protected Coluna() {}

    Coluna(String nome, int posicao, Integer limiteWip) {
        this.nome = nome;
        this.posicao = posicao;
        definirLimiteWip(limiteWip);
    }

    public boolean estaCheia() {
        return limiteWip != null && cards.size() >= limiteWip;
    }

    public void definirLimiteWip(Integer novoLimite) {
        if (novoLimite != null && novoLimite < 1) {
            throw new IllegalArgumentException("O limite WIP deve ser maior que zero");
        }
        if (novoLimite != null && novoLimite < cards.size()) {
            throw new IllegalStateException("A coluna já tem " + cards.size() + " cards; o limite não pode ser menor que isso");
        }
        this.limiteWip = novoLimite;
    }

    public void adicionarCard(Card card) {
        if (cards.contains(card)) {
            return;
        }
        if (estaCheia()) {
            throw new IllegalStateException("A coluna " + nome + " atingiu o limite WIP de " + limiteWip + " cards");
        }
        card.setColuna(this);
        cards.add(card);
    }

    public void removerCard(Card card) {
        cards.remove(card);
    }

    public boolean pertenceAo(Quadro outro) {
        return quadro != null && outro != null && Objects.equals(quadro.getId(), outro.getId());
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    public Integer getLimiteWip() {
        return limiteWip;
    }

    public int quantidadeCards() {
        return cards.size();
    }

    public void renomear(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da coluna é obrigatório");
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
        this.nome = nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public Quadro getQuadro() {
        return quadro;
    }

    public void setQuadro(Quadro quadro) {
        this.quadro = quadro;
    }
}
