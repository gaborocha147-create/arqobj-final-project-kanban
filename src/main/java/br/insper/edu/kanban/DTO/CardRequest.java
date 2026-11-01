package br.insper.edu.kanban.DTO;

import br.insper.edu.kanban.model.Prioridade;
import br.insper.edu.kanban.model.TipoCard;

import jakarta.validation.constraints.*;

public class CardRequest {
    @NotBlank
    @Size(max = 255)
    private String titulo;

    @Size(max = 2000)
    private String descricao;

    @NotNull
    private TipoCard tipo;
    @NotNull
    private Prioridade prioridade;
    @Positive
    private Integer estimativaHoras;
    private Boolean critico;

    @Size(max = 2000)
    private String passosReproducao;

    @Positive
    private Integer storyPoints;

    public CardRequest() {}

    public CardRequest(
        String titulo,
        String descricao,
        TipoCard tipo,
        Prioridade prioridade,
        Integer estimativaHoras,
        Boolean critico,
        String passosReproducao,
        Integer storyPoints) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.prioridade = prioridade;
        this.estimativaHoras = estimativaHoras;
        this.critico = critico;
        this.passosReproducao = passosReproducao;
        this.storyPoints = storyPoints;
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

    public TipoCard getTipo() {
        return tipo;
    }

    public void setTipo(TipoCard tipo) {
        this.tipo = tipo;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
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
}
