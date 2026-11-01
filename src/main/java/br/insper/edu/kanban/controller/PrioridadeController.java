package br.insper.edu.kanban.controller;

import br.insper.edu.kanban.model.Prioridade;
import br.insper.edu.kanban.view.PrioridadeView;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PrioridadeController {
    @GetMapping("/prioridades")
    public List<PrioridadeView> listar() {
        List<PrioridadeView> resposta = new ArrayList<>();
        for (Prioridade prioridade : Prioridade.values()) {
            resposta.add(new PrioridadeView(prioridade));
        }
        return resposta;
    }
}
