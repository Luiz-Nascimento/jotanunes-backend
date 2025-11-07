ALTER TABLE empreendimentos
ADD COLUMN data_criacao timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN criado_por uuid REFERENCES usuarios(id) ON DELETE SET NULL;