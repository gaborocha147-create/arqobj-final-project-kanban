package br.insper.edu.kanban.model;

import br.insper.edu.kanban.usuario.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_quadro", "id_usuario"}))
public class MembroQuadro {
    public enum Papel {
        ADMIN, MEMBRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_quadro", nullable = false)
    private Quadro quadro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Papel papel;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataEntrada;

    protected MembroQuadro() {}

    public MembroQuadro(Usuario usuario, Quadro quadro, Papel papel) {
        if (usuario == null || quadro == null) {
            throw new IllegalArgumentException("Usuário e quadro obrigatórios");
        }
        this.usuario = usuario;
        this.quadro = quadro;
        alterarPapel(papel);
        this.dataEntrada = LocalDateTime.now();
    }

    public void alterarPapel(Papel papel) {
        if (papel == null) {
            throw new IllegalArgumentException("Papel obrigatório");
        }
        this.papel = papel;
    }

    public UUID getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Quadro getQuadro() {
        return quadro;
    }

    public Papel getPapel() {
        return papel;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }
}
