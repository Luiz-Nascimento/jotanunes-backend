UPDATE empreendimentos
SET status = 'RASCUNHO'
WHERE status = 'PENDENTE';

ALTER TABLE empreendimentos
    ALTER COLUMN status SET DEFAULT 'RASCUNHO';