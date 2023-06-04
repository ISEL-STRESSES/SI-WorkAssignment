BEGIN TRANSACTION;

-- Create domain
DROP DOMAIN IF EXISTS ALPHANUMERIC CASCADE;
DROP DOMAIN IF EXISTS email CASCADE;
DROP DOMAIN IF EXISTS url CASCADE;

-- Domains
CREATE DOMAIN ALPHANUMERIC AS VARCHAR(10) CHECK (VALUE ~* '^[A-Z0-9]+$');
CREATE DOMAIN URL AS VARCHAR(255) CHECK (VALUE ~* '^(http|https)://[a-zA-Z0-9\-\.]+\.[a-zA-Z]{2,3}(/\S*)?$');
CREATE DOMAIN email AS varchar(254) CHECK (VALUE ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Z]{2,}$');

-- Tables
-- Região
CREATE TABLE IF NOT EXISTS regiao(
    nome VARCHAR(50) PRIMARY KEY
);

-- Jogador
CREATE TABLE IF NOT EXISTS jogador(
    id          INT         PRIMARY KEY
        GENERATED BY DEFAULT AS IDENTITY,
    username    VARCHAR(50) NOT NULL,
    email       EMAIL       NOT NULL,
    estado      VARCHAR(20) DEFAULT 'Ativo',
    nome_regiao VARCHAR(50) NOT NULL REFERENCES regiao(nome) ON DELETE CASCADE,

    UNIQUE(username),
    UNIQUE(email),
    UNIQUE(username, email),

    CONSTRAINT estado_jogador CHECK(estado ~* '^(ativo|banido|inativo)$')
);

-- Jogo
CREATE TABLE IF NOT EXISTS jogo(
    id   ALPHANUMERIC PRIMARY KEY,
    nome VARCHAR(50)  NOT NULL,
    url  URL          NOT NULL,

    UNIQUE(nome)
);

-- Conversa
CREATE TABLE IF NOT EXISTS conversa
(
    id   INT         PRIMARY KEY
        GENERATED BY DEFAULT AS IDENTITY,
    nome VARCHAR(50) NOT NULL
);

-- Jogador Estatistica
CREATE TABLE IF NOT EXISTS jogador_estatistica(
    id_jogador   INT PRIMARY KEY REFERENCES jogador(id) ON DELETE CASCADE,
    nr_partidas  INT DEFAULT 0,
    nr_jogos     INT DEFAULT 0,
    total_pontos INT DEFAULT 0
);

-- Jogo Estatistica
CREATE TABLE IF NOT EXISTS jogo_estatistica(
    id_jogo      ALPHANUMERIC PRIMARY KEY REFERENCES jogo(id) ON DELETE CASCADE,
    nr_partidas  INT NOT NULL DEFAULT 0,
    nr_jogadores INT NOT NULL DEFAULT 0,
    total_pontos INT NOT NULL DEFAULT 0
);

-- Partida
CREATE TABLE IF NOT EXISTS partida(
    nr          INT          NOT NULL,
    id_jogo     ALPHANUMERIC NOT NULL REFERENCES jogo(id) ON DELETE CASCADE,
    data_inicio DATE         NOT NULL,
    data_fim    DATE,
    nome_regiao VARCHAR(50)  NOT NULL REFERENCES regiao(nome),

    UNIQUE (id_jogo, nr),

    CONSTRAINT date_constraint CHECK(data_inicio < data_fim),

    CONSTRAINT pk_partida PRIMARY KEY(id_jogo, nr)
);

-- Partida normal
CREATE TABLE IF NOT EXISTS partida_normal(
    id_jogo     ALPHANUMERIC NOT NULL,
    nr          INT          NOT NULL,
    dificuldade INT          NOT NULL,

    UNIQUE (id_jogo, nr),

    CONSTRAINT dificuldade_constraint CHECK(dificuldade BETWEEN 1 and 5),

    CONSTRAINT fk_partida_normal FOREIGN KEY(id_jogo, nr) REFERENCES partida(id_jogo, nr) ON DELETE CASCADE,
    CONSTRAINT pk_partida_normal PRIMARY KEY(id_jogo, nr)
);

-- Partida multijogador
CREATE TABLE IF NOT EXISTS partida_multijogador(
    id_jogo    ALPHANUMERIC NOT NULL,
    nr         INT          NOT NULL,
    estado     VARCHAR(20)  DEFAULT 'por iniciar',

    CONSTRAINT estado_constraint CHECK(estado ~* '^(por iniciar|a aguardar jogadores|em curso|terminada)$'),
    CONSTRAINT fk_partida_multijogador FOREIGN KEY(id_jogo, nr) REFERENCES partida(id_jogo, nr) ON DELETE CASCADE,
    CONSTRAINT pk_partida_multijogador PRIMARY KEY(nr, id_jogo)
);

-- Cracha
CREATE TABLE IF NOT EXISTS cracha(
    nome          VARCHAR(50)  NOT NULL,
    id_jogo       ALPHANUMERIC NOT NULL REFERENCES jogo(id) ON DELETE CASCADE,
    imagem        URL          NOT NULL,
    limite_pontos INT          NOT NULL,

    UNIQUE (id_jogo, nome),

    CONSTRAINT pk_cracha PRIMARY KEY(nome, id_jogo)
);

-- Mensagem
CREATE TABLE IF NOT EXISTS mensagem(
    nr_ordem    INT  NOT NULL,
    id_conversa INT  NOT NULL REFERENCES conversa(id) ON DELETE CASCADE,
    id_jogador  INT  NOT NULL REFERENCES jogador(id),
    texto       TEXT NOT NULL,
    data        DATE NOT NULL,

    UNIQUE(nr_ordem, id_conversa, id_jogador),

    CONSTRAINT pk_mensagem PRIMARY KEY(nr_ordem, id_conversa, id_jogador)
);

-- Compra (Jogador - Jogo)
CREATE TABLE IF NOT EXISTS compra(
    id_jogador INT             NOT NULL REFERENCES jogador(id),
    id_jogo    ALPHANUMERIC    NOT NULL REFERENCES jogo(id),
    data       DATE            NOT NULL,
    preco      MONEY DEFAULT 0 NOT NULL,

    UNIQUE(id_jogador, id_jogo),

    CONSTRAINT pk_compra PRIMARY KEY(id_jogador, id_jogo)
);

-- Joga (Jogador - Partida)
CREATE TABLE IF NOT EXISTS joga(
    id_jogo    ALPHANUMERIC NOT NULL,
    nr_partida INT          NOT NULL,
    id_jogador INT          NOT NULL REFERENCES jogador(id),
    pontuacao  INT          DEFAULT 0,

    CONSTRAINT fk_joga_partida FOREIGN KEY(id_jogo, nr_partida) REFERENCES partida(id_jogo, nr) ON DELETE CASCADE,
    CONSTRAINT pk_joga PRIMARY KEY(id_jogador, nr_partida, id_jogo)
);

-- Ganha (Jogador - Cracha)
CREATE TABLE IF NOT EXISTS ganha(
    id_jogador  INT          NOT NULL REFERENCES jogador(id) ON DELETE CASCADE,
    id_jogo     ALPHANUMERIC NOT NULL,
    nome_cracha VARCHAR(50)  NOT NULL,

    UNIQUE(id_jogador, id_jogo, nome_cracha),

    CONSTRAINT fk_ganha_cracha FOREIGN KEY(id_jogo, nome_cracha) REFERENCES cracha(id_jogo, nome) ON DELETE CASCADE,
    CONSTRAINT pk_ganha PRIMARY KEY(id_jogador, id_jogo, nome_cracha)
);

-- Participa (Jogador - Conversa)
CREATE TABLE IF NOT EXISTS participa(
    id_jogador  INT NOT NULL REFERENCES jogador(id)  ON DELETE CASCADE,
    id_conversa INT NOT NULL REFERENCES conversa(id) ON DELETE CASCADE,

    CONSTRAINT pk_participa PRIMARY KEY(id_jogador, id_conversa)
);

-- Amigo
CREATE TABLE IF NOT EXISTS amigo(
    id_jogador1 INT NOT NULL REFERENCES jogador(id) ON DELETE CASCADE,
    id_jogador2 INT NOT NULL REFERENCES jogador(id) ON DELETE CASCADE,

    CONSTRAINT pk_amigo PRIMARY KEY(id_jogador1, id_jogador2)
);

COMMIT;
