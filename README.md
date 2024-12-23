# todo-list

# Documentação da API

## **AuthenticationController**

Controlador responsável pela autenticação e registro de usuários.

### **Endpoints**

#### **POST /auth/login**

- **Descrição**: Realiza a autenticação de um usuário.
- **Request Body**:
  ```json
  {
    "email": "string",
    "senha": "string"
  }
  ```
- **Response**:
  - **200 OK**: Retorna o token de autenticação.
    ```json
    {
      "token": "string"
    }
    ```

#### **POST /auth/register**

- **Descrição**: Registra um novo usuário no sistema.
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "senha": "string",
    "role": "string"
  }
  ```
- **Response**:
  - **200 OK**: Usuário registrado com sucesso.
  - **409 Conflict**: Email já registrado.

---

## **CategoriaController**

Controlador responsável por gerenciar categorias de tarefas.

### **Endpoints**

#### **GET /categorias**

- **Descrição**: Retorna todas as categorias.

#### **GET /categorias/usuarios/{usuarioId}**

- **Descrição**: Retorna as categorias associadas a um usuário específico.
- **Path Variable**:
  - **usuarioId**: ID do usuário.

#### **POST /categorias**

- **Descrição**: Cria uma nova categoria para um usuário.
- **Request Body**:
  ```json
  {
    "nome": "string"
  }
  ```
- **Request Params**:
  - **usuarioId**: ID do usuário associado.
- **Response**:
  - **201 Created**: Categoria criada com sucesso.

#### **PUT /categorias**

- **Descrição**: Atualiza uma categoria existente.
- **Request Body**:
  ```json
  {
    "nome": "string"
  }
  ```
- **Request Params**:
  - **usuarioId**: ID do usuário associado.
  - **categoriaId**: ID da categoria a ser atualizada.

#### **DELETE /categorias/{id}**

- **Descrição**: Deleta uma categoria.
- **Path Variable**:
  - **id**: ID da categoria.

---

## **TarefaController**

Controlador responsável por gerenciar tarefas.

### **Endpoints**

#### **GET /tarefas**

- **Descrição**: Retorna todas as tarefas.

#### **GET /tarefas/usuario/{usuarioId}**

- **Descrição**: Retorna as tarefas associadas a um usuário.
- **Path Variable**:
  - **usuarioId**: ID do usuário.

#### **GET /tarefas/{id}**

- **Descrição**: Retorna uma tarefa específica pelo ID.
- **Path Variable**:
  - **id**: ID da tarefa.

#### **POST /tarefas**

- **Descrição**: Cria uma nova tarefa.
- **Request Body**:
  ```json
  {
    "titulo": "string",
    "descricao": "string",
    "status": "string",
    "data_vencimento": "string"
  }
  ```
- **Request Params**:
  - **usuarioId**: ID do usuário associado.
  - **categoriaId**: ID da categoria associada.

#### **PUT /tarefas/{tarefaId}**

- **Descrição**: Atualiza uma tarefa existente.
- **Request Body**:
  ```json
  {
    "titulo": "string",
    "descricao": "string",
    "status": "string",
    "data_vencimento": "string",
    "categoria": {
      "id": "number"
    }
  }
  ```
- **Path Variable**:
  - **tarefaId**: ID da tarefa a ser atualizada.

#### **DELETE /tarefas/{tarefaId}**

- **Descrição**: Deleta uma tarefa.
- **Path Variable**:
  - **tarefaId**: ID da tarefa.

---

## **UsuarioController**

Controlador responsável por gerenciar usuários.

### **Endpoints**

#### **GET /usuarios**

- **Descrição**: Retorna todos os usuários.

#### **PUT /usuarios/{id}**

- **Descrição**: Atualiza informações de um usuário.
- **Request Body**:
  ```json
  {
    "name": "string",
    "email": "string",
    "senha": "string",
    "categorias": [
      {
        "id": "number",
        "nome": "string"
      }
    ]
  }
  ```
- **Path Variable**:
  - **id**: ID do usuário.

#### **DELETE /usuarios/{id}**

- **Descrição**: Deleta um usuário.
- **Path Variable**:
  - **id**: ID do usuário.

---

## **Services**

### **AuthorizationService**

- **Descrição**: Serviço responsável por gerenciar a autenticação e autorização dos usuários.

