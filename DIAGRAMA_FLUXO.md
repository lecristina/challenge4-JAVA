# 🔄 Diagrama de Fluxo - TrackZone

## Fluxo Principal: Cadastro de Moto

```
┌─────────────┐
│   Usuário   │
│  (Browser)  │
└──────┬──────┘
       │
       │ 1. Acessa /motos/novo
       ▼
┌─────────────────────┐
│  MotoController     │
│  novoCadastroMoto() │
└──────┬──────────────┘
       │
       │ 2. Verifica autenticação
       ▼
┌─────────────────────┐
│AuthenticationService│
│  isAuthenticated()  │
└──────┬──────────────┘
       │
       │ 3. Retorna formulário
       ▼
┌─────────────────────┐
│  Template Thymeleaf │
│  cadastroMotos.html │
└──────┬──────────────┘
       │
       │ 4. Preenche dados e submete
       ▼
┌─────────────────────┐
│  MotoController     │
│  salvarMoto()       │
└──────┬──────────────┘
       │
       │ 5. Valida dados
       ▼
┌─────────────────────┐
│  MotoService        │
│  - placaExiste()    │
│  - chassiExiste()   │
└──────┬──────────────┘
       │
       │ 6. Validações OK
       ▼
┌─────────────────────┐
│  MotoRepository     │
│  save()             │
└──────┬──────────────┘
       │
       │ 7. Persiste no banco
       ▼
┌─────────────────────┐
│  Oracle/H2 Database │
└──────┬──────────────┘
       │
       │ 8. Retorna sucesso
       ▼
┌─────────────────────┐
│  Redirect /motos    │
│  ?sucesso=true      │
└─────────────────────┘
```

## Fluxo: Chat com IA

```
┌─────────────┐
│   Usuário   │
│  (Browser)  │
└──────┬──────┘
       │
       │ 1. Acessa /ai/chat
       ▼
┌─────────────────────┐
│  AIController       │
│  chat()             │
└──────┬──────────────┘
       │
       │ 2. Renderiza template
       ▼
┌─────────────────────┐
│  Template Thymeleaf │
│  ai/chat.html       │
└──────┬──────────────┘
       │
       │ 3. Usuário faz pergunta
       │    POST /ai/perguntar
       ▼
┌─────────────────────┐
│  AIController       │
│  perguntar()        │
└──────┬──────────────┘
       │
       │ 4. Verifica se IA disponível
       ▼
┌─────────────────────┐
│  AIService          │
│  obterSugestao()    │
└──────┬──────────────┘
       │
       │ 5a. Spring AI disponível?
       │     ┌─────────┐
       │     │   SIM   │
       │     └────┬────┘
       │          │
       │          ▼
       │     ┌─────────────────────┐
       │     │  Spring AI          │
       │     │  (Ollama/OpenAI)    │
       │     └─────────────────────┘
       │
       │ 5b. Spring AI não disponível?
       │     ┌─────────┐
       │     │   NÃO   │
       │     └────┬────┘
       │          │
       │          ▼
       │     ┌─────────────────────┐
       │     │  AIServiceFallback   │
       │     │  obterSugestao()    │
       │     └─────────────────────┘
       │
       │ 6. Retorna resposta
       ▼
┌─────────────────────┐
│  JSON Response      │
│  {resposta: "..."}  │
└──────┬──────────────┘
       │
       │ 7. Exibe resposta no chat
       ▼
┌─────────────────────┐
│  Interface do Chat  │
│  (ai/chat.html)     │
└─────────────────────┘
```

## Fluxo: Autenticação e Autorização

```
┌─────────────┐
│   Usuário   │
│  (Browser)  │
└──────┬──────┘
       │
       │ 1. Acessa qualquer rota protegida
       ▼
┌─────────────────────┐
│  Spring Security    │
│  SecurityFilterChain│
└──────┬──────────────┘
       │
       │ 2. Verifica se autenticado
       │    ┌─────────┐
       │    │   NÃO   │
       │    └────┬────┘
       │         │
       │         ▼
       │    ┌─────────────────────┐
       │    │  Redirect /login    │
       │    └─────────────────────┘
       │
       │ 3. Usuário faz login
       ▼
┌─────────────────────┐
│  LoginController    │
│  login()            │
└──────┬──────────────┘
       │
       │ 4. Valida credenciais
       ▼
┌─────────────────────┐
│  UsuarioDetailsService│
│  loadUserByUsername()│
└──────┬──────────────┘
       │
       │ 5. Busca usuário
       ▼
┌─────────────────────┐
│  UsuarioRepository  │
│  findByEmail()      │
└──────┬──────────────┘
       │
       │ 6. Verifica senha (BCrypt)
       │    ┌─────────┐
       │    │   OK    │
       │    └────┬────┘
       │         │
       │         ▼
       │    ┌─────────────────────┐
       │    │  Cria sessão        │
       │    │  SecurityContext     │
       │    └─────────────────────┘
       │
       │ 7. Verifica perfil (ADMIN/GERENTE/OPERADOR)
       ▼
┌─────────────────────┐
│  SegurancaConfig    │
│  authorizeRequests()│
└──────┬──────────────┘
       │
       │ 8. Permite acesso conforme perfil
       ▼
┌─────────────────────┐
│  Controller         │
│  (MotoController,  │
│   etc.)             │
└─────────────────────┘
```

## Fluxo: Atualização de Status de Moto

```
┌─────────────┐
│   Usuário   │
│  (Admin)    │
└──────┬──────┘
       │
       │ 1. Acessa /motos/operacoes
       ▼
┌─────────────────────┐
│StatusMotoController│
│  listarStatusMotos()│
└──────┬──────────────┘
       │
       │ 2. Lista todas as motos e status
       ▼
┌─────────────────────┐
│StatusMotosRepository│
│  findAll()          │
└──────┬──────────────┘
       │
       │ 3. Usuário seleciona moto e atualiza status
       │    POST /motos/status/salvar
       ▼
┌─────────────────────┐
│StatusMotoController│
│  salvarStatusMoto() │
└──────┬──────────────┘
       │
       │ 4. Verifica autenticação
       ▼
┌─────────────────────┐
│AuthenticationService│
│  getUsuarioLogado() │
└──────┬──────────────┘
       │
       │ 5. Busca moto
       ▼
┌─────────────────────┐
│  MotoRepository     │
│  findById()         │
└──────┬──────────────┘
       │
       │ 6. Cria novo StatusMoto
       ▼
┌─────────────────────┐
│StatusMotosRepository│
│  save()             │
└──────┬──────────────┘
       │
       │ 7. Persiste no banco
       ▼
┌─────────────────────┐
│  Oracle/H2 Database │
└──────┬──────────────┘
       │
       │ 8. Retorna sucesso
       ▼
┌─────────────────────┐
│  Redirect /motos    │
│  ?sucesso=true      │
└─────────────────────┘
```

## Fluxo: Relatório de Status

```
┌─────────────┐
│   Usuário   │
│  (Gerente)  │
└──────┬──────┘
       │
       │ 1. Acessa /relatorios/status
       ▼
┌─────────────────────┐
│RelatorioController  │
│  relatorioStatus()  │
└──────┬──────────────┘
       │
       │ 2. Verifica autenticação
       ▼
┌─────────────────────┐
│AuthenticationService│
│  isAuthenticated()  │
└──────┬──────────────┘
       │
       │ 3. Busca status
       ▼
┌─────────────────────┐
│StatusMotosRepository│
│  findAll()          │
└──────┬──────────────┘
       │
       │ 4. Processa dados
       ▼
┌─────────────────────┐
│  Template Thymeleaf │
│  relatorios/status  │
│  .html              │
└──────┬──────────────┘
       │
       │ 5. Renderiza relatório
       ▼
┌─────────────────────┐
│  Usuário visualiza  │
│  relatório          │
└─────────────────────┘
```

## Fluxo: Dashboard

```
┌─────────────┐
│   Usuário   │
│  (Admin)    │
└──────┬──────┘
       │
       │ 1. Acessa /dashboard
       ▼
┌─────────────────────┐
│DashboardController  │
│  dashboard()        │
└──────┬──────────────┘
       │
       │ 2. Busca dados
       ▼
┌─────────────────────┐
│  MotoRepository     │
│  findAll()          │
├─────────────────────┤
│StatusMotosRepository│
│  findAll()          │
└──────┬──────────────┘
       │
       │ 3. Calcula estatísticas
       │    - Motos prontas
       │    - Motos alugadas
       │    - Motos em manutenção
       │    - etc.
       ▼
┌─────────────────────┐
│  Template Thymeleaf │
│  home/dashboard.html │
└──────┬──────────────┘
       │
       │ 4. Renderiza dashboard
       ▼
┌─────────────────────┐
│  Usuário visualiza  │
│  estatísticas       │
└─────────────────────┘
```

## Fluxo de Erro (Exception Handling)

```
┌─────────────────────┐
│  Qualquer Controller│
│  (Método qualquer)  │
└──────┬──────────────┘
       │
       │ 1. Erro ocorre
       ▼
┌─────────────────────┐
│  Exception lançada  │
└──────┬──────────────┘
       │
       │ 2. Capturada pelo handler
       ▼
┌─────────────────────┐
│GlobalExceptionHandler│
│  handleException()  │
└──────┬──────────────┘
       │
       │ 3. Log do erro
       ▼
┌─────────────────────┐
│  Logger (SLF4J)     │
│  logger.error()     │
└──────┬──────────────┘
       │
       │ 4. Retorna página de erro
       ▼
┌─────────────────────┐
│  Template Thymeleaf │
│  error/500.html     │
└─────────────────────┘
```

---

**Diagramas criados para demonstrar os principais fluxos do sistema TrackZone**





