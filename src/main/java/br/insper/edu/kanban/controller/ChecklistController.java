package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.DTO.ChecklistRequest;
import br.insper.edu.kanban.service.ChecklistService;
import br.insper.edu.kanban.view.ChecklistView;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class ChecklistController {
    @Autowired
    private ChecklistService checklistService;

    @GetMapping("/cards/{cardId}/checklists")
    public List<ChecklistView> listar(@PathVariable UUID cardId, Principal principal) {
        return checklistService.listar(cardId, principal.getName());
    }

    @GetMapping("/checklists/{id}")
    public ChecklistView obter(@PathVariable UUID id, Principal principal) {
        return checklistService.obter(id, principal.getName());
    }

    @PostMapping("/cards/{cardId}/checklists")
    @ResponseStatus(HttpStatus.CREATED)
    public ChecklistView criar(
            @PathVariable UUID cardId,
            @Valid @RequestBody ChecklistRequest dados,
            Principal principal) {
        return checklistService.criar(cardId, dados, principal.getName());
    }

    @PutMapping("/checklists/{id}")
    public ChecklistView atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ChecklistRequest dados,
            Principal principal) {
        return checklistService.atualizar(id, dados, principal.getName());
    }

    @DeleteMapping("/checklists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, Principal principal) {
        checklistService.excluir(id, principal.getName());
    }
}
