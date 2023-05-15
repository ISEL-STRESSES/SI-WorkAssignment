BEGIN TRANSACTION;
-- Model Restrictions

-- 1. The partida must be played by jogadores of the same regiao

-- This function is called before inserting a new row in the joga table and checks if the jogador is from the same
-- regiao as the partida he is playing in.
--
-- Example Usage:
-- INSERT INTO partida VALUES (now(), null, 1000000000, 'Portugal');
-- INSERT INTO joga (id_jogador, nr_partida) VALUES (1, 1);
CREATE OR REPLACE FUNCTION checkJogadorPartidaRegiao()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
    DECLARE
        regiao_nome VARCHAR(50);
        jogador_nome_regiao VARCHAR(50);
    BEGIN
        SELECT partida.nome_regiao INTO regiao_nome FROM partida WHERE partida.id_jogo = NEW.id_jogo;
        SELECT jogador.nome_regiao INTO jogador_nome_regiao FROM jogador WHERE jogador.id = NEW.id_jogador;
        IF (regiao_nome != jogador_nome_regiao) THEN
            RAISE EXCEPTION 'O jogador nao pertence a regiao da partida';
        END IF;
        RETURN NEW;
    END;
$$;

-- This trigger calls the function checkJogadorPartidaRegiao() before inserting a new row in the joga table
CREATE OR REPLACE TRIGGER checkJogadorPartidaRegiao
    BEFORE INSERT ON joga
    FOR EACH ROW
    EXECUTE FUNCTION checkJogadorPartidaRegiao();

------------------------------------------------------------------------------------------------------------------------
-- 2. The mensagem must be sent by a jogador of the conversa

-- This function is called before inserting a new row in the mensagem table and checks if the jogador is from the same
-- conversa as the mensagem he is sending.
--
-- Example Usage:
-- INSERT INTO mensagem VALUES (1, 1, 'ola', '2019-12-12 12:12:12');
CREATE OR REPLACE FUNCTION checkJogadorMensagemConversa()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
    BEGIN
        IF (NOT EXISTS (SELECT 1 FROM participa WHERE id_jogador = NEW.id_jogador AND id_conversa = NEW.id_conversa)) THEN
            RAISE EXCEPTION 'O jogador nao pertence a conversa';
        END IF;
        RETURN NEW;
    END;
$$;

CREATE OR REPLACE TRIGGER checkJogadorMensagemConversa
    BEFORE INSERT ON mensagem
    FOR EACH ROW
    EXECUTE PROCEDURE checkJogadorMensagemConversa();

COMMIT;