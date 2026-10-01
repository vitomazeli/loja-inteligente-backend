
# 🛒 Loja Inteligente - Backend

```markdown

Backend do projeto **Loja Inteligente**, desenvolvido em **Kotlin com Spring Boot**.

Nesta etapa do projeto foram implementados os CRUDs completos de:

- Categorias
- Produtos
- Clientes
- Vendas

A aplicação disponibiliza uma **API REST**, utiliza **Spring Data JPA / Hibernate** para persistência e **SQLite** como banco de dados.

---

## 🚀 Tecnologias utilizadas

- Kotlin
- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Hibernate
- SQLite
- Gradle
- Jakarta Validation
- Spring Security
- REST API

---

# 📌 Funcionalidades implementadas

Atualmente o backend possui **4 CRUDs completos**, atendendo às operações:

- Create
- Read
- Update
- Delete

Os CRUDs implementados são:

### 📁 Categorias

Permite cadastrar e gerenciar as categorias dos produtos da loja.

Exemplos:

- Eletrônicos
- Informática
- Alimentos
- Limpeza

---

### 📦 Produtos

Permite cadastrar e gerenciar produtos.

Cada produto possui uma categoria associada.

Exemplo:

```text
Categoria: Eletrônicos
│
├── Mouse Gamer
├── Teclado Mecânico
└── Notebook
```

---

### 👤 Clientes

Permite cadastrar e gerenciar clientes da loja.

Cada cliente possui:

- ID
- Nome
- E-mail

O e-mail é utilizado como informação única para evitar cadastros duplicados.

---

### 🧾 Vendas

Permite registrar e gerenciar vendas.

Uma venda possui:

- Cliente
- Data da venda
- Produtos
- Quantidades
- Preço unitário
- Subtotal de cada item
- Valor total

Uma venda pode possuir vários produtos através da entidade `ItemVenda`.

Exemplo:

```text
Venda #1
│
├── Cliente: Maria Silva
│
├── 2x Mouse Gamer
│
├── 1x Teclado Mecânico
│
└── Total: R$ 579,70
```

O valor da venda é calculado automaticamente pelo backend a partir do preço dos produtos.

---

# 🏗️ Arquitetura do backend

O projeto segue uma arquitetura organizada em camadas:

```text
Requisição HTTP
      ↓
Controller
      ↓
Service
      ↓
Repository
      ↓
JPA / Hibernate
      ↓
SQLite
```

## Controller

Responsável por disponibilizar os endpoints REST e receber as requisições HTTP.

## Service

Responsável pelas regras de negócio da aplicação.

## Repository

Responsável pela comunicação com o banco de dados utilizando Spring Data JPA.

## Entity

Representa as entidades e tabelas do banco de dados.

## DTO

Responsável pelos dados de entrada e saída da API.

---

# 🗂️ Estrutura do projeto

```text
src/main/kotlin/br/com/lojainteligente
│
├── categoria
│   ├── Categoria.kt
│   ├── CategoriaRepository.kt
│   ├── CategoriaService.kt
│   ├── CategoriaController.kt
│   └── dto
│       ├── CategoriaRequest.kt
│       └── CategoriaResponse.kt
│
├── produto
│   ├── Produto.kt
│   ├── ProdutoRepository.kt
│   ├── ProdutoService.kt
│   ├── ProdutoController.kt
│   └── dto
│       ├── ProdutoRequest.kt
│       └── ProdutoResponse.kt
│
├── cliente
│   ├── Cliente.kt
│   ├── ClienteRepository.kt
│   ├── ClienteService.kt
│   ├── ClienteController.kt
│   └── dto
│       ├── ClienteRequest.kt
│       └── ClienteResponse.kt
│
├── venda
│   ├── Venda.kt
│   ├── ItemVenda.kt
│   ├── VendaRepository.kt
│   ├── VendaService.kt
│   ├── VendaController.kt
│   └── dto
│       ├── VendaRequest.kt
│       ├── VendaResponse.kt
│       ├── ItemVendaRequest.kt
│       └── ItemVendaResponse.kt
│
├── config
│   └── SecurityConfig.kt
│
└── LojaInteligenteBackendApplication.kt
```

---

# 🗄️ Banco de dados

O projeto utiliza **SQLite**.

O banco é armazenado no arquivo:

```text
loja_inteligente.db
```

A configuração está localizada em:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:sqlite:loja_inteligente.db
spring.datasource.driver-class-name=org.sqlite.JDBC

spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8082
```

Durante o desenvolvimento, o Hibernate cria e atualiza automaticamente as tabelas através da configuração:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

# 🔗 Relacionamento entre as entidades

O modelo atual possui os seguintes relacionamentos:

```text
Categoria
    │
    │ 1:N
    ▼
 Produto
    │
    │ 1:N
    ▼
ItemVenda
    ▲
    │
    │ 1:N
  Venda
    ▲
    │
    │ N:1
 Cliente
```

De forma simplificada:

```text
Categoria → Produto → ItemVenda ← Venda ← Cliente
```

Uma categoria pode possuir vários produtos.

Um produto pertence a uma categoria.

Uma venda pertence a um cliente.

Uma venda pode possuir vários itens.

Cada item de venda referencia um produto.

---

# ▶️ Como executar o projeto

## Pré-requisitos

É necessário possuir:

- Java JDK 21
- Git
- IntelliJ IDEA ou outra IDE compatível com Kotlin

Não é necessário instalar o Gradle separadamente, pois o projeto utiliza o **Gradle Wrapper**.

---

## 1. Clonar o repositório

```bash
git clone https://github.com/vitomazeli/loja-inteligente-backend.git
```

Entre na pasta:

```bash
cd loja-inteligente-backend
```

---

## 2. Verificar a versão do Java

Execute:

```bash
java -version
```

O projeto utiliza:

```text
Java 21
```

No Windows também é possível verificar a JVM utilizada pelo Gradle:

```powershell
.\gradlew.bat --version
```

---

## 3. Fazer o build

### Windows

```powershell
.\gradlew.bat clean build
```

### Linux / macOS

```bash
./gradlew clean build
```

Se tudo estiver configurado corretamente:

```text
BUILD SUCCESSFUL
```

---

## 4. Executar o backend

### Windows

```powershell
.\gradlew.bat bootRun
```

### Linux / macOS

```bash
./gradlew bootRun
```

O servidor será iniciado na porta:

```text
8082
```

URL base:

```text
http://localhost:8082
```

Quando aparecer:

```text
Tomcat started on port 8082
```

a API estará pronta para uso.

Para interromper a aplicação:

```text
Ctrl + C
```

---

# 📡 Endpoints

## 📁 Categorias

### Criar

```http
POST /api/categorias
```

Exemplo:

```json
{
  "nome": "Eletrônicos",
  "descricao": "Produtos eletrônicos da loja",
  "ativo": true
}
```

---

### Listar

```http
GET /api/categorias
```

---

### Buscar por ID

```http
GET /api/categorias/{id}
```

---

### Atualizar

```http
PUT /api/categorias/{id}
```

---

### Excluir

```http
DELETE /api/categorias/{id}
```

---

# 📦 Produtos

## Criar

```http
POST /api/produtos
```

Exemplo:

```json
{
  "nome": "Mouse Gamer",
  "descricao": "Mouse gamer RGB",
  "codigoBarras": "7891234567890",
  "preco": 129.90,
  "categoriaId": 1,
  "ativo": true
}
```

---

## Listar

```http
GET /api/produtos
```

---

## Buscar por ID

```http
GET /api/produtos/{id}
```

---

## Atualizar

```http
PUT /api/produtos/{id}
```

---

## Excluir

```http
DELETE /api/produtos/{id}
```

---

# 👤 Clientes

## Criar

```http
POST /api/clientes
```

Exemplo:

```json
{
  "nome": "Maria Silva",
  "email": "maria@email.com"
}
```

---

## Listar

```http
GET /api/clientes
```

---

## Buscar por ID

```http
GET /api/clientes/{id}
```

---

## Atualizar

```http
PUT /api/clientes/{id}
```

---

## Excluir

```http
DELETE /api/clientes/{id}
```

---

# 🧾 Vendas

## Criar

```http
POST /api/vendas
```

Exemplo:

```json
{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 2,
      "quantidade": 1
    }
  ]
}
```

O backend busca automaticamente o preço atual de cada produto e calcula:

```text
preço unitário × quantidade = subtotal
```

Depois soma os subtotais para obter o valor total da venda.

---

## Listar

```http
GET /api/vendas
```

---

## Buscar por ID

```http
GET /api/vendas/{id}
```

---

## Atualizar

```http
PUT /api/vendas/{id}
```

---

## Excluir

```http
DELETE /api/vendas/{id}
```

---

# 🧪 Testando a API

A API pode ser testada utilizando ferramentas como:

- Postman
- Insomnia
- Bruno
- cURL

URL base:

```text
http://localhost:8082
```

Exemplos:

```text
http://localhost:8082/api/categorias
http://localhost:8082/api/produtos
http://localhost:8082/api/clientes
http://localhost:8082/api/vendas
```

---

# ✅ Status atual

| Funcionalidade | Status |
|---|---|
| Spring Boot + Kotlin | ✅ |
| Java 21 | ✅ |
| SQLite | ✅ |
| API REST | ✅ |
| CRUD Categoria | ✅ |
| CRUD Produto | ✅ |
| CRUD Cliente | ✅ |
| CRUD Venda | ✅ |
| Relacionamentos JPA | ✅ |
| Validação de dados | ✅ |

---


### MQTT

O backend será conectado a um Broker MQTT e ficará inscrito no tópico utilizado pelo dispositivo IoT.

Fluxo planejado:

```text
ESP32 + Sensor
      │
      │ MQTT
      ▼
 MQTT Broker
      │
      ▼
Spring Boot
      │
      ▼
SQLite / API REST
```

---

### IoT

O projeto utilizará um dispositivo **ESP32 programado em C++**, responsável pela leitura de um sensor e publicação das informações utilizando MQTT.

O backend será responsável por receber e processar essas informações.

---

# 🎓 Projeto acadêmico

Projeto desenvolvido como parte do curso de **Desenvolvimento de Software Multiplataforma**.

O objetivo do projeto é desenvolver uma solução de **Loja Inteligente**, integrando:

- Backend
- API REST
- Kotlin Multiplatform
- Internet das Coisas
- MQTT
- ESP32
- Sensores
- Autenticação e autorização

