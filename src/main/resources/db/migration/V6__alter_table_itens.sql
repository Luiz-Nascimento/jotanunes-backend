ALTER TABLE public.itens
    DROP COLUMN nome,
    DROP COLUMN descricao,
    ADD COLUMN descricao_customizada VARCHAR(160),
    ADD COLUMN catalogo_item_id INTEGER REFERENCES catalogo_itens(id);
