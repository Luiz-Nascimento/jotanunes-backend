CREATE TABLE IF NOT EXISTS catalogo_ambientes (
    id serial PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo VARCHAR(20) NOT NULL
);