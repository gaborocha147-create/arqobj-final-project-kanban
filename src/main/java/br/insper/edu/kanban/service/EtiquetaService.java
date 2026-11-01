package br.insper.edu.kanban.service;

import br.insper.edu.kanban.common.ConflitoException;
import br.insper.edu.kanban.common.RecursoNaoEncontradoException;
import br.insper.edu.kanban.DTO.EtiquetaRequest;
import br.insper.edu.kanban.model.Card;
import br.insper.edu.kanban.model.CardRepository;
import br.insper.edu.kanban.model.Etiqueta;
import br.insper.edu.kanban.model.EtiquetaRepository;
import br.insper.edu.kanban.model.Quadro;
import br.insper.edu.kanban.view.EtiquetaView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EtiquetaService {
    @Autowired
    private EtiquetaRepository etiquetaRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private QuadroService quadroService;

    public Etiqueta buscarPorId(UUID id, String email) {
        Etiqueta etiqueta = etiquetaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Etiqueta não encontrada"));
        quadroService.buscarPorId(etiqueta.getQuadro().getId(), email);
        return etiqueta;
    }

    public List<EtiquetaView> listar(UUID quadroId, String email) {
        quadroService.buscarPorId(quadroId, email);
        List<EtiquetaView> resposta = new ArrayList<>();
        for (Etiqueta etiqueta : etiquetaRepository.findByQuadroIdOrderById(quadroId)) {
            resposta.add(new EtiquetaView(etiqueta));
        }
        return resposta;
    }

    public EtiquetaView obter(UUID id, String email) {
        return new EtiquetaView(buscarPorId(id, email));
    }

    private void verificarNome(UUID quadroId, UUID id, String nome) {
        for (Etiqueta etiqueta : etiquetaRepository.findByQuadroIdOrderById(quadroId)) {
            if (!etiqueta.getId().equals(id) && etiqueta.getNome().equalsIgnoreCase(nome.trim())) {
                throw new ConflitoException("Já existe uma etiqueta com esse nome");
            }
        }
    }

    public EtiquetaView criar(UUID quadroId, EtiquetaRequest dados, String email) {
        Quadro quadro = quadroService.buscarPorId(quadroId, email);
        verificarNome(quadroId, null, dados.getNome());
        Etiqueta etiqueta = new Etiqueta(dados.getNome(), dados.getCor(), quadro);
        etiquetaRepository.save(etiqueta);
        return new EtiquetaView(etiqueta);
    }

    public EtiquetaView atualizar(UUID id, EtiquetaRequest dados, String email) {
        Etiqueta etiqueta = buscarPorId(id, email);
        verificarNome(etiqueta.getQuadro().getId(), id, dados.getNome());
        etiqueta.editar(dados.getNome(), dados.getCor());
        etiquetaRepository.save(etiqueta);
        return new EtiquetaView(etiqueta);
    }

    public void excluir(UUID id, String email) {
        Etiqueta etiqueta = buscarPorId(id, email);
        for (Card card : cardRepository.findByEtiquetasId(id)) {
            card.removerEtiqueta(etiqueta);
            cardRepository.save(card);
        }
        cardRepository.flush();
        etiquetaRepository.delete(etiqueta);
    }
}
