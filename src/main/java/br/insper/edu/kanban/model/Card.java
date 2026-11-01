package br.insper.edu.kanban.model;

import br.insper.edu.kanban.usuario.Usuario;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Checklist> checklists = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dataCriacao ASC, id ASC")
    private List<Comentario> comentarios = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "card_etiqueta",
            joinColumns = @JoinColumn(name = "id_card"),
            inverseJoinColumns = @JoinColumn(name = "id_etiqueta"))
    private Set<Etiqueta> etiquetas = new HashSet<>();

    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void adicionarEtiqueta(Etiqueta etiqueta) {
        if (!coluna.pertenceAo(etiqueta.getQuadro()))
            throw new IllegalArgumentException("Etiqueta deve pertencer ao quadro do card");
        etiquetas.add(etiqueta);
        tocar();
    }

    public void removerEtiqueta(Etiqueta etiqueta) {
        etiquetas.remove(etiqueta);
        tocar();
    }

    public void removerChecklist(Checklist checklist) {
        checklists.remove(checklist);
        tocar();
    }

    public void removerComentario(Comentario comentario) {
        comentarios.remove(comentario);
        tocar();
    }

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    protected Card() {}

    protected Card(String titulo, String descricao, Prioridade prioridade) {
        editar(titulo, descricao);

        if (prioridade != null) {
            this.prioridade = prioridade;
        }
    }

    public abstract int esforco();

    public abstract String getTipo();

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
        destino.adicionarCard(this);
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
        this.responsavel = usuario;
        tocar();
    }

    public Checklist criarChecklist(String titulo) {
        Checklist checklist = new Checklist(titulo, this);

        checklists.add(checklist);
        tocar();

        return checklist;
    }

    public Comentario adicionarComentario(String texto, Usuario autor) {
        Comentario comentario = new Comentario(texto, autor, this);

        comentarios.add(comentario);
        tocar();

        return comentario;
    }

    protected void tocar() {
        dataAtualizacao = LocalDateTime.now();
    }

    public void setColuna(Coluna coluna) {
        this.coluna = coluna;
    }

    public UUID getId() {
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

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}
