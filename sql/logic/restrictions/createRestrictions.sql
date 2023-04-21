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
    RETURNS TRIGGER
    LANGUAGE plpgsql
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
    EXECUTE FUNCTION checkJogadorPartidaRegiao();

------------------------------------------------------------------------------------------------------------------------
-- 2. The mensagem must be sent by a jogador of the conversa
DROP FUNCTION IF EXISTS checkJogadorMensagemConversa() CASCADE;

-- This function is called before inserting a new row in the mensagem table and checks if the jogador is from the same
-- conversa as the mensagem he is sending.
--
-- Example Usage:
-- INSERT INTO mensagem VALUES (1, 1, 'ola', '2019-12-12 12:12:12');
CREATE FUNCTION checkJogadorMensagemConversa()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    DECLARE
        jogador_id INT;
    BEGIN
        SELECT participa.id_jogador INTO jogador_id FROM participa WHERE(
            participa.id_jogador == NEW.id_jogador AND participa.id_conversa == NEW.id_conversa);
        IF(jogador_id == NULL) THEN
            RAISE EXCEPTION 'O jogador nao pertence a conversa';
        END IF;
    END;
$$;

DROP TRIGGER IF EXISTS checkJogadorMensagemConversa ON mensagem;

CREATE TRIGGER checkJogadorMensagemConversa BEFORE INSERT ON mensagem
    FOR EACH ROW
    EXECUTE PROCEDURE checkJogadorMensagemConversa();

------------------------------------------------------------------------------------------------------------------------
-- 3. Update the nr_partidas of the jogo_estatistica when a new partida is added or removed
DROP FUNCTION IF EXISTS update_nr_partidas() CASCADE;

CREATE OR REPLACE FUNCTION update_nr_partidas()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
    -- OLD AND NEW is the same id_jogo
    BEGIN
        IF (TG_OP = 'INSERT') THEN
            UPDATE jogo_estatistica
                SET nr_partidas = nr_partidas + 1
                WHERE id_jogo = NEW.id_jogo;
            END IF;
        IF (TG_OP = 'DELETE') THEN
            UPDATE jogo_estatistica
                SET nr_partidas = nr_partidas - 1
                WHERE id_jogo = OLD.id_jogo;
        end if;
        RETURN NEW;
    END;
$$;

DROP TRIGGER IF EXISTS update_nr_partidas_trigger ON partida;

CREATE TRIGGER update_nr_partidas_trigger
    AFTER INSERT OR DELETE ON partida
    FOR EACH ROW
    EXECUTE FUNCTION update_nr_partidas();

------------------------------------------------------------------------------------------------------------------------
-- 4. Update the nr_jogadores of the jogo_estatistica when a new jogador is added or removed
DROP FUNCTION IF EXISTS update_nr_jogadores() CASCADE;

CREATE OR REPLACE FUNCTION update_nr_jogadores()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
    BEGIN
        UPDATE jogo_estatistica
        SET nr_jogadores = (SELECT COUNT(nr_jogadores) FROM joga WHERE joga.id_jogo = NEW.id_jogo)
        WHERE id_jogo = NEW.id_jogo;
        RETURN NEW;
    END;
$$;

DROP TRIGGER IF EXISTS update_nr_jogadores_trigger ON jogador;

CREATE TRIGGER update_nr_jogadores_trigger
    AFTER INSERT OR DELETE ON joga
    FOR EACH ROW
    EXECUTE FUNCTION update_nr_jogadores();

------------------------------------------------------------------------------------------------------------------------
-- 5. Update the total_pontos of the jogo_estatistica when a new partida is added or removed
DROP FUNCTION IF EXISTS update_total_pontos() CASCADE;

CREATE OR REPLACE FUNCTION update_total_pontos()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
    DECLARE
        pontos_normal INT;
        pontos_multijogador INT;
    BEGIN
        SELECT SUM(pontuacao) INTO pontos_normal FROM partida_normal WHERE partida_normal.id_jogo = NEW.id_jogo;
        SELECT SUM(pontuacao) INTO pontos_multijogador FROM partida_multijogador WHERE partida_multijogador.id_jogo = NEW.id_jogo;
        -- not atomic because of multiplayer and single player games
        UPDATE jogo_estatistica
        SET total_pontos = pontos_normal + pontos_multijogador
        WHERE id_jogo = NEW.id_jogo;
        RETURN NEW;
    END;
$$;

DROP TRIGGER IF EXISTS update_total_pontos_trigger ON partida_multijogador;

CREATE TRIGGER update_total_pontos_trigger
    AFTER INSERT OR DELETE ON partida_multijogador
    FOR EACH ROW
    EXECUTE FUNCTION update_total_pontos();

DROP TRIGGER IF EXISTS update_total_pontos_trigger ON partida_normal;

CREATE TRIGGER update_total_pontos_trigger
    AFTER INSERT OR DELETE ON partida_normal
    FOR EACH ROW
    EXECUTE FUNCTION update_total_pontos();

------------------------------------------------------------------------------------------------------------------------
