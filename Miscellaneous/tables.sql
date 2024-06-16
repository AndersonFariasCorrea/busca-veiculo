CREATE TABLE tarefa(
    id bigserial not null,
    titulo VARCHAR(100) not null,
    descricao VARCHAR(200) not null
);
ALTER TABLE tarefa ADD CONSTRAINT tarefa_pk PRIMARY KEY (id);

CREATE TABLE car_model(
    id bigserial not null,
    name VARCHAR(500) NOT NULL,
    numLista DATE NOT NULL,
    frete bool default false
);
ALTER TABLE car_model ADD CONSTRAINT condicaoMes_pk PRIMARY KEY (id);