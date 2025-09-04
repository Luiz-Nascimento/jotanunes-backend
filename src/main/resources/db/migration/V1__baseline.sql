/* Lógico_1_JOTANUNES (1): */

CREATE TABLE Usuario (
                         id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
                         nome varchar(80) NOT NULL,
                         senha varchar(255) NOT NULL,
                         perfil varchar(12) NOT NULL,
                         email varchar(40) NOT NULL UNIQUE,
                         data_criacao timestamp WITH TIME ZONE DEFAULT NOW(),
                         criado_por uuid REFERENCES Usuario(id),
                         ativo boolean DEFAULT TRUE
);

CREATE TABLE Empreendimento (
                                id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
                                nome varchar(80) NOT NULL,
                                descricao text,
                                data_criacao timestamp WITH TIME ZONE DEFAULT NOW(),
                                status boolean DEFAULT TRUE,
                                fk_Usuario_id uuid
);

CREATE TABLE Ambiente (
                          id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
                          nome varchar(80) NOT NULL UNIQUE,
                          tipo varchar(80) NOT NULL
);

CREATE TABLE Item (
                      id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
                      nome varchar(80) NOT NULL UNIQUE
);

CREATE TABLE HistoricoAlteracao (
                                    id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
                                    data_hora timestamp,
                                    motivo varchar(255),
                                    acao varchar(40) NOT NULL,
                                    dados_antigos jsonb NOT NULL,
                                    dados_novos jsonb NOT NULL,
                                    tabela_afetada varchar(30),
                                    fk_Especificacao_id int,
                                    fk_Usuario_id uuid
);

CREATE TABLE Especificacao (
                               id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                               descricao text,
                               fk_Ambiente_id int,
                               fk_Item_id int,
                               fk_Material_id int
);

CREATE TABLE Checklist_Especificacao (
                                         id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                         status boolean DEFAULT FALSE,
                                         observacao text,
                                         fk_Empreendimento_id uuid,
                                         fk_Especificacao_id int
);

CREATE TABLE Material (
                          id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
                          nome varchar(80) NOT NULL UNIQUE,
                          descricao text
);

CREATE TABLE Marca (
                       id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
                       nome varchar(80) NOT NULL
);

CREATE TABLE Lista_Marca (
                             fk_Marca_id int,
                             fk_Material_id int,
                             id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY
);

ALTER TABLE Empreendimento ADD CONSTRAINT FK_Empreendimento_2
    FOREIGN KEY (fk_Usuario_id)
        REFERENCES Usuario (id)
        ON DELETE CASCADE;

ALTER TABLE HistoricoAlteracao ADD CONSTRAINT FK_HistoricoAlteracao_2
    FOREIGN KEY (fk_Especificacao_id)
        REFERENCES Especificacao (id)
        ON DELETE CASCADE;

ALTER TABLE HistoricoAlteracao ADD CONSTRAINT FK_HistoricoAlteracao_3
    FOREIGN KEY (fk_Usuario_id)
        REFERENCES Usuario (id)
        ON DELETE CASCADE;

ALTER TABLE Especificacao ADD CONSTRAINT FK_Especificacao_2
    FOREIGN KEY (fk_Ambiente_id)
        REFERENCES Ambiente (id)
        ON DELETE RESTRICT;

ALTER TABLE Especificacao ADD CONSTRAINT FK_Especificacao_3
    FOREIGN KEY (fk_Item_id)
        REFERENCES Item (id)
        ON DELETE RESTRICT;

ALTER TABLE Especificacao ADD CONSTRAINT FK_Especificacao_4
    FOREIGN KEY (fk_Material_id)
        REFERENCES Material (id)
        ON DELETE CASCADE;

ALTER TABLE Checklist_Especificacao ADD CONSTRAINT FK_Checklist_Especificacao_2
    FOREIGN KEY (fk_Empreendimento_id)
        REFERENCES Empreendimento (id);

ALTER TABLE Checklist_Especificacao ADD CONSTRAINT FK_Checklist_Especificacao_3
    FOREIGN KEY (fk_Especificacao_id)
        REFERENCES Especificacao (id);

ALTER TABLE Lista_Marca ADD CONSTRAINT FK_Lista_Marca_1
    FOREIGN KEY (fk_Marca_id)
        REFERENCES Marca (id);

ALTER TABLE Lista_Marca ADD CONSTRAINT FK_Lista_Marca_2
    FOREIGN KEY (fk_Material_id)
        REFERENCES Material (id);


