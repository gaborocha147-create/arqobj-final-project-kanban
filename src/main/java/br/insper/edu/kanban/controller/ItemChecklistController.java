package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.DTO.ItemChecklistRequest;
import br.insper.edu.kanban.service.ItemChecklistService;
import br.insper.edu.kanban.view.ItemChecklistView;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class ItemChecklistController {
    @Autowired
    private ItemChecklistService itemChecklistService;

    @GetMapping("/checklists/{checklistId}/itens")
    public List<ItemChecklistView> listar(@PathVariable UUID checklistId, Principal principal) {
        return itemChecklistService.listar(checklistId, principal.getName());
    }

    @GetMapping("/itens/{id}")
    public ItemChecklistView obter(@PathVariable UUID id, Principal principal) {
        return itemChecklistService.obter(id, principal.getName());
    }

    @PostMapping("/checklists/{checklistId}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemChecklistView criar(
            @PathVariable UUID checklistId,
            @Valid @RequestBody ItemChecklistRequest dados,
            Principal principal) {
        return itemChecklistService.criar(checklistId, dados, principal.getName());
    }

    @PutMapping("/itens/{id}")
    public ItemChecklistView atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ItemChecklistRequest dados,
            Principal principal) {
        return itemChecklistService.atualizar(id, dados, principal.getName());
    }

    @DeleteMapping("/itens/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, Principal principal) {
        itemChecklistService.excluir(id, principal.getName());
    }
}
