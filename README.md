# API de Gestão e Aluguel de Veículos

## Descrição do Projeto
API RESTful desenvolvida para o gerenciamento de frotas e automação de sistemas de aluguel de veículos. O sistema permite o controle completo dos registros de veículos e o gerenciamento das locações, garantindo a integridade dos status de disponibilidade e o registro histórico das operações.

**Swagger (API hospedada):** [https://ps-include.onrender.com/docs](https://ps-include.onrender.com/docs)

No plano gratuito do Render a primeira abertura pode demorar cerca de 1 minuto enquanto o serviço acorda.

## ⚙️ Requisitos Funcionais Implementados
- [x] **Cadastrar veículo**: Registro de novos veículos contendo marca, modelo, ano, placa, valor da diária e status de disponibilidade.
- [x] **Editar veículo**: Atualização de dados cadastrais de um veículo específico.
- [x] **Remover veículo**: Exclusão (física ou lógica) de um veículo do sistema.
- [x] **Listar veículos**: Consulta paginada/completa da frota atualizada.
- [x] **Buscar veículo**: Recuperação de detalhes operacionais de um veículo utilizando o ID ou a Placa.
- [x] **Alugar veículo**: Registro de transação de locação vinculada a um cliente, definindo data de início e término, com alteração automatizada do status do veículo para "alugado".

## Tecnologias
- Java 17
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL
- Maven (wrapper incluso no repositório)
- Springdoc OpenAPI (Swagger UI)
- Docker (opcional)

## Pré-requisitos
- [JDK 17](https://adoptium.net/) ou superior
- PostgreSQL em execução (local ou remoto)
- Maven **ou** o wrapper do projeto (`mvnw` / `mvnw.cmd`)

## Configuração do banco de dados
A aplicação usa PostgreSQL e cria/atualiza as tabelas automaticamente (`spring.jpa.hibernate.ddl-auto=update`).

Crie um banco vazio, por exemplo `projeto_back`:

```sql
CREATE DATABASE projeto_back;
```

Ou suba o PostgreSQL com Docker:

```bash
docker run --name projeto-postgres -e POSTGRES_DB=projeto_back -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=senha -p 5432:5432 -d postgres:16
```

## Variáveis de ambiente
A API não sobe sem a URL do banco. Informe as credenciais por variáveis de ambiente:

| Variável | Obrigatória | Descrição |
|---|---|---|
| `SPRING_DATASOURCE_URL` ou `DATABASE_URL` | Sim | URL de conexão com o PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` ou `DATABASE_USERNAME` | Sim* | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` ou `DATABASE_PASSWORD` | Sim* | Senha do banco |
| `PORT` | Não | Porta HTTP. Padrão: `8080` |

\* Usuário e senha podem vir embutidos em URLs no formato `postgres://usuario:senha@host:5432/banco`.

Formatos aceitos na URL:

```text
jdbc:postgresql://localhost:5432/projeto_back
postgres://usuario:senha@localhost:5432/projeto_back?sslmode=disable
postgresql://usuario:senha@localhost:5432/projeto_back?sslmode=disable
```

Para execução local, prefira `jdbc:postgresql://...` ou inclua `sslmode=disable`. URLs `postgres://` / `postgresql://` sem query recebem `sslmode=require` automaticamente (adequado para ambientes gerenciados).

## Como executar

### 1. Clone o repositório e entre na pasta do projeto

```bash
git clone <url-do-repositorio>
cd projetoBack
```

### 2. Defina as variáveis de ambiente

**Windows (PowerShell):**

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/projeto_back"
$env:SPRING_DATASOURCE_USERNAME="postgres"
$env:SPRING_DATASOURCE_PASSWORD="senha"
```

**Linux / macOS:**

```bash
export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/projeto_back"
export SPRING_DATASOURCE_USERNAME="postgres"
export SPRING_DATASOURCE_PASSWORD="senha"
```

### 3. Inicie a aplicação

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

### 4. (Opcional) Gerar o JAR e executar

```bash
./mvnw clean package -DskipTests
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

No Windows, use `.\mvnw.cmd` no lugar de `./mvnw`. As mesmas variáveis de ambiente do passo 2 precisam estar definidas.

## Executar com Docker
Com o PostgreSQL acessível (por exemplo, no host):

```bash
docker build -t projeto-back .
```

**Windows (PowerShell):**

```powershell
docker run --rm -p 8080:8080 `
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://host.docker.internal:5432/projeto_back" `
  -e SPRING_DATASOURCE_USERNAME="postgres" `
  -e SPRING_DATASOURCE_PASSWORD="senha" `
  projeto-back
```

**Linux / macOS:**

```bash
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://host.docker.internal:5432/projeto_back" \
  -e SPRING_DATASOURCE_USERNAME="postgres" \
  -e SPRING_DATASOURCE_PASSWORD="senha" \
  projeto-back
```

## Documentação da API
- Swagger da API hospedada: [https://ps-include.onrender.com/docs](https://ps-include.onrender.com/docs)
- Swagger local: [http://localhost:8080/docs](http://localhost:8080/docs)
- OpenAPI JSON local: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)
- A raiz (`/`) redireciona para o Swagger

## Endpoints principais

| Método | Caminho | Descrição |
|---|---|---|
| `POST` | `/veiculo` | Cadastrar veículo |
| `PUT` | `/veiculo/{id}` | Editar veículo |
| `DELETE` | `/veiculo/{id}` | Remover veículo |
| `GET` | `/veiculo` | Listar veículos |
| `GET` | `/veiculo/{id}` | Buscar veículo por ID |
| `GET` | `/veiculo/placa?placa=ABC1D23` | Buscar veículo por placa |
| `POST` | `/aluguel` | Alugar veículo |
| `POST` | `/aluguel/{id}/encerrar` | Encerrar aluguel |
| `GET` | `/aluguel/status?status=ATIVO` | Listar aluguéis por status |

A placa deve estar em letras maiúsculas e sem traço (ex.: `ABC1234` ou `ABC1D23`).
