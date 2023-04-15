-- Model Restrictions
-- 1. The partida must be played by jogadores of the same regiao
DROP FUNCTION IF EXISTS checkJogadorPartidaRegiao() CASCADE;

-- This function is called before inserting a new row in the joga table and checks if the jogador is from the same
-- regiao as the partida he is playing in.
--
-- Example Usage:
-- INSERT INTO partida VALUES ( now(), null, 1000000000, 'Portugal');
-- INSERT INTO joga (id_jogador, nr_partida) VALUES (1, 1);
CREATE FUNCTION checkJogadorPartidaRegiao()
    RETURNS trigger LANGUAGE plpgsql
AS
$$
    DECLARE
        regiao_nome VARCHAR(50);
    BEGIN
        SELECT partida.nome_regiao INTO regiao_nome FROM partida WHERE partida.nr == NEW.nr_partida;
        IF (regiao_nome != (SELECT jogador.nome_regiao FROM jogador WHERE jogador.id == NEW.id_jogador)) THEN
            RAISE EXCEPTION 'O jogador nao pertence a regiao da partida';
        END IF;
    END;
$$;

DROP TRIGGER IF EXISTS checkJogadorPartidaRegiao ON partida;

-- This trigger calls the function checkJogadorPartidaRegiao() before inserting a new row in the joga table
CREATE TRIGGER checkJogadorPartidaRegiao BEFORE INSERT ON joga
    FOR EACH ROW
    EXECUTE PROCEDURE checkJogadorPartidaRegiao();

------------------------------------------------------------------------------------------------------------------------
-- 2. The mensagem must be sent by a jogador of the conversa
DROP FUNCTION IF EXISTS checkJogadorMensagemConversa() CASCADE;

-- This function is called before inserting a new row in the mensagem table and checks if the jogador is from the same
-- conversa as the mensagem he is sending.
--
-- Example Usage:
-- INSERT INTO mensagem VALUES (1, 1, 'ola', '2019-12-12 12:12:12');
CREATE FUNCTION checkJogadorMensagemConversa()
    RETURNS trigger LANGUAGE plpgsql
AS
$$
    DECLARE
        jogador_id INT;
    BEGIN
        SELECT participa.id_jogador INTO jogador_id FROM participa WHERE (
            participa.id_jogador == NEW.id_jogador AND participa.id_conversa == NEW.id_conversa);
        IF (jogador_id == NULL) THEN
            RAISE EXCEPTION 'O jogador nao pertence a conversa';
        END IF;
    END;
$$;

DROP TRIGGER IF EXISTS checkJogadorMensagemConversa ON mensagem;

CREATE TRIGGER checkJogadorMensagemConversa BEFORE INSERT ON mensagem
    FOR EACH ROW
    EXECUTE PROCEDURE checkJogadorMensagemConversa();
