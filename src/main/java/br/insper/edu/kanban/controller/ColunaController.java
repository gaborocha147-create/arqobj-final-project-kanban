package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.DTO.ColunaRequest;
import br.insper.edu.kanban.DTO.PosicaoColunaRequest;
import br.insper.edu.kanban.service.ColunaService;
import br.insper.edu.kanban.view.ColunaView;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class ColunaController {
    @Autowired
    private ColunaService colunaService;

    @GetMapping("/quadros/{quadroId}/colunas")
    public List<ColunaView> listar(@PathVariable UUID quadroId, Principal principal) {
        return colunaService.listar(quadroId, principal.getName());
    }

    @GetMapping("/colunas/{id}")
    public ColunaView obter(@PathVariable UUID id, Principal principal) {
        return colunaService.obter(id, principal.getName());
    }

    @PostMapping("/quadros/{quadroId}/colunas")
    @ResponseStatus(HttpStatus.CREATED)
    public ColunaView criar(
    @PathVariable UUID quadroId,
    @Valid @RequestBody ColunaRequest dados,
    Principal principal) {
        return colunaService.criar(quadroId, dados, principal.getName());
    }

    @PutMapping("/colunas/{id}")
    public ColunaView atualizar(
    @PathVariable UUID id, @Valid @RequestBody ColunaRequest dados, Principal principal) {
        return colunaService.atualizar(id, dados, principal.getName());
    }

    @DeleteMapping("/colunas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, Principal principal) {
        colunaService.excluir(id, principal.getName());
    }

    @PatchMapping("/colunas/{id}/posicao")
    public ColunaView posicionar(
    @PathVariable UUID id,
    @Valid @RequestBody PosicaoColunaRequest dados,
    Principal principal) {
        return colunaService.posicionar(id, dados.getPosicao(), principal.getName());
    }
}
