package br.insper.edu.kanban.model;
import jakarta.persistence.*;

@Entity(name = "Tarefa")
@DiscriminatorValue("TAREFA")
public class Tarefa extends Card {
    private Integer estimativaHoras;
    protected Tarefa() {}
    public Tarefa(String titulo, String descricao, Prioridade prioridade, Integer estimativaHoras) {
        super(titulo, descricao, prioridade);
        definirEstimativa(estimativaHoras);
    }
    public void definirEstimativa(Integer horas) {
        if (horas != null && horas < 1) {
            throw new IllegalArgumentException("A estimativa deve ser de pelo menos 1 hora");
        }
        this.estimativaHoras = horas;
        tocar();
    }

    @Override
    public int esforco() {
        return estimativaHoras == null ? 1 : (int) Math.ceil(estimativaHoras / 4.0);
    }

    @Override
    public String getTipo() {
        return "TAREFA";
    }

    public Integer getEstimativaHoras() {
        return estimativaHoras;
    }
}
