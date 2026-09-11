# 🛒 Loja Inteligente - Backend

Backend do projeto **Loja Inteligente**, desenvolvido em **Kotlin com Spring Boot**.

O projeto faz parte de uma aplicação multiplataforma para gerenciamento de uma loja inteligente. Nesta etapa estão implementados os CRUDs de **Categorias** e **Produtos**, utilizando uma API REST e banco de dados SQLite.

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
- Spring Security
- Jakarta Validation
- REST API

---

## 📌 Funcionalidades implementadas

Atualmente o backend possui dois CRUDs completos:

### Categorias

Permite:

- Criar uma categoria
- Listar categorias
- Buscar uma categoria por ID
- Atualizar uma categoria
- Excluir uma categoria

### Produtos

Permite:

- Criar um produto
- Listar produtos
- Buscar um produto por ID
- Atualizar um produto
- Excluir um produto

Cada produto pertence a uma categoria.

Exemplo:

```text
Categoria: Eletrônicos
│
├── Mouse
├── Teclado
└── Notebook
````

---

## 🗂️ Estrutura do projeto

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
├── config
│   └── SecurityConfig.kt
│
└── LojaInteligenteBackendApplication.kt
```

---

## 🗄️ Banco de dados

O projeto utiliza **SQLite**.

O arquivo do banco é criado automaticamente na raiz do projeto:

```text
loja_inteligente.db
```

A conexão é configurada no arquivo:

```text
src/main/resources/application.properties
```

Exemplo de configuração:

```properties
spring.datasource.url=jdbc:sqlite:loja_inteligente.db
spring.datasource.driver-class-name=org.sqlite.JDBC

spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8082
```

O Hibernate cria e atualiza as tabelas automaticamente durante o desenvolvimento.

---

# ▶️ Como executar o projeto

## Pré-requisitos

Para executar o projeto é necessário possuir:

* Java JDK 21
* Git
* IntelliJ IDEA ou outra IDE compatível com Kotlin
* Postman ou Insomnia para testar a API

Não é necessário instalar o Gradle separadamente, pois o projeto possui **Gradle Wrapper**.

---

## 1. Clone o repositório

```bash
git clone https://github.com/vitomazeli/loja-inteligente-backend.git
```

Entre na pasta do projeto:

```bash
cd loja-inteligente-backend
```

---

## 2. Verifique a versão do Java

Execute:

```bash
java -version
```

O projeto utiliza **Java 21**.

Exemplo:

```text
java version "21"
```

Também é possível verificar qual JVM o Gradle está utilizando:

### Windows

```powershell
.\gradlew.bat --version
```

### Linux/macOS

```bash
./gradlew --version
```

---

## 3. Faça o build do projeto

No Windows:

```powershell
.\gradlew.bat clean build
```

No Linux/macOS:

```bash
./gradlew clean build
```

Se tudo estiver configurado corretamente, deverá aparecer:

```text
BUILD SUCCESSFUL
```

---

## 4. Execute a aplicação

No Windows:

```powershell
.\gradlew.bat bootRun
```

No Linux/macOS:

```bash
./gradlew bootRun
```

A aplicação será iniciada em:

```text
http://localhost:8082
```

Quando aparecer uma mensagem semelhante a:

```text
Tomcat started on port 8082
```

o backend estará em execução.

Para interromper o servidor:

```text
Ctrl + C
```

---

# 📡 Endpoints da API

## Categorias

### Criar categoria

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

Resposta esperada:

```json
{
  "id": 1,
  "nome": "Eletrônicos",
  "descricao": "Produtos eletrônicos da loja",
  "ativo": true
}
```

---

### Listar categorias

```http
GET /api/categorias
```

---

### Buscar categoria por ID

```http
GET /api/categorias/{id}
```

Exemplo:

```http
GET /api/categorias/1
```

---

### Atualizar categoria

```http
PUT /api/categorias/{id}
```

Exemplo:

```json
{
  "nome": "Eletrônicos e Informática",
  "descricao": "Eletrônicos, computadores e periféricos",
  "ativo": true
}
```

---

### Excluir categoria

```http
DELETE /api/categorias/{id}
```

Exemplo:

```http
DELETE /api/categorias/1
```

Em caso de sucesso:

```text
204 No Content
```

---

# 📦 Produtos

## Criar produto

```http
POST /api/produtos
```

Antes de cadastrar um produto é necessário possuir uma categoria cadastrada.

Exemplo:

```json
{
  "nome": "Mouse Gamer",
  "descricao": "Mouse gamer RGB com 6 botões",
  "codigoBarras": "7891234567890",
  "preco": 129.90,
  "categoriaId": 1,
  "ativo": true
}
```

Exemplo de resposta:

```json
{
  "id": 1,
  "nome": "Mouse Gamer",
  "descricao": "Mouse gamer RGB com 6 botões",
  "codigoBarras": "7891234567890",
  "preco": 129.90,
  "categoriaId": 1,
  "categoriaNome": "Eletrônicos",
  "ativo": true
}
```

---

## Listar produtos

```http
GET /api/produtos
```

---

## Buscar produto por ID

```http
GET /api/produtos/{id}
```

Exemplo:

```http
GET /api/produtos/1
```

---

## Atualizar produto

```http
PUT /api/produtos/{id}
```

---

## Excluir produto

```http
DELETE /api/produtos/{id}
```

---

# 🔗 Relacionamento entre as entidades

O relacionamento atual é:

```text
Categoria
    │
    │ 1
    │
    │ N
    ▼
Produto
```

Uma categoria pode possuir vários produtos.

Cada produto pertence a uma categoria.

No banco de dados, a tabela `produtos` possui a chave estrangeira:

```text
categoria_id
```

que referencia:

```text
categorias.id
```

---

# 🏗️ Arquitetura utilizada

O backend utiliza uma arquitetura organizada em camadas:

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

### Controller

Responsável por receber as requisições HTTP e disponibilizar os endpoints REST.

### Service

Responsável pelas regras de negócio.

### Repository

Responsável pela comunicação com o banco de dados através do Spring Data JPA.

### Entity

Representa as tabelas do banco de dados.

### DTO

Define os dados recebidos e enviados pela API.

---

# 🔐 Segurança

O projeto possui a dependência do **Spring Security**.

Durante esta etapa de desenvolvimento, os endpoints estão liberados para facilitar os testes da API.

A implementação futura utilizará autenticação e autorização através de **JWT (JSON Web Token)**.

---

# 🧪 Testando a API

A API pode ser testada utilizando:

* Postman
* Insomnia
* Bruno
* cURL

URL base:

```text
http://localhost:8082
```

Exemplo:

```text
http://localhost:8082/api/categorias
```

---

# 🔮 Próximas etapas

O projeto ainda será expandido com:

* CRUD de Usuários
* CRUD de Estoque
* Autenticação com JWT
* Autorização por perfil de usuário
* Integração MQTT
* ESP32
* Sensor IoT
* Monitoramento inteligente de estoque
* Aplicação Mobile em Kotlin Multiplatform
* Aplicação Web em Kotlin Multiplatform
* Aplicação Desktop em Kotlin Multiplatform

A arquitetura planejada é:

```text
ESP32 + Sensor
      │
      │ MQTT
      ▼
 MQTT Broker
      │
      ▼
Spring Boot + Kotlin
      │
      ├── SQLite
      │
      └── REST API
             │
       ┌─────┼─────┐
       ▼     ▼     ▼
     Mobile  Web  Desktop
          Kotlin Multiplatform
```

---

## 👩‍💻 Projeto acadêmico

Projeto desenvolvido como parte das atividades acadêmicas do curso de **Desenvolvimento de Software Multiplataforma**.

O objetivo é desenvolver uma solução de **Loja Inteligente Multiplataforma**, integrando desenvolvimento backend, aplicações multiplataforma e Internet das Coisas (IoT).


