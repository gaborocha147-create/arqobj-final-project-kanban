package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.DTO.EtiquetaRequest;
import br.insper.edu.kanban.service.EtiquetaService;
import br.insper.edu.kanban.view.EtiquetaView;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class EtiquetaController {
    @Autowired
    private EtiquetaService etiquetaService;

    @GetMapping("/quadros/{quadroId}/etiquetas")
    public List<EtiquetaView> listar(@PathVariable UUID quadroId, Principal principal) {
        return etiquetaService.listar(quadroId, principal.getName());
    }

    @GetMapping("/etiquetas/{id}")
    public EtiquetaView obter(@PathVariable UUID id, Principal principal) {
        return etiquetaService.obter(id, principal.getName());
    }

    @PostMapping("/quadros/{quadroId}/etiquetas")
    @ResponseStatus(HttpStatus.CREATED)
    public EtiquetaView criar(
            @PathVariable UUID quadroId,
            @Valid @RequestBody EtiquetaRequest dados,
            Principal principal) {
        return etiquetaService.criar(quadroId, dados, principal.getName());
    }

    @PutMapping("/etiquetas/{id}")
    public EtiquetaView atualizar(
            @PathVariable UUID id, @Valid @RequestBody EtiquetaRequest dados, Principal principal) {
        return etiquetaService.atualizar(id, dados, principal.getName());
    }

    @DeleteMapping("/etiquetas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, Principal principal) {
        etiquetaService.excluir(id, principal.getName());
    }
}
