# 📊 Diagrama de Classes - TrackZone

## Diagrama de Classes Principal

```
┌─────────────────────────────────────────────────────────────┐
│                         CONTROLLERS                          │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────────┐  ┌──────────────────┐              │
│  │ MotoController   │  │ StatusMotoController│              │
│  ├──────────────────┤  ├──────────────────┤              │
│  │ - listarMotos()  │  │ - novoStatusMoto()│              │
│  │ - novoCadastro() │  │ - salvarStatus()  │              │
│  │ - salvarMoto()   │  │ - editarStatus()  │              │
│  │ - editarMoto()   │  │ - atualizarStatus()│             │
│  │ - excluirMoto()  │  │ - excluirStatus() │              │
│  └────────┬─────────┘  │ - listarStatus()  │              │
│           │            └────────┬──────────┘              │
│           │                     │                          │
│  ┌────────▼─────────┐  ┌────────▼──────────┐              │
│  │ UsuarioController│  │ RelatorioController│             │
│  │ DashboardController│ │ AIController      │              │
│  └──────────────────┘  └──────────────────┘              │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                          SERVICES                           │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────────────┐  ┌──────────────────────┐        │
│  │ AuthenticationService│  │ MotoService          │        │
│  ├──────────────────────┤  ├──────────────────────┤        │
│  │ + getUsuarioLogado() │  │ + placaExiste()      │        │
│  │ + adicionarUsuario()  │  │ + chassiExiste()     │        │
│  │ + isAuthenticated()   │  │ + podeAlterarPlaca() │        │
│  │ + getEmailUsuario()  │  │ + salvarMoto()       │        │
│  └──────────────────────┘  │ + atualizarMoto()    │        │
│                             └──────────────────────┘        │
│                                                              │
│  ┌──────────────────────┐  ┌──────────────────────┐        │
│  │ AIService            │  │ AIServiceFallback    │        │
│  ├──────────────────────┤  ├──────────────────────┤        │
│  │ + obterSugestao()    │  │ + obterSugestao()    │        │
│  │ + analisarOperacao() │  │ + analisarOperacao() │        │
│  └──────────────────────┘  └──────────────────────┘        │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                        REPOSITORIES                         │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │MotoRepository│  │StatusMotos   │  │Usuario      │      │
│  │              │  │Repository     │  │Repository   │      │
│  ├──────────────┤  ├──────────────┤  ├──────────────┤      │
│  │ + findAll()  │  │ + findAll()  │  │ + findAll()  │      │
│  │ + save()     │  │ + save()     │  │ + save()     │      │
│  │ + findById() │  │ + findById() │  │ + findByEmail()│   │
│  │ + findByPlaca()│ │ + findByMotoId()│             │      │
│  │ + findByChassi()│             │                  │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                          MODELS                              │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────┐      ┌──────────────┐      ┌──────────────┐│
│  │    Moto      │      │  StatusMoto  │      │   Usuario   ││
│  ├──────────────┤      ├──────────────┤      ├──────────────┤│
│  │ - id         │      │ - id         │      │ - id         ││
│  │ - placa      │      │ - moto (FK)  │      │ - nomeFilial ││
│  │ - chassi     │      │ - status     │      │ - email      ││
│  │ - motor      │      │ - area       │      │ - senhaHash  ││
│  │ - usuario(FK)│      │ - usuario(FK)│      │ - cnpj       ││
│  │ - dataCriacao│      │ - dataCriacao│      │ - perfil     ││
│  └──────────────┘      └──────────────┘      └──────────────┘│
│                                                              │
│  ┌──────────────┐      ┌──────────────┐                     │
│  │  Operacao    │      │  Dashboard   │                     │
│  ├──────────────┤      ├──────────────┤                     │
│  │ - id         │      │ - id         │                     │
│  │ - moto (FK)  │      │ - metricas   │                     │
│  │ - tipo       │      │ - dados      │                     │
│  │ - usuario(FK)│      └──────────────┘                     │
│  │ - observacoes│                                            │
│  └──────────────┘                                            │
└─────────────────────────────────────────────────────────────┘
```

## Relacionamentos entre Classes

### Relacionamentos Principais

1. **Controller → Service → Repository → Model**
   - MotoController → MotoService → MotoRepository → Moto
   - StatusMotoController → StatusMotosRepository → StatusMoto
   - UsuarioController → UsuarioRepository → Usuario

2. **Dependências**
   - Todos os Controllers dependem de `AuthenticationService`
   - MotoController depende de `MotoService`
   - AIService é independente (usa reflexão)

3. **Herança/Implementação**
   - Todos os Repositories estendem `JpaRepository`
   - Todos os Services são `@Service`
   - Todos os Controllers são `@Controller`

## Padrões Aplicados

### 1. Repository Pattern
- Abstração de acesso a dados
- Métodos customizados quando necessário
- Uso de Spring Data JPA

### 2. Service Layer Pattern
- Lógica de negócio separada dos controllers
- Reutilização de código
- Facilita testes

### 3. MVC Pattern
- Model: Entidades JPA
- View: Templates Thymeleaf
- Controller: Controllers Spring

### 4. Dependency Injection
- Uso de `@Autowired`
- Constructor Injection (onde aplicável)
- Inversão de Controle

## Princípios SOLID Aplicados

### Single Responsibility Principle (SRP)
- ✅ MotoController: Apenas gerencia motos
- ✅ StatusMotoController: Apenas gerencia status
- ✅ AuthenticationService: Apenas gerencia autenticação
- ✅ MotoService: Apenas lógica de negócio de motos

### Open/Closed Principle (OCP)
- ✅ Services podem ser estendidos sem modificar controllers
- ✅ Fallback para IA quando não disponível

### Liskov Substitution Principle (LSP)
- ✅ Repositories seguem interface JpaRepository

### Interface Segregation Principle (ISP)
- ✅ Services focados em responsabilidades específicas

### Dependency Inversion Principle (DIP)
- ✅ Controllers dependem de abstrações (Services)
- ✅ Services dependem de abstrações (Repositories)

---

**Diagrama criado para demonstrar a arquitetura do sistema TrackZone**





