
CREATE TYPE empreendimento_status AS ENUM ('PENDENTE', 'APROVADO', 'REPROVADO');
ALTER TABLE empreendimentos
    ADD COLUMN status empreendimento_status NOT NULL DEFAULT 'PENDENTE';
