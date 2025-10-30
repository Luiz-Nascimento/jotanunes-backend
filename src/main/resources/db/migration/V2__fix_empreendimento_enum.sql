CREATE TYPE segmento_empreendimento AS ENUM ('TORRE_PC_MAIS_VIVER', 'TORRE_PC_RESIDENCE', 'SOBRADO_PC_MAIS_VIVER', 'SOBRADO_PC_RESIDENCE');

TRUNCATE TABLE empreendimentos CASCADE;

ALTER TABLE empreendimentos
DROP COLUMN tipologia,
DROP COLUMN sistema_construtivo,
DROP COLUMN linha;

DROP TYPE tipologia_empreendimento;
DROP TYPE sistema_construtivo;
DROP TYPE linha_empreendimento;



ALTER TABLE empreendimentos
ADD COLUMN segmento segmento_empreendimento NOT NULL;