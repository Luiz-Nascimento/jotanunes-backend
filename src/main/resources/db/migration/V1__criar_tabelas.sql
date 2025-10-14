
BEGIN;


CREATE TABLE IF NOT EXISTS public.empreendimentos
(
    id serial PRIMARY KEY,
    nome character varying(255) NOT NULL,
    localizacao character varying(255) NOT NULL,
    descricao text NOT NULL,
    observacoes text
);

CREATE TABLE IF NOT EXISTS public.documentos
(
    id serial PRIMARY KEY,
    empreendimento_id INTEGER UNIQUE NOT NULL,
    data_criacao timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status character varying(20) NOT NULL DEFAULT 'RASCUNHO',
    data_atualizacao timestamp without time zone,
    FOREIGN KEY (empreendimento_id) REFERENCES empreendimentos(id)
);

CREATE TABLE IF NOT EXISTS public.ambientes
(
    id serial PRIMARY KEY,
    empreendimento_id INTEGER NOT NULL,
    nome character varying(80) NOT NULL,
    tipo character varying(20) NOT NULL,
    FOREIGN KEY (empreendimento_id) REFERENCES empreendimentos(id)
);

CREATE TABLE IF NOT EXISTS public.itens
(
    id serial PRIMARY KEY,
    ambiente_id INTEGER NOT NULL,
    nome character varying(40) NOT NULL,
    descricao character varying(255) NOT NULL,
    status character varying(20) NOT NULL DEFAULT 'PENDENTE',
    FOREIGN KEY (ambiente_id) REFERENCES ambientes(id)
);

CREATE TABLE IF NOT EXISTS public.usuarios
(
    id uuid PRIMARY KEY,
    nome character varying(30) NOT NULL UNIQUE,
    senha character varying(255) NOT NULL,
    email character varying(255) NOT NULL UNIQUE,
    data_criacao timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    criado_por uuid,
    ativo boolean NOT NULL DEFAULT TRUE,
    nivel_acesso character varying(10) NOT NULL,
    FOREIGN KEY (criado_por) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS public.materiais
(
    id serial PRIMARY KEY,
    nome character varying(30) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.marcas
(
    id serial PRIMARY KEY,
    nome character varying(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.empreendimento_material_marca
(
    id serial PRIMARY KEY,
    empreendimento_id INTEGER NOT NULL,
    material_id INTEGER NOT NULL,
    marca_id INTEGER NOT NULL,
    FOREIGN KEY (empreendimento_id) REFERENCES empreendimentos(id),
    FOREIGN KEY (material_id) REFERENCES materiais(id),
    FOREIGN KEY (marca_id) REFERENCES marcas(id)
);

CREATE TABLE IF NOT EXISTS public.revisao_item
(
    id serial PRIMARY KEY,
    item_id INTEGER NOT NULL,
    usuario_id uuid NOT NULL,
    status character varying(20) NOT NULL,
    motivo text,
    data_avaliacao timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE UNIQUE INDEX idx_empreendimento_material_marca ON empreendimento_material_marca (empreendimento_id, material_id, marca_id);

CREATE TYPE ambiente_status AS ENUM ('PENDENTE', 'APROVADO', 'REPROVADO');


ALTER TABLE ambientes
    ADD COLUMN status ambiente_status NOT NULL DEFAULT 'PENDENTE';


END;