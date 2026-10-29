package br.insper.edu.kanban.usuario;

import br.insper.edu.kanban.service.JwtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    public record Credenciais(
            @NotBlank @Email @Size(max = 254) String email,
            @NotBlank @Size(max = 72) String senha) {}
    public record UsuarioView(Long id, String email) {}
    public record TokenView(String token, String tipo) {}

    @PostMapping("/usuarios")
    @SecurityRequirements
    public ResponseEntity<UsuarioView> cadastrar(@Valid @RequestBody Credenciais dados) {
        Usuario usuario = service.cadastrar(dados.email(), dados.senha());
        return ResponseEntity.created(URI.create("/usuarios/" + usuario.getId()))
                .body(new UsuarioView(usuario.getId(), usuario.getEmail()));
    }

    @PostMapping("/login")
    @SecurityRequirements
    public TokenView login(@Valid @RequestBody Credenciais dados) {
        return new TokenView(jwtService.generateToken(service.autenticar(dados.email(), dados.senha())), "Bearer");
    }
}
