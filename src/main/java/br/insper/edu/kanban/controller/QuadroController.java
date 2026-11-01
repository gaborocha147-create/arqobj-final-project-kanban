package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.DTO.QuadroRequest;
import br.insper.edu.kanban.service.QuadroService;
import br.insper.edu.kanban.view.QuadroView;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class QuadroController {
    @Autowired
    private QuadroService quadroService;

    @GetMapping("/quadros")
    public List<QuadroView> listar(Principal principal) {
        return quadroService.listar(principal.getName());
    }

    @GetMapping("/quadros/{id}")
    public QuadroView obter(@PathVariable UUID id, Principal principal) {
        return quadroService.obter(id, principal.getName());
    }

    @PostMapping("/quadros")
    @ResponseStatus(HttpStatus.CREATED)
    public QuadroView criar(@Valid @RequestBody QuadroRequest dados, Principal principal) {
        return quadroService.criar(dados, principal.getName());
    }

    @PutMapping("/quadros/{id}")
    public QuadroView atualizar(
            @PathVariable UUID id, @Valid @RequestBody QuadroRequest dados, Principal principal) {
        return quadroService.atualizar(id, dados, principal.getName());
    }

    @DeleteMapping("/quadros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, Principal principal) {
        quadroService.excluir(id, principal.getName());
    }
}
