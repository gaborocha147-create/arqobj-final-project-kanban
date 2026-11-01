package br.insper.edu.kanban.service;

import br.insper.edu.kanban.common.ConflitoException;
import br.insper.edu.kanban.common.RecursoNaoEncontradoException;
import br.insper.edu.kanban.DTO.ColunaRequest;
import br.insper.edu.kanban.model.Coluna;
import br.insper.edu.kanban.model.ColunaRepository;
import br.insper.edu.kanban.model.Quadro;
import br.insper.edu.kanban.view.ColunaView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ColunaService {
    @Autowired
    private ColunaRepository colunaRepository;
    @Autowired
    private QuadroService quadroService;

    public Coluna buscarPorId(UUID id, String email) {
        Coluna coluna = colunaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Coluna não encontrada"));
        quadroService.buscarPorId(coluna.getQuadro().getId(), email);
        return coluna;
    }

    public List<ColunaView> listar(UUID quadroId, String email) {
        Quadro quadro = quadroService.buscarPorId(quadroId, email);
        List<ColunaView> resposta = new ArrayList<>();
        for (Coluna coluna : quadro.getColunas()) {
            resposta.add(new ColunaView(coluna));
        }
        return resposta;
    }

    public ColunaView obter(UUID id, String email) {
        return new ColunaView(buscarPorId(id, email));
    }

    public ColunaView criar(UUID quadroId, ColunaRequest dados, String email) {
        quadroService.verificarAdmin(quadroId, email);
        Quadro quadro = quadroService.buscarPorId(quadroId, email);
        Coluna coluna = quadro.adicionarColuna(dados.getNome(), dados.getLimiteWip());
        colunaRepository.save(coluna);
        return new ColunaView(coluna);
    }

    public ColunaView atualizar(UUID id, ColunaRequest dados, String email) {
        Coluna coluna = buscarPorId(id, email);
        quadroService.verificarAdmin(coluna.getQuadro().getId(), email);
        for (Coluna outra : coluna.getQuadro().getColunas()) {
            if (!outra.getId().equals(id)
                    && outra.getNome().equalsIgnoreCase(dados.getNome().trim())) {
                throw new ConflitoException("Já existe uma coluna com esse nome");
            }
        }
        coluna.renomear(dados.getNome());
        coluna.definirLimiteWip(dados.getLimiteWip());
        colunaRepository.save(coluna);
        return new ColunaView(coluna);
    }

    public ColunaView posicionar(UUID id, int posicao, String email) {
        Coluna coluna = buscarPorId(id, email);
        quadroService.verificarAdmin(coluna.getQuadro().getId(), email);
        List<Coluna> colunas = new ArrayList<>(coluna.getQuadro().getColunas());
        if (posicao < 0 || posicao >= colunas.size()) {
            throw new IllegalArgumentException("Posição fora do quadro");
        }
        colunas.remove(coluna);
        colunas.add(posicao, coluna);
        for (int i = 0; i < colunas.size(); i++) {
            colunas.get(i).setPosicao(i);
            colunaRepository.save(colunas.get(i));
        }
        return new ColunaView(coluna);
    }

    public void excluir(UUID id, String email) {
        Coluna coluna = buscarPorId(id, email);
        quadroService.verificarAdmin(coluna.getQuadro().getId(), email);
        coluna.getQuadro().removerColuna(coluna);
        colunaRepository.flush();
    }
}
