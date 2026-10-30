package br.insper.edu.kanban.model;

import br.insper.edu.kanban.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Card {

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

    @OneToMany(
            mappedBy = "card",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("id ASC")
    private List<Checklist> checklists = new ArrayList<>();

    @OneToMany(
            mappedBy = "card",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("dataCriacao ASC, id ASC")
    private List<Comentario> comentarios = new ArrayList<>();

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    protected Card() {
    }

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
            throw new IllegalArgumentException(
                    "Coluna de destino obrigatória"
            );
        }

        if (destino == coluna) {
            throw new IllegalStateException(
                    "O card já está na coluna " + destino.getNome()
            );
        }

        if (!destino.pertenceAo(coluna.getQuadro())) {
            throw new IllegalArgumentException(
                    "Não é possível mover o card para outro quadro"
            );
        }

        Coluna origem = coluna;

        // Valida o WIP antes de mexer na origem.
        destino.adicionarCard(this);
        origem.removerCard(this);

        tocar();
    }

    public void editar(String titulo, String descricao) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException(
                    "O título do card é obrigatório"
            );
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
        // null remove o responsável.
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

    public List<Checklist> getChecklists() {
        return List.copyOf(checklists);
    }

    public List<Comentario> getComentarios() {
        return List.copyOf(comentarios);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    @Entity(name = "Tarefa")
    @DiscriminatorValue("TAREFA")
    public static class Tarefa extends Card {

        private Integer estimativaHoras;

        protected Tarefa() {
        }

        public Tarefa(
                String titulo,
                String descricao,
                Prioridade prioridade,
                Integer estimativaHoras
        ) {
            super(titulo, descricao, prioridade);
            definirEstimativa(estimativaHoras);
        }

        public void definirEstimativa(Integer horas) {
            if (horas != null && horas < 1) {
                throw new IllegalArgumentException(
                        "A estimativa deve ser de pelo menos 1 hora"
                );
            }

            this.estimativaHoras = horas;
        }

        @Override
        public int esforco() {
            return estimativaHoras == null
                    ? 1
                    : (int) Math.ceil(estimativaHoras / 4.0);
        }

        @Override
        public String getTipo() {
            return "TAREFA";
        }

        public Integer getEstimativaHoras() {
            return estimativaHoras;
        }
    }

    @Entity(name = "Bug")
    @DiscriminatorValue("BUG")
    public static class Bug extends Card {

        private boolean critico;

        @Column(length = 2000)
        private String passosReproducao;

        protected Bug() {
        }

        public Bug(
                String titulo,
                String descricao,
                Prioridade prioridade,
                boolean critico,
                String passosReproducao
        ) {
            super(
                    titulo,
                    descricao,
                    critico ? Prioridade.ALTA : prioridade
            );

            this.critico = critico;
            this.passosReproducao = passosReproducao;
        }

        @Override
        public void alterarPrioridade(Prioridade prioridade) {
            if (isCritico() && prioridade != Prioridade.ALTA) {
                throw new IllegalStateException(
                        "Bug crítico deve ter prioridade ALTA"
                );
            }

            super.alterarPrioridade(prioridade);
        }

        public void marcarComoCritico() {
            this.critico = true;
            super.alterarPrioridade(Prioridade.ALTA);
        }

        @Override
        public int esforco() {
            return isCritico() ? 5 : 2;
        }

        @Override
        public String getTipo() {
            return "BUG";
        }

        public boolean isCritico() {
            return Boolean.TRUE.equals(critico);
        }

        public String getPassosReproducao() {
            return passosReproducao;
        }
    }

    @Entity(name = "Melhoria")
    @DiscriminatorValue("MELHORIA")
    public static class Melhoria extends Card {

        private static final Set<Integer> FIBONACCI =
                Set.of(1, 2, 3, 5, 8, 13);

        private Integer storyPoints;

        protected Melhoria() {
        }

        public Melhoria(
                String titulo,
                String descricao,
                Prioridade prioridade,
                Integer storyPoints
        ) {
            super(titulo, descricao, prioridade);
            definirStoryPoints(storyPoints);
        }

        public void definirStoryPoints(Integer pontos) {
            if (pontos == null || !FIBONACCI.contains(pontos)) {
                throw new IllegalArgumentException(
                        "Story points devem ser 1, 2, 3, 5, 8 ou 13"
                );
            }

            this.storyPoints = pontos;
        }

        @Override
        public int esforco() {
            return storyPoints;
        }

        @Override
        public String getTipo() {
            return "MELHORIA";
        }

        public Integer getStoryPoints() {
            return storyPoints;
        }
    }
}