
BEGIN;

CREATE TYPE tipologia_empreendimento AS ENUM ('SOBRADO', 'TORRE');
CREATE TYPE sistema_construtivo AS ENUM ('PC');
CREATE TYPE linha_empreendimento AS ENUM ('MAIS_VIVER', 'RESIDENCE');
CREATE TYPE empreendimento_status AS ENUM ('PENDENTE', 'APROVADO', 'REPROVADO');

CREATE TABLE IF NOT EXISTS empreendimentos
(
    id serial PRIMARY KEY,
    tipologia tipologia_empreendimento NOT NULL,
    sistema_construtivo sistema_construtivo NOT NULL,
    linha linha_empreendimento NOT NULL,
    nome character varying(255) NOT NULL,
    status empreendimento_status NOT NULL DEFAULT 'PENDENTE',
    localizacao character varying(255) NOT NULL,
    descricao text NOT NULL,
    observacoes text[] NOT NULL DEFAULT '{}'
);

CREATE INDEX idx_empreendimentos_segmento
    ON empreendimentos(tipologia, sistema_construtivo, linha);

CREATE TYPE documento_status AS ENUM ('PENDENTE', 'APROVADO', 'REPROVADO');
CREATE TABLE IF NOT EXISTS documentos
(
    id serial PRIMARY KEY,
    empreendimento_id INTEGER UNIQUE NOT NULL,
    data_criacao timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status documento_status NOT NULL DEFAULT 'PENDENTE',
    data_atualizacao timestamp without time zone,
    FOREIGN KEY (empreendimento_id) REFERENCES empreendimentos(id) ON DELETE CASCADE
);
CREATE TYPE ambiente_tipo AS ENUM ('PRIVATIVO', 'AREA_COMUM');
CREATE TABLE IF NOT EXISTS catalogo_ambientes (
    id serial PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    tipo ambiente_tipo NOT NULL
);

CREATE TYPE ambiente_status AS ENUM ('PENDENTE', 'APROVADO', 'REPROVADO');
CREATE TABLE IF NOT EXISTS ambientes
(
    id serial PRIMARY KEY,
    empreendimento_id INTEGER NOT NULL,
    catalogo_ambiente_id INTEGER NOT NULL REFERENCES catalogo_ambientes(id),
    status ambiente_status NOT NULL DEFAULT 'PENDENTE',
    FOREIGN KEY (empreendimento_id) REFERENCES empreendimentos(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_ambientes_empreendimento_id ON ambientes(empreendimento_id);
CREATE INDEX IF NOT EXISTS idx_ambientes_catalogo_ambiente_id ON ambientes(catalogo_ambiente_id);

CREATE TABLE IF NOT EXISTS catalogo_itens (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    descricao VARCHAR(255),
    ambiente_id INT REFERENCES catalogo_ambientes(id) ON DELETE RESTRICT
);
CREATE INDEX IF NOT EXISTS idx_catalogo_itens_ambiente_id ON catalogo_itens(ambiente_id);

CREATE TYPE item_status AS ENUM ('PENDENTE', 'APROVADO', 'REPROVADO');
CREATE TABLE IF NOT EXISTS itens
(
    id serial PRIMARY KEY,
    catalogo_item_id INTEGER NOT NULL REFERENCES catalogo_itens(id),
    ambiente_id INTEGER NOT NULL,
    descricao_customizada character varying(255),
    status item_status NOT NULL DEFAULT 'PENDENTE',
    FOREIGN KEY (ambiente_id) REFERENCES ambientes(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_itens_ambiente_id ON itens(ambiente_id);
CREATE INDEX IF NOT EXISTS idx_itens_catalogo_item_id ON itens(catalogo_item_id);


CREATE TYPE nivel_acesso_enum AS ENUM('PADRAO', 'GESTOR', 'ADMIN');
CREATE TABLE IF NOT EXISTS usuarios
(
    id uuid PRIMARY KEY,
    nome character varying(30) NOT NULL UNIQUE,
    senha character varying(255) NOT NULL,
    email character varying(255) NOT NULL UNIQUE,
    data_criacao timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    criado_por uuid,
    ativo boolean NOT NULL DEFAULT TRUE,
    nivel_acesso nivel_acesso_enum NOT NULL DEFAULT 'PADRAO',
    alterar_senha BOOLEAN DEFAULT FALSE NOT NULL,
    FOREIGN KEY (criado_por) REFERENCES usuarios(id) ON DELETE SET NULL
);
CREATE INDEX IF NOT EXISTS idx_usuarios_criado_por ON usuarios(criado_por);

CREATE TABLE IF NOT EXISTS materiais
(
    id serial PRIMARY KEY,
    nome character varying(30) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS marcas
(
    id serial PRIMARY KEY,
    nome character varying(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS empreendimento_material_marca
(
    id serial PRIMARY KEY,
    empreendimento_id INTEGER NOT NULL,
    material_id INTEGER NOT NULL,
    marca_id INTEGER NOT NULL,
    FOREIGN KEY (empreendimento_id) REFERENCES empreendimentos(id) ON DELETE CASCADE,
    FOREIGN KEY (material_id) REFERENCES materiais(id) ON DELETE CASCADE,
    FOREIGN KEY (marca_id) REFERENCES marcas(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX idx_empreendimento_material_marca ON empreendimento_material_marca (empreendimento_id, material_id, marca_id);
CREATE INDEX IF NOT EXISTS idx_emm_material_id ON empreendimento_material_marca(material_id);
CREATE INDEX IF NOT EXISTS idx_emm_marca_id ON empreendimento_material_marca(marca_id);

CREATE TYPE revisao_status AS ENUM ('APROVADO', 'REPROVADO');
CREATE TABLE IF NOT EXISTS revisao_item
(
    id serial PRIMARY KEY,
    item_id INTEGER NOT NULL,
    usuario_id uuid NOT NULL,
    status revisao_status NOT NULL,
    motivo text,
    data_avaliacao timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE RESTRICT,
    FOREIGN KEY (item_id) REFERENCES itens(id) ON DELETE CASCADE,
    CONSTRAINT chk_motivo_reprovado
        CHECK ( (status = 'APROVADO' AND motivo IS NULL) OR (status = 'REPROVADO' AND motivo IS NOT NULL) )
);
CREATE INDEX IF NOT EXISTS  idx_revisoes_item_id ON revisao_item(item_id);
CREATE INDEX IF NOT EXISTS idx_revisoes_usuario_id ON revisao_item(usuario_id);

END;