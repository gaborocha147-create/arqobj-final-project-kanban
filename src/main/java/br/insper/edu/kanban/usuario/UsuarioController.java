package br.insper.edu.kanban.usuario;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;

@RestController
public class UsuarioController {
    private final UsuarioService service;
    private final JwtService jwtService;

    public UsuarioController(UsuarioService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/usuarios")
    @SecurityRequirements
    public ResponseEntity<UsuarioView> cadastrar(@Valid @RequestBody CadastroUsuarioRequest dados) {
        Usuario usuario = service.cadastrar(dados.getEmail(), dados.getSenha());
        return ResponseEntity.created(URI.create("/usuarios/" + usuario.getId()))
                .body(new UsuarioView(usuario));
    }

    @PostMapping("/login")
    @SecurityRequirements
    public LoginResponse login(@Valid @RequestBody LoginRequest dados) {
        return new LoginResponse(jwtService.generateToken(service.autenticar(dados.getEmail(), dados.getSenha())), "Bearer");
    }

    @GetMapping("/usuarios/me")
    public UsuarioView eu(Principal principal) {
        return new UsuarioView(service.buscarPorEmail(principal.getName()));
    }

    @PutMapping("/usuarios/me/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@Valid @RequestBody AlterarSenhaRequest dados, Principal principal) {
        service.alterarSenha(principal.getName(), dados.getSenhaAtual(), dados.getNovaSenha());
    }
}