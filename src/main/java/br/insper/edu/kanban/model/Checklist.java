package br.insper.edu.kanban.model;

import jakarta.persistence.*;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_card", nullable = false)
    private Card card;

    @OneToMany(
            mappedBy = "checklist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("id ASC")
    private List<ItemChecklist> itens = new ArrayList<>();

    protected Checklist() {
    }

    Checklist(String titulo, Card card) {
        renomear(titulo);

        if (card == null) {
            throw new IllegalArgumentException("Card obrigatório");
        }

        this.card = card;
    }

    public void renomear(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título obrigatório");
        }

        if (titulo.trim().length() > 255) {
            throw new IllegalArgumentException("Título muito longo");
        }

        this.titulo = titulo.trim();
    }

    public void removerItem(ItemChecklist item) {
        itens.remove(item);
    }

    public ItemChecklist adicionarItem(String descricao) {
        ItemChecklist item = new ItemChecklist(descricao, this);
        itens.add(item);
        return item;
    }

    public double percentualConcluido() {
        if (itens.isEmpty()) {
            return 0;
        }

        long concluidos = itens.stream()
                .filter(ItemChecklist::isConcluido)
                .count();

        return concluidos * 100.0 / itens.size();
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Card getCard() {
        return card;
    }

    public List<ItemChecklist> getItens() {
        return List.copyOf(itens);
    }
}
