package br.insper.edu.kanban.service;

import br.insper.edu.kanban.common.ConflitoException;
import br.insper.edu.kanban.common.RecursoNaoEncontradoException;
import br.insper.edu.kanban.DTO.MembroQuadroRequest;
import br.insper.edu.kanban.DTO.PapelMembroRequest;
import br.insper.edu.kanban.model.Card;
import br.insper.edu.kanban.model.CardRepository;
import br.insper.edu.kanban.model.Coluna;
import br.insper.edu.kanban.model.MembroQuadro;
import br.insper.edu.kanban.model.MembroQuadroRepository;
import br.insper.edu.kanban.model.Quadro;
import br.insper.edu.kanban.view.MembroQuadroView;
import br.insper.edu.kanban.usuario.Usuario;
import br.insper.edu.kanban.usuario.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MembroQuadroService {
    @Autowired
    private MembroQuadroRepository membroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private QuadroService quadroService;

    public MembroQuadro buscarPorId(UUID id, String email) {
        MembroQuadro membro = membroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Membro não encontrado"));
        quadroService.buscarPorId(membro.getQuadro().getId(), email);
        return membro;
    }

    public List<MembroQuadroView> listar(UUID quadroId, String email) {
        quadroService.buscarPorId(quadroId, email);
        List<MembroQuadroView> resposta = new ArrayList<>();
        for (MembroQuadro membro : membroRepository.findByQuadroIdOrderById(quadroId)) {
            resposta.add(new MembroQuadroView(membro));
        }
        return resposta;
    }

    public MembroQuadroView obter(UUID id, String email) {
        return new MembroQuadroView(buscarPorId(id, email));
    }

    public MembroQuadroView criar(UUID quadroId, MembroQuadroRequest dados, String email) {
        quadroService.verificarAdmin(quadroId, email);
        if (membroRepository.existsByQuadroIdAndUsuarioId(quadroId, dados.getUsuarioId())) {
            throw new ConflitoException("Usuário já participa do quadro");
        }
        Usuario usuario = usuarioRepository.findById(dados.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        Quadro quadro = quadroService.buscarPorId(quadroId, email);
        MembroQuadro membro = new MembroQuadro(usuario, quadro, dados.getPapel());
        membroRepository.save(membro);
        return new MembroQuadroView(membro);
    }

    private void verificarUltimoAdmin(MembroQuadro membro) {
        if (membro.getPapel() == MembroQuadro.Papel.ADMIN
                && membroRepository.countByQuadroIdAndPapel(
                membro.getQuadro().getId(), MembroQuadro.Papel.ADMIN)
                == 1) {
            throw new ConflitoException("O quadro precisa de pelo menos um administrador");
        }
    }

    public MembroQuadroView atualizar(UUID id, PapelMembroRequest dados, String email) {
        MembroQuadro membro = buscarPorId(id, email);
        quadroService.verificarAdmin(membro.getQuadro().getId(), email);
        if (dados.getPapel() != MembroQuadro.Papel.ADMIN) {
            verificarUltimoAdmin(membro);
        }
        membro.alterarPapel(dados.getPapel());
        membroRepository.save(membro);
        return new MembroQuadroView(membro);
    }

    public void excluir(UUID id, String email) {
        MembroQuadro membro = buscarPorId(id, email);
        quadroService.verificarAdmin(membro.getQuadro().getId(), email);
        verificarUltimoAdmin(membro);
        for (Coluna coluna : membro.getQuadro().getColunas()) {
            for (Card card : coluna.getCards()) {
                if (card.getResponsavel() != null
                        && card.getResponsavel().getId().equals(membro.getUsuario().getId())) {
                    card.atribuirResponsavel(null);
                    cardRepository.save(card);
                }
            }
        }
        membroRepository.delete(membro);
    }
}
