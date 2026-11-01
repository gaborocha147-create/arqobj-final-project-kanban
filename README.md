### Repositório do projeto final de Arquitetura de Objetos e Times Ágeis

### Participantes
- Humberto Filho Castello Branco Lages Rebello e Castro


### Executar

O projeto usa Java 25 ou superior e o Maven Wrapper incluído no repositório.
Configure `JAVA_HOME` com o diretório do JDK antes de executar os comandos.

No PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

A API fica em `http://localhost:8080` e o Swagger em
`http://localhost:8080/swagger-ui/index.html`.

Cadastre um usuário com `POST /usuarios` e faça login com `POST /login`.
Ambos recebem `email` e `senha` no JSON. Use o token retornado no cabeçalho
`Authorization: Bearer <token>` para acessar os quadros e seus recursos.
Os identificadores da API são UUIDs.

### Testes

```powershell
.\mvnw.cmd clean test
```

Os testes de integração iniciam a API em uma porta livre e usam H2 em memória,
sem alterar o banco local em `data/`. Cobrem autenticação, permissões, membros,
checklists, limite WIP, regras dos cards e exclusão de recursos relacionados.
