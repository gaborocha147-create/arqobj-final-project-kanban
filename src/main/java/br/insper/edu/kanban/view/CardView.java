package br.insper.edu.kanban.view;

import br.insper.edu.kanban.model.Bug;
import br.insper.edu.kanban.model.Card;
import br.insper.edu.kanban.model.Etiqueta;
import br.insper.edu.kanban.model.Melhoria;
import br.insper.edu.kanban.model.Prioridade;
import br.insper.edu.kanban.model.Tarefa;
import br.insper.edu.kanban.usuario.UsuarioView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardView {
    private UUID id;
    private String titulo;
    private String descricao;
    private String tipo;
    private Prioridade prioridade;
    private int esforco;
    private UUID colunaId;
    private UsuarioView responsavel;
    private Integer estimativaHoras;
    private Boolean critico;
    private String passosReproducao;
    private Integer storyPoints;
    private List<EtiquetaView> etiquetas;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public CardView() {}

    public CardView(Card card) {
        this.id = card.getId();
        this.titulo = card.getTitulo();
        this.descricao = card.getDescricao();
        this.tipo = card.getTipo();
        this.prioridade = card.getPrioridade();
        this.esforco = card.esforco();
        this.colunaId = card.getColuna().getId();
        this.dataCriacao = card.getDataCriacao();
        this.dataAtualizacao = card.getDataAtualizacao();
        if (card.getResponsavel() != null) {
            this.responsavel = new UsuarioView(card.getResponsavel());
        }
        if (card instanceof Tarefa) {
            Tarefa tarefa = (Tarefa) card;
            this.estimativaHoras = tarefa.getEstimativaHoras();
        } else if (card instanceof Bug) {
            Bug bug = (Bug) card;
            this.critico = bug.isCritico();
            this.passosReproducao = bug.getPassosReproducao();
        } else if (card instanceof Melhoria) {
            Melhoria melhoria = (Melhoria) card;
            this.storyPoints = melhoria.getStoryPoints();
        }
        this.etiquetas = new ArrayList<>();
        for (Etiqueta etiqueta : card.getEtiquetas()) {
            this.etiquetas.add(new EtiquetaView(etiqueta));
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public int getEsforco() {
        return esforco;
    }

    public void setEsforco(int esforco) {
        this.esforco = esforco;
    }

    public UUID getColunaId() {
        return colunaId;
    }

    public void setColunaId(UUID colunaId) {
        this.colunaId = colunaId;
    }

    public UsuarioView getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(UsuarioView responsavel) {
        this.responsavel = responsavel;
    }

    public Integer getEstimativaHoras() {
        return estimativaHoras;
    }

    public void setEstimativaHoras(Integer estimativaHoras) {
        this.estimativaHoras = estimativaHoras;
    }

    public Boolean getCritico() {
        return critico;
    }

    public void setCritico(Boolean critico) {
        this.critico = critico;
    }

    public String getPassosReproducao() {
        return passosReproducao;
    }

    public void setPassosReproducao(String passosReproducao) {
        this.passosReproducao = passosReproducao;
    }

    public Integer getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public List<EtiquetaView> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<EtiquetaView> etiquetas) {
        this.etiquetas = etiquetas;
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
