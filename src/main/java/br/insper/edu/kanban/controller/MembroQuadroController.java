package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.DTO.MembroQuadroRequest;
import br.insper.edu.kanban.DTO.PapelMembroRequest;
import br.insper.edu.kanban.service.MembroQuadroService;
import br.insper.edu.kanban.view.MembroQuadroView;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class MembroQuadroController {
    @Autowired
    private MembroQuadroService membroQuadroService;

    @GetMapping("/quadros/{quadroId}/membros")
    public List<MembroQuadroView> listar(@PathVariable UUID quadroId, Principal principal) {
        return membroQuadroService.listar(quadroId, principal.getName());
    }

    @GetMapping("/membros/{id}")
    public MembroQuadroView obter(@PathVariable UUID id, Principal principal) {
        return membroQuadroService.obter(id, principal.getName());
    }

    @PostMapping("/quadros/{quadroId}/membros")
    @ResponseStatus(HttpStatus.CREATED)
    public MembroQuadroView criar(
            @PathVariable UUID quadroId,
            @Valid @RequestBody MembroQuadroRequest dados,
            Principal principal) {
        return membroQuadroService.criar(quadroId, dados, principal.getName());
    }

    @PutMapping("/membros/{id}")
    public MembroQuadroView atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody PapelMembroRequest dados,
            Principal principal) {
        return membroQuadroService.atualizar(id, dados, principal.getName());
    }

    @DeleteMapping("/membros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, Principal principal) {
        membroQuadroService.excluir(id, principal.getName());
    }
}
