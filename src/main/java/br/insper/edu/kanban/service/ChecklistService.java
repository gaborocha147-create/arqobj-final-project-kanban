package br.insper.edu.kanban.service;

import br.insper.edu.kanban.common.RecursoNaoEncontradoException;
import br.insper.edu.kanban.DTO.ChecklistRequest;
import br.insper.edu.kanban.model.Card;
import br.insper.edu.kanban.model.Checklist;
import br.insper.edu.kanban.model.ChecklistRepository;
import br.insper.edu.kanban.view.ChecklistView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ChecklistService {
    @Autowired
    private ChecklistRepository checklistRepository;
    @Autowired
    private CardService cardService;

    public Checklist buscarPorId(UUID id, String email) {
        Checklist checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Checklist não encontrado"));
        cardService.buscarPorId(checklist.getCard().getId(), email);
        return checklist;
    }

    public List<ChecklistView> listar(UUID cardId, String email) {
        Card card = cardService.buscarPorId(cardId, email);
        List<ChecklistView> resposta = new ArrayList<>();
        for (Checklist checklist : card.getChecklists()) {
            resposta.add(new ChecklistView(checklist));
        }
        return resposta;
    }

    public ChecklistView obter(UUID id, String email) {
        return new ChecklistView(buscarPorId(id, email));
    }

    public ChecklistView criar(UUID cardId, ChecklistRequest dados, String email) {
        Card card = cardService.buscarPorId(cardId, email);
        Checklist checklist = card.criarChecklist(dados.getTitulo());
        checklistRepository.save(checklist);
        return new ChecklistView(checklist);
    }

    public ChecklistView atualizar(UUID id, ChecklistRequest dados, String email) {
        Checklist checklist = buscarPorId(id, email);
        checklist.renomear(dados.getTitulo());
        checklistRepository.save(checklist);
        return new ChecklistView(checklist);
    }

    public void excluir(UUID id, String email) {
        Checklist checklist = buscarPorId(id, email);
        checklist.getCard().removerChecklist(checklist);
        checklistRepository.flush();
    }
}
