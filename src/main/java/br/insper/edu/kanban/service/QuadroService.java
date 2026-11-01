package br.insper.edu.kanban.service;

import br.insper.edu.kanban.common.AcessoNegadoException;
import br.insper.edu.kanban.common.RecursoNaoEncontradoException;
import br.insper.edu.kanban.DTO.QuadroRequest;
import br.insper.edu.kanban.model.MembroQuadro;
import br.insper.edu.kanban.model.MembroQuadroRepository;
import br.insper.edu.kanban.model.Quadro;
import br.insper.edu.kanban.model.QuadroRepository;
import br.insper.edu.kanban.view.QuadroView;
import br.insper.edu.kanban.usuario.Usuario;
import br.insper.edu.kanban.usuario.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class QuadroService {
    @Autowired
    private QuadroRepository quadroRepository;
    @Autowired
    private MembroQuadroRepository membroRepository;
    @Autowired
    private UsuarioService usuarioService;

    public Quadro buscarPorId(UUID id, String email) {
        Quadro quadro = quadroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Quadro não encontrado"));
        if (membroRepository.findByQuadroIdAndUsuarioEmail(id, email).isEmpty()) {
            throw new AcessoNegadoException("Você não participa deste quadro");
        }
        return quadro;
    }

    public void verificarAdmin(UUID quadroId, String email) {
        buscarPorId(quadroId, email);
        MembroQuadro membro = membroRepository.findByQuadroIdAndUsuarioEmail(quadroId, email).get();
        if (membro.getPapel() != MembroQuadro.Papel.ADMIN) {
            throw new AcessoNegadoException("Apenas administradores podem fazer isso");
        }
    }

    public List<QuadroView> listar(String email) {
        List<QuadroView> resposta = new ArrayList<>();
        for (Quadro quadro : quadroRepository.findByMembrosUsuarioEmailOrderById(email)) {
            resposta.add(new QuadroView(quadro));
        }
        return resposta;
    }

    public QuadroView obter(UUID id, String email) {
        return new QuadroView(buscarPorId(id, email));
    }

    public QuadroView criar(QuadroRequest dados, String email) {
        Quadro quadro = new Quadro(dados.getNome(), dados.getDescricao());
        quadro.adicionarColuna("A fazer", null);
        quadro.adicionarColuna("Em andamento", null);
        quadro.adicionarColuna("Concluído", null);
        quadroRepository.save(quadro);
        Usuario usuario = usuarioService.buscarPorEmail(email);
        membroRepository.save(new MembroQuadro(usuario, quadro, MembroQuadro.Papel.ADMIN));
        return new QuadroView(quadro);
    }

    public QuadroView atualizar(UUID id, QuadroRequest dados, String email) {
        verificarAdmin(id, email);
        Quadro quadro = buscarPorId(id, email);
        quadro.renomear(dados.getNome());
        quadro.setDescricao(dados.getDescricao());
        quadroRepository.save(quadro);
        return new QuadroView(quadro);
    }

    public void excluir(UUID id, String email) {
        verificarAdmin(id, email);
        quadroRepository.delete(buscarPorId(id, email));
    }
}
