package br.insper.edu.kanban.service;

import br.insper.edu.kanban.common.RecursoNaoEncontradoException;
import br.insper.edu.kanban.DTO.CardRequest;
import br.insper.edu.kanban.model.Bug;
import br.insper.edu.kanban.model.Card;
import br.insper.edu.kanban.model.CardRepository;
import br.insper.edu.kanban.model.Coluna;
import br.insper.edu.kanban.model.Etiqueta;
import br.insper.edu.kanban.model.Melhoria;
import br.insper.edu.kanban.model.MembroQuadroRepository;
import br.insper.edu.kanban.model.Prioridade;
import br.insper.edu.kanban.model.Tarefa;
import br.insper.edu.kanban.model.TipoCard;
import br.insper.edu.kanban.view.CardView;
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
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ColunaService colunaService;
    @Autowired
    private EtiquetaService etiquetaService;
    @Autowired
    private MembroQuadroRepository membroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Card buscarPorId(UUID id, String email) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Card não encontrado"));
        colunaService.buscarPorId(card.getColuna().getId(), email);
        return card;
    }

    public List<CardView> listar(UUID colunaId, String email) {
        Coluna coluna = colunaService.buscarPorId(colunaId, email);
        List<CardView> resposta = new ArrayList<>();
        for (Card card : coluna.getCards()) {
            resposta.add(new CardView(card));
        }
        return resposta;
    }

    public CardView obter(UUID id, String email) {
        return new CardView(buscarPorId(id, email));
    }

    private void validarTipo(CardRequest dados) {
        if (dados.getTipo() != TipoCard.TAREFA && dados.getEstimativaHoras() != null) {
            throw new IllegalArgumentException("Estimativa em horas é um campo de tarefa");
        }
        if (dados.getTipo() != TipoCard.BUG
                && (dados.getCritico() != null || dados.getPassosReproducao() != null)) {
            throw new IllegalArgumentException("Campos de bug enviados para outro tipo de card");
        }
        if (dados.getTipo() != TipoCard.MELHORIA && dados.getStoryPoints() != null) {
            throw new IllegalArgumentException("Story points são um campo de melhoria");
        }
        if (Boolean.TRUE.equals(dados.getCritico()) && dados.getPrioridade() != Prioridade.ALTA) {
            throw new IllegalArgumentException("Bug crítico deve ter prioridade ALTA");
        }
    }

    public CardView criar(UUID colunaId, CardRequest dados, String email) {
        Coluna coluna = colunaService.buscarPorId(colunaId, email);
        validarTipo(dados);
        Card card;
        if (dados.getTipo() == TipoCard.TAREFA) {
            card = new Tarefa(dados.getTitulo(), dados.getDescricao(),
                    dados.getPrioridade(), dados.getEstimativaHoras());
        } else if (dados.getTipo() == TipoCard.BUG) {
            card = new Bug(dados.getTitulo(), dados.getDescricao(), dados.getPrioridade(),
                    Boolean.TRUE.equals(dados.getCritico()), dados.getPassosReproducao());
        } else {
            card = new Melhoria(dados.getTitulo(), dados.getDescricao(),
                    dados.getPrioridade(), dados.getStoryPoints());
        }
        coluna.adicionarCard(card);
        cardRepository.save(card);
        return new CardView(card);
    }

    public CardView atualizar(UUID id, CardRequest dados, String email) {
        Card card = buscarPorId(id, email);
        validarTipo(dados);
        if (!card.getTipo().equals(dados.getTipo().name())) {
            throw new IllegalArgumentException("O tipo do card não pode ser alterado");
        }
        card.editar(dados.getTitulo(), dados.getDescricao());
        if (card instanceof Tarefa) {
            Tarefa tarefa = (Tarefa) card;
            tarefa.definirEstimativa(dados.getEstimativaHoras());
        } else if (card instanceof Bug) {
            Bug bug = (Bug) card;
            bug.editarBug(Boolean.TRUE.equals(dados.getCritico()), dados.getPassosReproducao());
        } else if (card instanceof Melhoria) {
            Melhoria melhoria = (Melhoria) card;
            melhoria.definirStoryPoints(dados.getStoryPoints());
        }
        card.alterarPrioridade(dados.getPrioridade());
        cardRepository.save(card);
        return new CardView(card);
    }

    public CardView mover(UUID id, UUID destinoId, String email) {
        Card card = buscarPorId(id, email);
        Coluna destino = colunaService.buscarPorId(destinoId, email);
        card.moverPara(destino);
        cardRepository.save(card);
        return new CardView(card);
    }

    public CardView responsavel(UUID id, UUID usuarioId, String email) {
        Card card = buscarPorId(id, email);
        Usuario usuario = null;
        if (usuarioId != null) {
            if (!membroRepository.existsByQuadroIdAndUsuarioId(
                    card.getColuna().getQuadro().getId(), usuarioId)) {
                throw new IllegalArgumentException("Responsável deve participar do quadro");
            }
            usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        }
        card.atribuirResponsavel(usuario);
        cardRepository.save(card);
        return new CardView(card);
    }

    public CardView prioridade(UUID id, Prioridade prioridade, String email) {
        Card card = buscarPorId(id, email);
        card.alterarPrioridade(prioridade);
        cardRepository.save(card);
        return new CardView(card);
    }

    public CardView etiqueta(UUID id, UUID etiquetaId, boolean adicionar, String email) {
        Card card = buscarPorId(id, email);
        Etiqueta etiqueta = etiquetaService.buscarPorId(etiquetaId, email);
        if (!card.getColuna().pertenceAo(etiqueta.getQuadro())) {
            throw new IllegalArgumentException("Etiqueta não pertence ao quadro do card");
        }
        if (adicionar) {
            card.adicionarEtiqueta(etiqueta);
        } else {
            card.removerEtiqueta(etiqueta);
        }
        cardRepository.save(card);
        return new CardView(card);
    }

    public void excluir(UUID id, String email) {
        Card card = buscarPorId(id, email);
        card.getColuna().removerCard(card);
        cardRepository.delete(card);
    }
}
