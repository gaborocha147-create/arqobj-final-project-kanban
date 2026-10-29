package br.insper.edu.kanban.model;

import br.insper.edu.kanban.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 2000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade = Prioridade.MEDIA;

    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private Usuario responsavel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_coluna")
    private Coluna coluna;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    protected Card() {
    }

    public Card(String titulo, String descricao, Prioridade prioridade) {
        editar(titulo, descricao);
        if (prioridade != null) {
            this.prioridade = prioridade;
        }
    }

    @PrePersist
    void aoCriar() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = dataCriacao;
    }

    public void moverPara(Coluna destino) {
        if (destino == null) {
            throw new IllegalArgumentException("Coluna de destino obrigatória");
        }
        if (destino == coluna) {
            throw new IllegalStateException("O card já está na coluna " + destino.getNome());
        }
        if (!destino.pertenceAo(coluna.getQuadro())) {
            throw new IllegalArgumentException("Não é possível mover o card para outro quadro");
        }

        Coluna origem = coluna;
        destino.adicionarCard(this);   // valida o WIP antes de mexer na origem
        origem.removerCard(this);
        tocar();
    }

    public void editar(String titulo, String descricao) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título do card é obrigatório");
        }
        this.titulo = titulo.trim();
        this.descricao = descricao;
        tocar();
    }

    public void alterarPrioridade(Prioridade prioridade) {
        if (prioridade == null) {
            throw new IllegalArgumentException("Prioridade obrigatória");
        }
        this.prioridade = prioridade;
        tocar();
    }

    public void atribuirResponsavel(Usuario usuario) {
        this.responsavel = usuario;   // null remove o responsável
        tocar();
    }

    private void tocar() {
        dataAtualizacao = LocalDateTime.now();
    }

    void setColuna(Coluna coluna) {
        this.coluna = coluna;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public Coluna getColuna() {
        return coluna;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}