ALTER TABLE public.ambientes
    DROP COLUMN nome,
    DROP COLUMN tipo;

ALTER TABLE public.ambientes
    ADD COLUMN catalogo_ambiente_id INTEGER REFERENCES public.catalogo_ambientes(id);

