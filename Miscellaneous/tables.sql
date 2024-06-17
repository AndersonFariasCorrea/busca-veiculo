CREATE TABLE tarefa(
    id bigserial not null PRIMARY KEY,
    titulo VARCHAR(100) not null,
    descricao VARCHAR(200) not null
);

CREATE TABLE car_model (
   id bigserial NOT NULL PRIMARY KEY,
   versao VARCHAR(255) NULL,
   model VARCHAR(255) NULL,
   config VARCHAR(255) NULL,
   conteudo TEXT NULL,
   preco_nacional FLOAT NULL,
   preco_zfm FLOAT NULL,
   preco_ao FLOAT NULL,
   preco_alc FLOAT NULL
);