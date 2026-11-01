package br.insper.edu.kanban.service;

import br.insper.edu.kanban.common.RecursoNaoEncontradoException;
import br.insper.edu.kanban.DTO.ItemChecklistRequest;
import br.insper.edu.kanban.model.Checklist;
import br.insper.edu.kanban.model.ItemChecklist;
import br.insper.edu.kanban.model.ItemChecklistRepository;
import br.insper.edu.kanban.view.ItemChecklistView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ItemChecklistService {
    @Autowired
    private ItemChecklistRepository itemRepository;
    @Autowired
    private ChecklistService checklistService;

    public ItemChecklist buscarPorId(UUID id, String email) {
        ItemChecklist item = itemRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrado"));
        checklistService.buscarPorId(item.getChecklist().getId(), email);
        return item;
    }

    public List<ItemChecklistView> listar(UUID checklistId, String email) {
        Checklist checklist = checklistService.buscarPorId(checklistId, email);
        List<ItemChecklistView> resposta = new ArrayList<>();
        for (ItemChecklist item : checklist.getItens()) {
            resposta.add(new ItemChecklistView(item));
        }
        return resposta;
    }

    public ItemChecklistView obter(UUID id, String email) {
        return new ItemChecklistView(buscarPorId(id, email));
    }

    public ItemChecklistView criar(UUID checklistId, ItemChecklistRequest dados, String email) {
        Checklist checklist = checklistService.buscarPorId(checklistId, email);
        ItemChecklist item = checklist.adicionarItem(dados.getDescricao());
        item.editar(dados.getDescricao(), dados.getConcluido());
        itemRepository.save(item);
        return new ItemChecklistView(item);
    }

    public ItemChecklistView atualizar(UUID id, ItemChecklistRequest dados, String email) {
        ItemChecklist item = buscarPorId(id, email);
        item.editar(dados.getDescricao(), dados.getConcluido());
        itemRepository.save(item);
        return new ItemChecklistView(item);
    }

    public void excluir(UUID id, String email) {
        ItemChecklist item = buscarPorId(id, email);
        item.getChecklist().removerItem(item);
        itemRepository.flush();
    }
}
