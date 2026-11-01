package br.insper.edu.kanban.model;

import jakarta.persistence.*;

@Entity(name = "Bug")
@DiscriminatorValue("BUG")
public class Bug extends Card {

    private boolean critico;

    @Column(length = 2000)
    private String passosReproducao;

    protected Bug() {}

    public Bug(String titulo, String descricao, Prioridade prioridade,
               boolean critico, String passosReproducao) {
        super(titulo, descricao, critico ? Prioridade.ALTA : prioridade);

        this.critico = critico;
        this.passosReproducao = passosReproducao;
    }

    @Override
    public void alterarPrioridade(Prioridade prioridade) {
        if (isCritico() && prioridade != Prioridade.ALTA) {
            throw new IllegalStateException("Bug crítico deve ter prioridade ALTA");
        }

        super.alterarPrioridade(prioridade);
    }

    public void editarBug(boolean critico, String passosReproducao) {
        this.critico = critico;
        this.passosReproducao = passosReproducao;
        if (critico) {
            super.alterarPrioridade(Prioridade.ALTA);
        }
        tocar();
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
        return critico;
    }

    public String getPassosReproducao() {
        return passosReproducao;
    }
}
