package br.insper.edu.kanban.service;

import br.insper.edu.kanban.common.AcessoNegadoException;
import br.insper.edu.kanban.common.RecursoNaoEncontradoException;
import br.insper.edu.kanban.DTO.ComentarioRequest;
import br.insper.edu.kanban.model.Card;
import br.insper.edu.kanban.model.Comentario;
import br.insper.edu.kanban.model.ComentarioRepository;
import br.insper.edu.kanban.view.ComentarioView;
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
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private QuadroService quadroService;
    @Autowired
    private UsuarioService usuarioService;

    public Comentario buscarPorId(UUID id, String email) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Comentário não encontrado"));
        cardService.buscarPorId(comentario.getCard().getId(), email);
        return comentario;
    }

    public List<ComentarioView> listar(UUID cardId, String email) {
        Card card = cardService.buscarPorId(cardId, email);
        List<ComentarioView> resposta = new ArrayList<>();
        for (Comentario comentario : card.getComentarios()) {
            resposta.add(new ComentarioView(comentario));
        }
        return resposta;
    }

    public ComentarioView obter(UUID id, String email) {
        return new ComentarioView(buscarPorId(id, email));
    }

    public ComentarioView criar(UUID cardId, ComentarioRequest dados, String email) {
        Card card = cardService.buscarPorId(cardId, email);
        Usuario autor = usuarioService.buscarPorEmail(email);
        Comentario comentario = card.adicionarComentario(dados.getTexto(), autor);
        comentarioRepository.save(comentario);
        return new ComentarioView(comentario);
    }

    public ComentarioView atualizar(UUID id, ComentarioRequest dados, String email) {
        Comentario comentario = buscarPorId(id, email);
        if (!comentario.getAutor().getEmail().equals(email)) {
            throw new AcessoNegadoException("Somente o autor pode editar o comentário");
        }
        comentario.editar(dados.getTexto());
        comentarioRepository.save(comentario);
        return new ComentarioView(comentario);
    }

    public void excluir(UUID id, String email) {
        Comentario comentario = buscarPorId(id, email);
        if (!comentario.getAutor().getEmail().equals(email)) {
            quadroService.verificarAdmin(
                    comentario.getCard().getColuna().getQuadro().getId(), email);
        }
        comentario.getCard().removerComentario(comentario);
        comentarioRepository.flush();
    }
}
