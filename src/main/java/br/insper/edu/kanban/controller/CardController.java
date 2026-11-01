package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.DTO.CardRequest;
import br.insper.edu.kanban.DTO.MoverCardRequest;
import br.insper.edu.kanban.DTO.PrioridadeCardRequest;
import br.insper.edu.kanban.DTO.ResponsavelCardRequest;
import br.insper.edu.kanban.service.CardService;
import br.insper.edu.kanban.view.CardView;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("/colunas/{colunaId}/cards")
    public List<CardView> listar(@PathVariable UUID colunaId, Principal principal) {
        return cardService.listar(colunaId, principal.getName());
    }

    @GetMapping("/cards/{id}")
    public CardView obter(@PathVariable UUID id, Principal principal) {
        return cardService.obter(id, principal.getName());
    }

    @PostMapping("/colunas/{colunaId}/cards")
    @ResponseStatus(HttpStatus.CREATED)
    public CardView criar(
            @PathVariable UUID colunaId,
            @Valid @RequestBody CardRequest dados,
            Principal principal) {
        return cardService.criar(colunaId, dados, principal.getName());
    }

    @PutMapping("/cards/{id}")
    public CardView atualizar(
            @PathVariable UUID id, @Valid @RequestBody CardRequest dados, Principal principal) {
        return cardService.atualizar(id, dados, principal.getName());
    }

    @DeleteMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, Principal principal) {
        cardService.excluir(id, principal.getName());
    }

    @PatchMapping("/cards/{id}/mover")
    public CardView mover(
            @PathVariable UUID id,
            @Valid @RequestBody MoverCardRequest dados,
            Principal principal) {
        return cardService.mover(id, dados.getColunaId(), principal.getName());
    }

    @PatchMapping("/cards/{id}/responsavel")
    public CardView responsavel(
            @PathVariable UUID id,
            @Valid @RequestBody ResponsavelCardRequest dados,
            Principal principal) {
        return cardService.responsavel(id, dados.getUsuarioId(), principal.getName());
    }

    @PatchMapping("/cards/{id}/prioridade")
    public CardView prioridade(
            @PathVariable UUID id,
            @Valid @RequestBody PrioridadeCardRequest dados,
            Principal principal) {
        return cardService.prioridade(id, dados.getPrioridade(), principal.getName());
    }

    @PutMapping("/cards/{id}/etiquetas/{etiquetaId}")
    public CardView adicionarEtiqueta(
            @PathVariable UUID id, @PathVariable UUID etiquetaId, Principal principal) {
        return cardService.etiqueta(id, etiquetaId, true, principal.getName());
    }

    @DeleteMapping("/cards/{id}/etiquetas/{etiquetaId}")
    public CardView removerEtiqueta(
            @PathVariable UUID id, @PathVariable UUID etiquetaId, Principal principal) {
        return cardService.etiqueta(id, etiquetaId, false, principal.getName());
    }
}
