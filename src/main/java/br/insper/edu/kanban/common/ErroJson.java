package br.insper.edu.kanban.common;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ErroJson {
    private ErroJson() {}

    public static void escrever(HttpServletResponse response, int status, String mensagem) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                "{\"status\":" + status + ",\"errors\":[],\"message\":\"" + mensagem.replace("\"", "\\\"") + "\"}");
    }
}
