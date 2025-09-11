
CREATE TABLE IF NOT EXISTS public."empreendimento"
(
    id uuid DEFAULT gen_random_uuid(),
    nome character varying(255) NOT NULL UNIQUE,
    localizacao character varying(255) NOT NULL,
    descricao text NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public."especificacao_tecnica"
(
    id uuid DEFAULT gen_random_uuid(),
    empreendimento_id uuid NOT NULL UNIQUE,
    data_criacao timestamp without time zone NOT NULL DEFAULT NOW(),
    status character varying(20) NOT NULL DEFAULT 'RASCUNHO',
    data_atualizacao timestamp without time zone,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public."ambiente"
(
    id uuid DEFAULT gen_random_uuid(),
    especificacao_tecnica_id uuid NOT NULL,
    nome character varying(80) NOT NULL,
    tipo character varying(20) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public."item"
(
    id uuid DEFAULT gen_random_uuid(),
    ambiente_id uuid NOT NULL,
    nome character varying(40) NOT NULL,
    descricao character varying(255) NOT NULL,
    observacao character varying(255),
    tipo_material_id uuid NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public."marca"
(
    id uuid DEFAULT gen_random_uuid(),
    nome character varying(80) NOT NULL UNIQUE,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public."tipo_material"
(
    id uuid DEFAULT gen_random_uuid(),
    nome character varying(40) NOT NULL UNIQUE,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public."especificacao_marca"
(
    id uuid DEFAULT gen_random_uuid(),
    especificacao_id uuid NOT NULL,
    tipo_material_id uuid NOT NULL,
    marca_id uuid NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public."checklist_especificacao"
(
    id uuid DEFAULT gen_random_uuid(),
    status character varying(20) NOT NULL,
    observacao text,
    data_revisao timestamp without time zone NOT NULL,
    especificacao_id uuid NOT NULL,
    usuario_id uuid NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public."usuario"
(
    id uuid DEFAULT gen_random_uuid(),
    nome character varying(80) NOT NULL UNIQUE,
    senha character varying(255) NOT NULL,
    email character varying(255) NOT NULL UNIQUE,
    data_criacao timestamp without time zone NOT NULL DEFAULT NOW(),
    criado_por uuid,
    ativo boolean NOT NULL DEFAULT TRUE,
    nivel_acesso character varying(10) NOT NULL,
    PRIMARY KEY (id)
    );


CREATE TABLE IF NOT EXISTS public."historico_alteracao"
(
    id uuid DEFAULT gen_random_uuid(),
    data_hora timestamp without time zone NOT NULL,
    acao character varying(25) NOT NULL,
    motivo text,
    dados_antes json,
    dados_depois json,
    tabela_afetada character varying NOT NULL,
    usuario_id uuid NOT NULL,
    especificacao_id uuid NOT NULL,
    PRIMARY KEY (id)
    );

-- FOREIGN KEYS CORRETAS (todas invertidas)

-- 1. especificacao_tecnica referencia empreendimento
ALTER TABLE public."especificacao_tecnica"
    ADD CONSTRAINT fk_especificacao_empreendimento
        FOREIGN KEY (empreendimento_id)
            REFERENCES public."empreendimento"(id);

-- 2. Ambiente referencia EspecificacaoTecnica
ALTER TABLE public."ambiente"
    ADD CONSTRAINT fk_ambiente_especificacao
        FOREIGN KEY (especificacao_tecnica_id)
            REFERENCES public."especificacao_tecnica"(id);

-- 3. Item referencia Ambiente
ALTER TABLE public."item"
    ADD CONSTRAINT fk_item_ambiente
        FOREIGN KEY (ambiente_id)
            REFERENCES public."ambiente"(id);

-- 4. Item referencia TipoMaterial (FK que faltava!)
ALTER TABLE public."item"
    ADD CONSTRAINT fk_item_tipo_material
        FOREIGN KEY (tipo_material_id)
            REFERENCES public."tipo_material"(id);

-- 5. EspecificacaoMarca referencia EspecificacaoTecnica
ALTER TABLE public."especificacao_marca"
    ADD CONSTRAINT fk_esp_marca_especificacao
        FOREIGN KEY (especificacao_id)
            REFERENCES public."especificacao_tecnica"(id);

-- 6. EspecificacaoMarca referencia TipoMaterial
ALTER TABLE public."especificacao_marca"
    ADD CONSTRAINT fk_esp_marca_tipo_material
        FOREIGN KEY (tipo_material_id)
            REFERENCES public."tipo_material"(id);

-- 7. EspecificacaoMarca referencia Marca
ALTER TABLE public."especificacao_marca"
    ADD CONSTRAINT fk_esp_marca_marca
        FOREIGN KEY (marca_id)
            REFERENCES public."marca"(id);

-- 8. Checklist_Especificacao referencia EspecificacaoTecnica
ALTER TABLE public."checklist_especificacao"
    ADD CONSTRAINT fk_checklist_especificacao
        FOREIGN KEY (especificacao_id)
            REFERENCES public."especificacao_tecnica"(id);

-- 9. Checklist_Especificacao referencia Usuario
ALTER TABLE public."checklist_especificacao"
    ADD CONSTRAINT fk_checklist_usuario
        FOREIGN KEY (usuario_id)
            REFERENCES public."usuario"(id);

-- 10. Historico_Alteracao referencia EspecificacaoTecnica
ALTER TABLE public."historico_alteracao"
    ADD CONSTRAINT fk_historico_especificacao
        FOREIGN KEY (especificacao_id)
            REFERENCES public."especificacao_tecnica"(id);

-- 11. Historico_Alteracao referencia Usuario
ALTER TABLE public."historico_alteracao"
    ADD CONSTRAINT fk_historico_usuario
        FOREIGN KEY (usuario_id)
            REFERENCES public."usuario"(id);

-- 12. Usuario autoreferencia (criado_por)
ALTER TABLE public."usuario"
    ADD CONSTRAINT fk_usuario_criado_por
        FOREIGN KEY (criado_por)
            REFERENCES public."usuario"(id);

END;

