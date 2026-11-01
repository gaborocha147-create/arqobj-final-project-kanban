package br.insper.edu.kanban.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity(name = "Melhoria")
@DiscriminatorValue("MELHORIA")
public class Melhoria extends Card {

    private static final Set<Integer> PONTOS_PERMITIDOS =
            Set.of(1, 2, 3, 4, 5, 8);

    private Integer storyPoints;

    protected Melhoria() {}

    public Melhoria(String titulo, String descricao,
                    Prioridade prioridade, Integer storyPoints) {
        super(titulo, descricao, prioridade);
        definirStoryPoints(storyPoints);
    }

    public void definirStoryPoints(Integer pontos) {
        if (pontos == null || !PONTOS_PERMITIDOS.contains(pontos)) {
            throw new IllegalArgumentException(
                    "Story points devem ser 1, 2, 3, 4, 5 ou 8"
            );
        }

        this.storyPoints = pontos;
        tocar();
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
