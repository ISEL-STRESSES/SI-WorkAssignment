BEGIN TRANSACTION;

TRUNCATE TABLE amigo CASCADE;

TRUNCATE TABLE participa CASCADE;

TRUNCATE TABLE ganha CASCADE;

TRUNCATE TABLE joga CASCADE;

TRUNCATE TABLE compra CASCADE;

TRUNCATE TABLE mensagem CASCADE;

TRUNCATE TABLE cracha CASCADE;

TRUNCATE TABLE partida_multijogador CASCADE;

TRUNCATE TABLE partida_normal CASCADE;

TRUNCATE TABLE partida CASCADE;

TRUNCATE TABLE jogo_estatistica CASCADE;

TRUNCATE TABLE jogador_estatistica CASCADE;

TRUNCATE TABLE conversa CASCADE;
ALTER SEQUENCE conversa_id_seq RESTART WITH 1;

TRUNCATE TABLE jogo CASCADE;

TRUNCATE TABLE jogador CASCADE;
ALTER SEQUENCE jogador_id_seq RESTART WITH 1;

TRUNCATE TABLE regiao CASCADE;

COMMIT;
