# Support API

API RESTful de triagem e gerenciamento de chamados de suporte técnico, desenvolvida em Java 17 com Spring Boot 3.

O projeto demonstra a aplicação de três padrões de projeto GoF em um domínio real, onde cada padrão resolve um problema concreto de negócio.

---

## Tecnologias

- Java 17
- Spring Boot 3.5.5
- Spring Data JPA / Hibernate
- Spring Validation
- H2 (banco em memória)
- Maven

---

## Padrões de Projeto

### Strategy — Cálculo de prioridade

**Problema:** como calcular a prioridade de um chamado sem espalhar `if/else` pelo sistema?

A interface `PriorityStrategy` define o contrato. Cada implementação (`HighPriorityStrategy`, `MediumPriorityStrategy`, `LowPriorityStrategy`) encapsula uma regra de triagem. O `PriorityStrategySelector` escolhe qual estratégia aplicar com base no impacto e urgência informados na abertura do chamado.

Isso permite trocar ou adicionar regras de triagem sem alterar o `TicketService`.

```
PriorityStrategy (interface)
├── HighPriorityStrategy
├── MediumPriorityStrategy
└── LowPriorityStrategy

PriorityStrategySelector → seleciona a estratégia correta
```

---

### State — Ciclo de vida do chamado

**Problema:** como garantir que as transições de status respeitem as regras de negócio?

Cada estado (`OpenState`, `InProgressState`, `ResolvedState`) implementa a interface `TicketState` e define quais operações são permitidas a partir dele. Operações inválidas lançam `IllegalStateException` com uma mensagem descritiva.

```
OPEN → start() → IN_PROGRESS → resolve() → RESOLVED
RESOLVED → reopen() → OPEN
OPEN → resolve()  ← bloqueado pelo OpenState
```

O `TicketStateContext` reconstrói o objeto de estado correto a partir do status persistido no banco.

---

### Factory Method — Criação de chamados

**Problema:** como criar tipos diferentes de chamados sem espalhar lógica de instanciação pelo sistema?

A classe abstrata `TicketCreator` define o processo geral de criação. As subclasses `IncidentTicketCreator` e `RequestTicketCreator` decidem qual tipo de `Ticket` produzir, integrando a estratégia de prioridade no processo de criação.

```
TicketCreator (abstract)
├── IncidentTicketCreator → cria Ticket do tipo INCIDENT
└── RequestTicketCreator  → cria Ticket do tipo REQUEST
```

---

## Arquitetura

```
HTTP
 │
 ▼
TicketController       ← recebe a requisição, delega tudo ao Service
 │
 ▼
TicketService          ← orquestra Strategy, Factory e State
 │
 ├── PriorityStrategySelector (Strategy)
 ├── TicketCreator             (Factory Method)
 └── TicketStateContext        (State)
 │
 ▼
TicketRepository       ← Spring Data JPA
 │
 ▼
H2 (em memória)
```

### Estrutura de pacotes

```
src/main/java/com/rafael/supportapi/
│
├── SupportApiApplication.java
│
├── controllers/
│   └── TicketController.java
│
├── services/
│   └── TicketService.java
│
├── repositories/
│   └── TicketRepository.java
│
├── models/
│   ├── Ticket.java
│   ├── TicketStatus.java
│   ├── TicketType.java
│   ├── TicketPriority.java
│   ├── TicketImpact.java
│   └── TicketUrgency.java
│
├── dto/
│   ├── CreateTicketRequest.java
│   └── TicketResponse.java
│
└── patterns/
    ├── strategy/
    │   ├── PriorityStrategy.java
    │   ├── HighPriorityStrategy.java
    │   ├── MediumPriorityStrategy.java
    │   ├── LowPriorityStrategy.java
    │   └── PriorityStrategySelector.java
    │
    ├── state/
    │   ├── TicketState.java
    │   ├── OpenState.java
    │   ├── InProgressState.java
    │   ├── ResolvedState.java
    │   └── TicketStateContext.java
    │
    └── factory/
        ├── TicketCreator.java
        ├── IncidentTicketCreator.java
        └── RequestTicketCreator.java
```

---

## Como executar

**Pré-requisitos:** Java 17+ e Maven instalados.

```bash
# Clonar o repositório
git clone https://github.com/RafaelSM21/java.git
cd java/support-api

# Rodar a aplicação
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

O banco H2 é criado automaticamente em memória. O console do H2 está disponível em:

```
http://localhost:8080/h2-console

JDBC URL : jdbc:h2:mem:supportdb
User     : sa
Password : (vazio)
```

---

## Endpoints

### Chamados

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/tickets` | Abre um novo chamado |
| `GET` | `/api/tickets` | Lista todos os chamados |
| `GET` | `/api/tickets/{id}` | Busca um chamado por ID |
| `PATCH` | `/api/tickets/{id}/start` | Inicia o atendimento |
| `PATCH` | `/api/tickets/{id}/resolve` | Resolve o chamado |
| `PATCH` | `/api/tickets/{id}/reopen` | Reabre o chamado |

### Exemplo — Abrir chamado

**Request:**

```http
POST /api/tickets
Content-Type: application/json

{
  "title": "Sistema financeiro indisponível",
  "description": "O sistema financeiro não está respondendo.",
  "type": "INCIDENT",
  "impact": "HIGH",
  "urgency": "HIGH"
}
```

**Response `201 Created`:**

```json
{
  "id": 1,
  "title": "Sistema financeiro indisponível",
  "description": "O sistema financeiro não está respondendo.",
  "type": "INCIDENT",
  "priority": "HIGH",
  "impact": "HIGH",
  "urgency": "HIGH",
  "status": "OPEN"
}
```

### Valores aceitos

| Campo | Valores |
|-------|---------|
| `type` | `INCIDENT`, `REQUEST` |
| `impact` | `LOW`, `MEDIUM`, `HIGH` |
| `urgency` | `LOW`, `MEDIUM`, `HIGH` |

### Regra de prioridade

| Impacto | Urgência | Prioridade calculada |
|---------|----------|----------------------|
| `HIGH` | qualquer | `HIGH` |
| qualquer | `HIGH` | `HIGH` |
| `MEDIUM` | qualquer | `MEDIUM` |
| qualquer | `MEDIUM` | `MEDIUM` |
| `LOW` | `LOW` | `LOW` |

### Transições de status permitidas

| Estado atual | Operação | Próximo estado |
|---|---|---|
| `OPEN` | `start` | `IN_PROGRESS` |
| `IN_PROGRESS` | `resolve` | `RESOLVED` |
| `RESOLVED` | `reopen` | `OPEN` |
| `OPEN` | `resolve` | ❌ Bloqueado |
| `RESOLVED` | `start` | ❌ Bloqueado |