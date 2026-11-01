package br.insper.edu.kanban.usuario;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
