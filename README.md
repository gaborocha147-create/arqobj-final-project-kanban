### Repositório do projeto final de Arquitetura de Objetos e Times Ágeis

### Participantes
- Humberto Filho Castello Branco Lages Rebello e Castro

### Docker e H2

Crie um arquivo `.env` local (ignorado pelo Git) com `JWT_SECRET` (segredo
aleatorio com pelo menos 32 caracteres) e `DATABASE_PASSWORD`.
Execute `docker compose up --build -d`. A API fica em
http://localhost:8080/swagger-ui/index.html e o banco H2 no volume `kanban-data`.
`docker compose down` preserva os dados; `docker compose down -v` apaga o volume.

### Render

O `render.yaml` configura um Web Service Docker gratuito. No Render, crie um
Blueprint apontando para a branch que contem estes arquivos. Os segredos sao
gerados pelo Render. O health check usa `/v3/api-docs`; a interface da API fica
em `/swagger-ui/index.html`.

O plano gratuito e apenas para demonstracao: o arquivo H2 em `/app/data/kanban`
e efemero e pode ser perdido em reinicios e deploys. Para preservar dados no
Render, use um plano pago com disco montado em `/app/data` e uma unica instancia.
Nao altere a senha do banco existente sem migrar suas credenciais.

O perfil `prod` usa a porta `PORT` do Render, desativa o console H2 e exige
`JWT_SECRET` e `DATABASE_PASSWORD`. `DATABASE_URL` pode substituir o caminho H2.
O Docker compila e executa os testes antes de gerar a imagem final com Java 25.

### Integracao com o frontend Vercel

CORS permite apenas `https://front-arq-e-obj.vercel.app` por padrao, incluindo no perfil `prod`.
Use `CORS_ALLOWED_ORIGINS` para substituir a lista de origens (separadas por virgula).
No frontend, substitua a URL de exemplo em `vercel.json` pela URL publica do
Render, mantendo `/:path*` e sem adicionar `/api` ao destino. Alternativamente,
defina `VITE_API_BASE` com a URL do Render e refaca o deploy do frontend.
