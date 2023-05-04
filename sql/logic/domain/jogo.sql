------------------------------------------------------------------------------------------------------------------------
-- 3. Update the nr_partidas of the jogo_estatistica when a new partida is added or removed
DROP FUNCTION IF EXISTS updateNrPartidas() CASCADE;

CREATE OR REPLACE FUNCTION updateNrPartidas()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
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

DROP TRIGGER IF EXISTS updateNrPartidasTrigger ON partida;

CREATE TRIGGER updateNrPartidasTrigger
    AFTER INSERT OR DELETE ON partida
    FOR EACH ROW
    EXECUTE FUNCTION updateNrPartidas();

------------------------------------------------------------------------------------------------------------------------
-- 4. Update the nr_jogadores of the jogo_estatistica when a new jogador is added or removed
DROP FUNCTION IF EXISTS updateNrJogadores() CASCADE;

CREATE OR REPLACE FUNCTION updateNrJogadores()
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

DROP TRIGGER IF EXISTS updateNrJogadoresTrigger ON jogador;

CREATE TRIGGER updateNrJogadoresTrigger
    AFTER INSERT OR DELETE ON joga
    FOR EACH ROW
    EXECUTE FUNCTION updateNrJogadores();

------------------------------------------------------------------------------------------------------------------------
-- 5. Update the total_pontos of the jogo_estatistica when a new partida is added or removed
DROP FUNCTION IF EXISTS updatePontuacaoTotal() CASCADE;

CREATE OR REPLACE FUNCTION updatePontuacaoTotal()
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

DROP TRIGGER IF EXISTS updatePontuacaoTotalTrigger ON partida_multijogador;

CREATE TRIGGER updatePontuacaoTotalTrigger
    AFTER INSERT OR DELETE ON partida_multijogador
    FOR EACH ROW
    EXECUTE FUNCTION updatePontuacaoTotal();

DROP TRIGGER IF EXISTS updatePontuacaoTotalTrigger ON partida_normal;

CREATE TRIGGER update_total_pontos_trigger
    AFTER INSERT OR DELETE ON partida_normal
    FOR EACH ROW
    EXECUTE FUNCTION updatePontuacaoTotal();

------------------------------------------------------------------------------------------------------------------------
-- (h) Criar o procedimento armazenado associarCrachá que recebe como parâmetros o identificador de um jogador, a
-- referência de um jogo e o nome de um crachá desse jogo e atribui o crachá a esse jogador se ele reunir as condições
-- para o obter.
DROP PROCEDURE IF EXISTS associarCracha(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50));

-- This procedure associates a crachá with a player if they meet the conditions to obtain it.
--
-- Example usage:
-- CALL associarCracha(1, '1', 'Crachá de Ouro');
CREATE PROCEDURE associarCracha(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nome_cracha VARCHAR(50);
        total_pontos INT;
    BEGIN
        SELECT nome INTO nome_cracha FROM cracha WHERE cracha.nome == cracha_nome;
        SELECT total_pontos FROM PontosJogoPorJogador(jogo_id) AS pontos_jogo WHERE pontos_jogo.id_jogador = jogador_id INTO total_pontos;
        IF (total_pontos >= (SELECT limite_pontos FROM cracha WHERE cracha.nome == cracha_nome)) THEN
            INSERT INTO ganha VALUES (jogador_id, cracha_nome, jogo_id);
        END IF;
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (m) Criar os mecanismos necessários para que, de forma automática, quando uma partida termina, se proceda à
-- atribuição de crachás do jogo a que ela pertence.
DROP FUNCTION IF EXISTS atribuirCrachas() CASCADE;

-- This function is called when a match ends and it assigns badges to the players who have played the match.
--
-- Example usage:
--
CREATE FUNCTION atribuirCrachas()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    DECLARE
        jogo_id      ALPHANUMERIC;
        jogador_id   INT;
        nome_cracha  VARCHAR(50);
        total_pontos INT;
    BEGIN
        SELECT jogo_id INTO jogo_id FROM partida WHERE partida.nr == NEW.nr;
        SELECT id_jogador INTO jogador_id FROM joga WHERE joga.nr_partida == NEW.nr;
        SELECT nome INTO nome_cracha FROM cracha WHERE cracha.id_jogo == jogo_id;
        SELECT pontuacao from partida_normal WHERE partida_normal.nr_partida == NEW.nr INTO total_pontos;
        IF (total_pontos == NULL) THEN
            SELECT pontuacao from partida_multijogador WHERE partida_multijogador.nr_partida == NEW.nr INTO total_pontos;
        END IF;
        IF (total_pontos >= (SELECT limite_pontos FROM cracha WHERE cracha.nome == nome_cracha)) THEN
            INSERT INTO ganha VALUES (jogador_id, nome_cracha, id_jogo);
        END IF;
    END;
$$;

DROP TRIGGER IF EXISTS atribuirCrachas ON partida;

-- This trigger is called when a match ends and it assigns badges to the players who have played the match if they
-- have obtained enough points.
CREATE TRIGGER atribuirCrachas
    AFTER INSERT ON partida
    FOR EACH ROW
    EXECUTE PROCEDURE atribuirCrachas();
