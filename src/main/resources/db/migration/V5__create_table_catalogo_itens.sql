CREATE TABLE catalogo_itens (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    descricao VARCHAR(160),
    ambiente_id INT REFERENCES catalogo_ambientes(id)
);