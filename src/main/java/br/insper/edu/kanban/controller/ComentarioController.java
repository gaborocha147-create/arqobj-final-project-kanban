package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.DTO.ComentarioRequest;
import br.insper.edu.kanban.service.ComentarioService;
import br.insper.edu.kanban.view.ComentarioView;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/cards/{cardId}/comentarios")
    public List<ComentarioView> listar(@PathVariable UUID cardId, Principal principal) {
        return comentarioService.listar(cardId, principal.getName());
    }

    @GetMapping("/comentarios/{id}")
    public ComentarioView obter(@PathVariable UUID id, Principal principal) {
        return comentarioService.obter(id, principal.getName());
    }

    @PostMapping("/cards/{cardId}/comentarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioView criar(
            @PathVariable UUID cardId,
            @Valid @RequestBody ComentarioRequest dados,
            Principal principal) {
        return comentarioService.criar(cardId, dados, principal.getName());
    }

    @PutMapping("/comentarios/{id}")
    public ComentarioView atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ComentarioRequest dados,
            Principal principal) {
        return comentarioService.atualizar(id, dados, principal.getName());
    }

    @DeleteMapping("/comentarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, Principal principal) {
        comentarioService.excluir(id, principal.getName());
    }
}
