package br.insper.edu.kanban;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:kanban-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false"
})
class KanbanApplicationTests {
    @LocalServerPort
    private int porta;

    private final HttpClient cliente = HttpClient.newHttpClient();
    private final JsonMapper json = JsonMapper.builder().build();
    private String token;
    private String quadroId;
    private String colunaId;

    @BeforeEach
    void prepararQuadro() throws Exception {
        String email = UUID.randomUUID() + "@teste.com";
        String credenciais = "{\"email\":\"" + email + "\",\"senha\":\"senha-teste\"}";
        JsonNode usuario = enviar("POST", "/usuarios", credenciais, 201);
        assertFalse(usuario.has("senha"));
        token = enviar("POST", "/login", credenciais, 200).get("token").asText();
        quadroId = enviar("POST", "/quadros", "{\"nome\":\"Projeto\"}", 201).get("id").asText();
        JsonNode colunas = enviar("GET", "/quadros/" + quadroId + "/colunas", null, 200);
        assertEquals(3, colunas.size());
        colunaId = colunas.get(0).get("id").asText();
    }

    @Test
    void persisteChecklistESeusItens() throws Exception {
        String cardId = criarTarefa(colunaId);
        String checklistId = enviar("POST", "/cards/" + cardId + "/checklists",
                "{\"titulo\":\"Revisão\"}", 201).get("id").asText();
        String itemId = enviar("POST", "/checklists/" + checklistId + "/itens",
                "{\"descricao\":\"Testar API\",\"concluido\":false}", 201).get("id").asText();
        enviar("PUT", "/itens/" + itemId,
                "{\"descricao\":\"Testar API\",\"concluido\":true}", 200);
        enviar("PUT", "/checklists/" + checklistId, "{\"titulo\":\"Revisado\"}", 200);
        JsonNode checklist = enviar("GET", "/checklists/" + checklistId, null, 200);
        assertEquals("Revisado", checklist.get("titulo").asText());
        assertEquals(100.0, checklist.get("percentualConcluido").asDouble());
        enviar("DELETE", "/itens/" + itemId, null, 204);
        enviar("GET", "/itens/" + itemId, null, 404);
        enviar("DELETE", "/checklists/" + checklistId, null, 204);
        enviar("GET", "/checklists/" + checklistId, null, 404);
    }

    @Test
    void respeitaLimiteWipSemPerderCardDaOrigem() throws Exception {
        String destinoId = enviar("POST", "/quadros/" + quadroId + "/colunas",
                "{\"nome\":\"Revisão\",\"limiteWip\":1}", 201).get("id").asText();
        criarTarefa(destinoId);
        String cardId = criarTarefa(colunaId);
        enviar("PATCH", "/cards/" + cardId + "/mover",
                "{\"colunaId\":\"" + destinoId + "\"}", 409);
        assertEquals(colunaId, enviar("GET", "/cards/" + cardId, null, 200).get("colunaId").asText());
        enviar("DELETE", "/colunas/" + destinoId, null, 409);
    }

    @Test
    void protegeQuadroEUltimoAdministrador() throws Exception {
        String membroId = enviar("GET", "/quadros/" + quadroId + "/membros", null, 200)
                .get(0).get("id").asText();
        enviar("DELETE", "/membros/" + membroId, null, 409);
        enviar("PUT", "/membros/" + membroId, "{\"papel\":\"MEMBRO\"}", 409);
        String credenciais = "{\"email\":\"" + UUID.randomUUID() + "@teste.com\",\"senha\":\"outra-senha\"}";
        enviar("POST", "/usuarios", credenciais, 201);
        token = enviar("POST", "/login", credenciais, 200).get("token").asText();
        enviar("GET", "/quadros/" + quadroId, null, 403);
        token = null;
        enviar("GET", "/quadros", null, 401);
        token = "token-invalido";
        enviar("GET", "/quadros", null, 401);
    }

    @Test
    void validaDadosERegrasDosTiposDeCard() throws Exception {
        enviar("POST", "/colunas/" + colunaId + "/cards",
                "{\"titulo\":\"\",\"tipo\":\"TAREFA\",\"prioridade\":\"MEDIA\"}", 400);
        enviar("POST", "/colunas/" + colunaId + "/cards",
                "{\"titulo\":\"Melhoria\",\"tipo\":\"MELHORIA\",\"prioridade\":\"MEDIA\",\"storyPoints\":7}", 400);
        String bugId = enviar("POST", "/colunas/" + colunaId + "/cards",
                "{\"titulo\":\"Falha\",\"tipo\":\"BUG\",\"prioridade\":\"ALTA\",\"critico\":true}", 201)
                .get("id").asText();
        enviar("PATCH", "/cards/" + bugId + "/prioridade", "{\"prioridade\":\"BAIXA\"}", 409);
        enviar("GET", "/cards/" + UUID.randomUUID(), null, 404);
        enviar("GET", "/cards/id-invalido", null, 400);
    }

    @Test
    void excluiQuadroComCardsEtiquetasEComentarios() throws Exception {
        String cardId = criarTarefa(colunaId);
        String etiquetaId = enviar("POST", "/quadros/" + quadroId + "/etiquetas",
                "{\"nome\":\"Entrega\",\"cor\":\"#123456\"}", 201).get("id").asText();
        enviar("PUT", "/cards/" + cardId + "/etiquetas/" + etiquetaId, null, 200);
        enviar("POST", "/cards/" + cardId + "/comentarios", "{\"texto\":\"Em revisão\"}", 201);
        enviar("DELETE", "/quadros/" + quadroId, null, 204);
        enviar("GET", "/cards/" + cardId, null, 404);
        enviar("GET", "/etiquetas/" + etiquetaId, null, 404);
    }

    @Test
    void removeMembroELimpaResponsabilidadeDosCards() throws Exception {
        String usuarioId = enviar("POST", "/usuarios",
                "{\"email\":\"" + UUID.randomUUID() + "@teste.com\",\"senha\":\"senha-teste\"}", 201)
                .get("id").asText();
        String membroId = enviar("POST", "/quadros/" + quadroId + "/membros",
                "{\"usuarioId\":\"" + usuarioId + "\",\"papel\":\"MEMBRO\"}", 201).get("id").asText();
        String cardId = criarTarefa(colunaId);
        enviar("PATCH", "/cards/" + cardId + "/responsavel", "{\"usuarioId\":\"" + usuarioId + "\"}", 200);
        enviar("DELETE", "/membros/" + membroId, null, 204);
        enviar("GET", "/membros/" + membroId, null, 404);
        assertTrue(enviar("GET", "/cards/" + cardId, null, 200).get("responsavel").isNull());
    }

    private String criarTarefa(String coluna) throws Exception {
        return enviar("POST", "/colunas/" + coluna + "/cards",
                "{\"titulo\":\"Implementar API\",\"tipo\":\"TAREFA\",\"prioridade\":\"MEDIA\"}", 201)
                .get("id").asText();
    }

    private JsonNode enviar(String metodo, String caminho, String corpo, int status) throws Exception {
        HttpRequest.Builder pedido = HttpRequest.newBuilder(URI.create("http://localhost:" + porta + caminho))
                .header("Content-Type", "application/json")
                .method(metodo, corpo == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(corpo));
        if (token != null) {
            pedido.header("Authorization", "Bearer " + token);
        }
        HttpResponse<String> resposta = cliente.send(pedido.build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(status, resposta.statusCode(), metodo + " " + caminho + ": " + resposta.body());
        return resposta.body().isBlank() ? null : json.readTree(resposta.body());
    }
}
