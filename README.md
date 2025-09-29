# 🏍️ Sistema de Gestão de Motos - TrackZone

## Integrantes
- André Rogério Vieira Pavanela Altobelli Antunes, RM: 554764
- Enrico Figueiredo Del Guerra, RM: 558604
- Leticia Cristina Dos Santos Passos, RM: 555241

Sistema web completo para gestão de motos desenvolvido com Spring Boot, Thymeleaf e Spring Security.

## 📋 Índice

- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar](#-como-executar)
  - [Via Eclipse/IDE](#via-eclipseide)
  - [Via Terminal/CMD](#via-terminalcmd)
- [Configuração do Banco](#-configuração-do-banco)
- [Funcionalidades](#-funcionalidades)
- [Como Testar](#-como-testar)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Requisitos Técnicos](#-requisitos-técnicos)
- [Solução de Problemas](#-solução-de-problemas)
- [Roteiro para Gravação](#-roteiro-para-gravação)

## 🛠️ Tecnologias Utilizadas

- **Backend**: Spring Boot 3.5.4
- **Frontend**: Thymeleaf + Bootstrap 5
- **Segurança**: Spring Security 6
- **Banco de Dados**: Oracle Database
- **Migração**: Flyway
- **Build**: Maven
- **Java**: 17+

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Oracle Database (ou acesso ao banco da FIAP)
- Eclipse/IntelliJ IDEA (opcional)

## 🚀 Como Executar

### Via Eclipse/IDE

#### 1. Importar o Projeto
1. **Abrir Eclipse**
2. **File → Import**
3. **Maven → Existing Maven Projects**
4. **Browse** → Selecionar pasta: `challenge3-java-finalizado-main/universidade_fiap`
5. **Finish**

#### 2. Configurar Java
1. **Botão direito no projeto → Properties**
2. **Java Build Path → Libraries**
3. **Modulepath** → Verificar se está usando Java 17+
4. **Apply and Close**

#### 3. Configurar Maven
1. **Botão direito no projeto → Maven → Reload Projects**
2. Aguardar download das dependências
3. Verificar se não há erros no console

#### 4. Executar a Aplicação

**Método 1: Java Application**
1. **Navegar até**: `src/main/java/br/com/fiap/universidade_fiap/UniversidadeFiapApplication.java`
2. **Botão direito → Run As → Java Application**
3. Aguardar inicialização (pode demorar 1-2 minutos)
4. Verificar console: "Started UniversidadeFiapApplication"

**Método 2: Spring Boot App**
1. **Botão direito no projeto → Run As → Spring Boot App**
2. Aguardar inicialização
3. Verificar se não há erros

**Método 3: Maven**
1. **Botão direito no projeto → Run As → Maven build**
2. **Goals**: `spring-boot:run`
3. **Run**

### Via Terminal/CMD

1. **Navegar até o diretório do projeto**
   ```bash
   cd challenge3-java-finalizado-main/universidade_fiap
   ```

2. **Compilar o projeto**
   ```bash
   mvn clean compile
   ```

3. **Executar a aplicação**
   ```bash
   mvn spring-boot:run
   ```

4. **Acessar a aplicação**
   ```
   http://localhost:8081
   ```

## 🗄️ Configuração do Banco

### 1. Executar Script de Limpeza (Recomendado)

Execute o script `script_limpar_banco.sql` no Oracle para:
- Limpar dados antigos
- Recriar tabelas com sequences corretas
- Inserir dados iniciais

### 2. Configuração de Conexão

As configurações estão em `src/main/resources/application.properties`:

```properties
# Banco Oracle FIAP
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=rm555241
spring.datasource.password=230205
```

### 3. Dados Iniciais

Após executar o script, você terá os seguintes usuários:

| Perfil | Email | Senha |
|--------|-------|-------|
| ADMIN | admin@teste.com | Admin123! |
| GERENTE | gerente@teste.com | Gerente123! |
| OPERADOR | operador@teste.com | Operador123! |

## ⚙️ Funcionalidades

### 🔐 Sistema de Autenticação
- **Login/Logout** com Spring Security
- **3 Perfis de Usuário**:
  - **ADMIN**: Acesso total ao sistema
  - **GERENTE**: Acesso a dashboard e relatórios
  - **OPERADOR**: Acesso a motos e operações

### 👥 Gestão de Usuários
- ✅ Cadastrar novos usuários
- ✅ Listar usuários existentes
- ✅ Editar informações
- ✅ Validações de CNPJ e email únicos
- ✅ Criptografia de senhas (BCrypt)

### 🏍️ Gestão de Motos
- ✅ Cadastrar motos
- ✅ Listar motos com filtros
- ✅ Editar informações
- ✅ Validações de placa e chassi únicos
- ✅ Relacionamento com usuário responsável

### 📊 Status das Motos
- ✅ Definir status (PRONTA, PENDENTE, REPARO_SIMPLES, etc.)
- ✅ Associar área de estacionamento
- ✅ Histórico de mudanças de status

### 🔄 Operações
- ✅ **Check-in**: Entrada de moto no sistema
- ✅ **Check-out**: Saída de moto do sistema
- ✅ Observações detalhadas
- ✅ Histórico completo de operações

### 📈 Dashboard e Relatórios
- ✅ Estatísticas gerais
- ✅ Relatórios por período
- ✅ Gráficos de operações
- ✅ Métricas de performance

### 🔍 Auditoria
- ✅ Log de todas as operações
- ✅ Rastreamento de mudanças
- ✅ Histórico de usuários

## 🧪 Como Testar

### ✅ 1. Teste de Inicialização

**Objetivo**: Verificar se a aplicação inicia corretamente

**Passos**:
1. Executar `mvn spring-boot:run`
2. Aguardar mensagem: `Started UniversidadeFiapApplication`
3. Acessar `http://localhost:8081`
4. Verificar se redireciona para login

**Resultado Esperado**: ✅ Aplicação inicia sem erros

### ✅ 2. Teste de Autenticação

**Objetivo**: Verificar sistema de login/logout

#### 2.1 Login Válido
**Passos**:
1. Acessar `http://localhost:8081/login`
2. Inserir: `admin@teste.com` / `Admin123!`
3. Clicar em "Entrar"

**Resultado Esperado**: ✅ Redireciona para dashboard

#### 2.2 Login Inválido
**Passos**:
1. Inserir: `admin@teste.com` / `senhaerrada`
2. Clicar em "Entrar"

**Resultado Esperado**: ✅ Mostra mensagem de erro

### ✅ 3. Teste de Controle de Acesso

**Objetivo**: Verificar se os perfis funcionam corretamente

#### 3.1 Acesso de ADMIN
**Passos**:
1. Login como ADMIN
2. Verificar se pode acessar:
   - `/usuario/lista` ✅
   - `/motos` ✅
   - `/operacoes` ✅
   - `/dashboard` ✅

#### 3.2 Acesso de GERENTE
**Passos**:
1. Login como GERENTE
2. Verificar se pode acessar:
   - `/usuario/lista` ❌ (deve negar)
   - `/motos` ✅
   - `/operacoes` ✅
   - `/dashboard` ✅

#### 3.3 Acesso de OPERADOR
**Passos**:
1. Login como OPERADOR
2. Verificar se pode acessar:
   - `/usuario/lista` ❌ (deve negar)
   - `/motos` ✅
   - `/operacoes` ✅
   - `/dashboard` ❌ (deve negar)

### ✅ 4. Teste de Cadastro de Usuário

**Objetivo**: Verificar CRUD de usuários

#### 4.1 Cadastro Válido
**Passos**:
1. Login como ADMIN
2. Acessar `/usuario/novo`
3. Preencher:
   - Nome: "Teste Usuário"
   - Email: "teste@exemplo.com"
   - Senha: "Teste123!"
   - CNPJ: "12.345.678/0001-90"
   - Perfil: "OPERADOR"
4. Clicar "Cadastrar"

**Resultado Esperado**: ✅ Usuário cadastrado com sucesso

#### 4.2 Validação de Email Duplicado
**Passos**:
1. Tentar cadastrar com email existente
2. Clicar "Cadastrar"

**Resultado Esperado**: ✅ Mostra erro "E-mail já cadastrado"

### ✅ 5. Teste de Cadastro de Motos

**Objetivo**: Verificar CRUD de motos

#### 5.1 Cadastro Válido
**Passos**:
1. Acessar `/motos`
2. Clicar "Nova Moto"
3. Preencher:
   - Placa: "ABC1234"
   - Chassi: "CHASSI123456789"
   - Motor: "150CC"
   - Usuário: Selecionar
4. Clicar "Salvar"

**Resultado Esperado**: ✅ Moto cadastrada com sucesso

### ✅ 6. Teste de Operações

**Objetivo**: Verificar sistema de check-in/check-out

#### 6.1 Check-in de Moto
**Passos**:
1. Acessar `/operacoes`
2. Clicar "Nova Operação"
3. Selecionar:
   - Moto: Uma moto disponível
   - Tipo: "CHECK_IN"
   - Observações: "Entrada da moto"
4. Clicar "Salvar"

**Resultado Esperado**: ✅ Operação registrada

### ✅ 7. Teste de Dashboard

**Objetivo**: Verificar estatísticas e relatórios

#### 7.1 Acesso ao Dashboard
**Passos**:
1. Login como ADMIN ou GERENTE
2. Acessar `/dashboard`
3. Verificar se carrega estatísticas

**Resultado Esperado**: ✅ Dashboard carrega com dados

## 📁 Estrutura do Projeto

```
universidade_fiap/
├── src/main/java/br/com/fiap/universidade_fiap/
│   ├── control/          # Controllers REST
│   ├── model/           # Entidades JPA
│   ├── repository/      # Repositórios Spring Data
│   ├── service/         # Serviços de negócio
│   ├── security/        # Configurações de segurança
│   └── exception/       # Tratamento de exceções
├── src/main/resources/
│   ├── templates/       # Templates Thymeleaf
│   ├── static/         # CSS, JS, imagens
│   ├── db/migration/   # Scripts Flyway
│   └── application.properties
└── pom.xml
```

## 🎯 Requisitos Técnicos Atendidos

### ✅ Thymeleaf (30 pontos)
- Páginas HTML com Thymeleaf para CRUD completo
- Fragmentos reutilizáveis (navbar, cabeçalho, rodapé)
- Validações no frontend
- Interface responsiva com Bootstrap

### ✅ Flyway (20 pontos)
- Configuração do Flyway para versionamento
- 4 versões de migração (V1 a V4)
- Migrações automáticas na inicialização
- Controle de versão do banco de dados

### ✅ Spring Security (30 pontos)
- Sistema de autenticação via formulário
- 3 tipos de usuário: ADMIN, GERENTE, OPERADOR
- Proteção de rotas baseada em perfil
- Controle de sessão e logout

### ✅ Funcionalidades Completas (20 pontos)
- CRUD completo de usuários e motos
- Sistema de operações (check-in/check-out)
- Dashboard com estatísticas
- Relatórios e auditoria
- Validações em formulários e dados

## 🔧 Solução de Problemas

### Erro: "Port 8081 is already in use"
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8081 | xargs kill -9
```

### Erro: "Could not execute statement [ORA-01400]"
- Execute o script `script_limpar_banco.sql`
- Reinicie a aplicação

### Erro: "Flyway validation failed"
- Desabilite temporariamente o Flyway:
  ```properties
  spring.flyway.enabled=false
  ```

### Erro: "ClassNotFoundException: Oracle12cDialect"
- Use o dialect correto:
  ```properties
  spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
  ```

### Aplicação não inicia
1. Verifique se o Java 17+ está instalado
2. Verifique se o Maven está configurado
3. Verifique a conexão com o banco Oracle
4. Execute: `mvn clean compile`

## 🎬 Roteiro para Gravação

### **INTRODUÇÃO (2 minutos)**

#### 1. **Apresentação do Projeto**
```
"Olá! Hoje vou demonstrar o Sistema de Gestão de Motos TrackZone, 
desenvolvido com Spring Boot, Thymeleaf e Spring Security.

Este sistema permite:
- Gerenciar usuários com diferentes perfis
- Cadastrar e controlar motos
- Registrar operações de check-in/check-out
- Visualizar relatórios e dashboard"
```

#### 2. **Tecnologias Utilizadas**
```
"O projeto utiliza:
- Spring Boot 3.5.4
- Thymeleaf para templates
- Spring Security para autenticação
- Flyway para migração de banco
- Oracle Database
- Bootstrap para interface"
```

### **DEMONSTRAÇÃO TÉCNICA (12-15 minutos)**

#### 3. **Estrutura do Projeto (2 minutos)**
```
"Vou mostrar a estrutura do projeto:
- src/main/java: Código Java
- src/main/resources: Configurações e templates
- src/main/resources/db/migration: Scripts Flyway
- Templates Thymeleaf organizados por funcionalidade"
```

#### 4. **Spring Security - Autenticação (3 minutos)**

**4.1. Tela de Login**
- Acessar: `http://localhost:8081/login`
- Mostrar formulário de login
- Explicar: "Sistema de autenticação com Spring Security"

**4.2. Testar diferentes usuários**
```
Credenciais de teste:
- Admin: admin@teste.com / Admin123!
- Gerente: gerente@teste.com / Gerente123!
- Operador: operador@teste.com / Operador123!
```

**4.3. Controle de acesso**
- Mostrar que cada perfil tem acesso diferente
- Tentar acessar rotas restritas

#### 5. **Thymeleaf - Interface (3 minutos)**

**5.1. Templates e Fragmentos**
- Mostrar estrutura de templates
- Explicar fragmentos (navbar, cabeçalho)
- Mostrar reutilização de código

**5.2. Formulários com Validação**
- Cadastro de usuário com validações
- Mostrar mensagens de erro/sucesso
- Validação em tempo real no frontend

#### 6. **Flyway - Migração de Banco (2 minutos)**

**6.1. Scripts de Migração**
- Mostrar arquivos V1, V2, V3, V4
- Explicar versionamento do banco
- Mostrar logs do Flyway

**6.2. Estrutura do Banco**
- Conectar no Oracle e mostrar tabelas
- Mostrar sequences criadas
- Explicar relacionamentos

#### 7. **Funcionalidades Completas (5 minutos)**

**7.1. CRUD de Usuários**
- Listar usuários
- Cadastrar novo usuário
- Editar usuário
- Mostrar validações

**7.2. CRUD de Motos**
- Listar motos
- Cadastrar nova moto
- Mostrar relacionamento com usuário
- Validações de placa e chassi

**7.3. Operações**
- Check-in de moto
- Check-out de moto
- Histórico de operações

**7.4. Dashboard e Relatórios**
- Estatísticas gerais
- Relatórios por período
- Gráficos e métricas

### **DEMONSTRAÇÃO DE REQUISITOS TÉCNICOS (3 minutos)**

#### 8. **Thymeleaf (30 pontos)**
```
"Requisitos atendidos:
✅ Páginas HTML com Thymeleaf para CRUD completo
✅ Fragmentos reutilizáveis (navbar, cabeçalho, rodapé)
✅ Validações no frontend
✅ Interface responsiva com Bootstrap"
```

#### 9. **Flyway (20 pontos)**
```
"Requisitos atendidos:
✅ Configuração do Flyway para versionamento
✅ 4 versões de migração (V1 a V4)
✅ Migrações automáticas na inicialização
✅ Controle de versão do banco de dados"
```

#### 10. **Spring Security (30 pontos)**
```
"Requisitos atendidos:
✅ Sistema de autenticação via formulário
✅ 3 tipos de usuário: ADMIN, GERENTE, OPERADOR
✅ Proteção de rotas baseada em perfil
✅ Controle de sessão e logout"
```

#### 11. **Funcionalidades Completas (20 pontos)**
```
"Requisitos atendidos:
✅ CRUD completo de usuários e motos
✅ Sistema de operações (check-in/check-out)
✅ Dashboard com estatísticas
✅ Relatórios e auditoria
✅ Validações em formulários e dados"
```

### **CONCLUSÃO (2 minutos)**

#### 12. **Resumo Técnico**
```
"O sistema demonstra:
- Arquitetura MVC bem estruturada
- Segurança robusta com Spring Security
- Interface moderna com Thymeleaf
- Controle de versão com Flyway
- Integração completa com Oracle Database"
```

#### 13. **Pontos Fortes**
```
"Principais destaques:
- Código limpo seguindo princípios SOLID
- Interface intuitiva e responsiva
- Sistema de auditoria completo
- Validações robustas
- Controle de acesso granular"
```

#### 14. **Encerramento**
```
"Este sistema atende todos os requisitos do challenge:
- Thymeleaf para frontend
- Flyway para migrações
- Spring Security para autenticação
- Funcionalidades completas e validações

Obrigado pela atenção!"
```

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique os logs da aplicação
2. Consulte a seção "Solução de Problemas"
3. Verifique se todos os pré-requisitos estão atendidos

## 🎯 Próximos Passos

1. **Testar todas as funcionalidades**
2. **Verificar validações**
3. **Testar diferentes perfis de usuário**
4. **Gravar demonstração** seguindo o roteiro

---

**Desenvolvido com ❤️ para o Challenge Java Advanced - FIAP**
