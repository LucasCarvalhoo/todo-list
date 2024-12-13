-- Tabela Usuario
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Tabela Categoria
CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    usuario_id BIGINT,
    CONSTRAINT fk_categoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE
);

-- Tabela Tarefa
CREATE TABLE tarefa (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    data_vencimento DATE,
    categoria_id BIGINT,
    usuario_id BIGINT,
    CONSTRAINT fk_tarefa_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id) ON DELETE SET NULL,
    CONSTRAINT fk_tarefa_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE
);
