--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username e regiao, desativar e banir o
-- jogador;

-- This procedure creates a new player given their region name, username, and email. It performs the following tasks:
--
-- Checks if the given region name exists in the regiao table. If not, it inserts a new row with the given region name.
-- Inserts a new row into the jogador table with the given username, email, and region name.
-- Example usage:
-- CALL create_jogador('Regiao A', 'user123', 'user123@example.com');
CREATE OR REPLACE PROCEDURE createJogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL)
    LANGUAGE plpgsql
AS
$$
    BEGIN
        -- Checks
        IF (regiao_nome NOT IN (SELECT regiao.nome FROM regiao)) THEN
            RAISE EXCEPTION 'No Region with name (%)', regiao_nome
                    USING HINT = 'Check the inserted name or insert a new region';
        END IF;
        IF (new_username IN (SELECT jogador.username FROM jogador)) THEN
            RAISE EXCEPTION 'Player already exists with username (%)', new_username
                    USING HINT = 'Check the inserted username';
        END IF;
        -- expected
        INSERT INTO jogador(username, email, nome_regiao) VALUES (new_username, new_email, regiao_nome);
    END;
$$;

-- This procedure updates the state of a player given their player ID and new state. It performs the following tasks:
--
-- Checks if the given player ID exists in the jogador table. If not, it raises a notice indicating that the player was
-- not found.
-- Checks if the player already has the new state. If so, it raises a notice indicating that the player already has this
-- state.
-- Updates the estado field of the jogador table with the new state for the given player ID.
-- Example usage:
-- CALL update_estado_jogador(1, 'ativo');
CREATE OR REPLACE PROCEDURE updateEstadoJogador(id_jogador INT, new_estado VARCHAR(10))
    LANGUAGE plpgsql
AS
$$
    BEGIN
        -- Checks
        IF (id_jogador NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'No Player with id (%)', id_jogador
                    USING HINT = 'Check the inserted id';
        END IF;
        IF ((SELECT jogador.estado FROM jogador WHERE jogador.id == id_jogador) == new_estado) THEN
            RAISE EXCEPTION 'Player already has state (%)', new_estado
                    USING HINT = 'Check the inserted state';
        END IF;
        -- expected
        UPDATE jogador SET estado = new_estado WHERE jogador.id = id_jogador;
    END ;
$$;

-- This procedure creates a new player given their region name, username, and email. It is an abstraction with
-- transaction control and it performs the following tasks:
--
-- Sets the transaction isolation level to SERIALIZABLE.
-- Calls the create_jogador procedure.
-- If the create_jogador procedure raises a unique_violation exception, it raises a notice indicating that the player
-- already exists and rolls back the transaction.
-- Example usage:
-- CALL createJogadorTransaction('Regiao A', 'user123', 'user123@gmail.com');
CREATE OR REPLACE PROCEDURE createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL)
    LANGUAGE plpgsql
AS
$$
    Declare t1 text;
            t2 text;
            t3 text;
            t4 text;
            t5 text;
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; -- default is READ COMMITTED
        call createJogador(regiao_nome, new_username, new_email);
    EXCEPTION
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT,
                                    t2 = RETURNED_SQLSTATE,
                                    t3 = PG_EXCEPTION_DETAIL,
                                    t4 = PG_EXCEPTION_HINT,
                                    t5 = PG_EXCEPTION_CONTEXT;
            RAISE NOTICE 'MESSAGE_TEXT: %', t1;
            RAISE NOTICE 'SQLSTATE: %', t2;
            RAISE NOTICE 'DETAIL: %', t3;
            RAISE NOTICE 'HINT: %', t4;
            RAISE NOTICE 'CONTEXT: %', t5;
            ROLLBACK;
END;
$$;

-- This procedure updates the state of a player given their player ID and new state. It is an abstraction with
-- transaction control and it performs the following tasks:
--
-- Sets the transaction isolation level to SERIALIZABLE.
-- Calls the update_estado_jogador procedure.
-- If the update_estado_jogador procedure raises a unique_violation exception, it raises a notice indicating that the
-- player already has this state and rolls back the transaction.
-- Example usage:
-- CALL updateEstadoJogadorTransaction(1, 'ativo');
CREATE OR REPLACE PROCEDURE updateEstadoJogadorTransaction(id_jogador INT, new_estado VARCHAR(10))
    LANGUAGE plpgsql
AS
$$
    Declare t1 text;
            t2 text;
            t3 text;
            t4 text;
            t5 text;
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; -- default is READ COMMITTED
        call updateEstadoJogador(id_jogador, new_estado);
    EXCEPTION
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT,
                                    t2 = RETURNED_SQLSTATE,
                                    t3 = PG_EXCEPTION_DETAIL,
                                    t4 = PG_EXCEPTION_HINT,
                                    t5 = PG_EXCEPTION_CONTEXT;
            RAISE NOTICE 'MESSAGE_TEXT: %', t1;
            RAISE NOTICE 'SQLSTATE: %', t2;
            RAISE NOTICE 'DETAIL: %', t3;
            RAISE NOTICE 'HINT: %', t4;
            RAISE NOTICE 'CONTEXT: %', t5;
            ROLLBACK;
END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.

-- This function calculates and returns the total points obtained by a player given their player ID. It performs the
-- following tasks:
--
-- Checks if the given player ID exists in the jogador table. If not, it raises a notice indicating that the player was
-- not found.
-- Checks if the player is banned or inactive. If so, it raises a notice indicating that the player is banned or
-- inactive.
-- Checks if the player has played any games. If not, it raises a notice indicating that the player has not played any
-- games.
-- Retrieves the points from the partida_normal and partida_multijogador tables for the player ID.
-- Returns the sum of the points from both tables as the total points for the player.
-- Example usage:
-- SELECT totalPontosJogador(1);
CREATE OR REPLACE FUNCTION totalPontosJogador(jogador_id INT)
    RETURNS INT
    LANGUAGE plpgsql
AS
$$
    DECLARE
        partidaNormal       INT;
        partidaMultiJogador INT;
    BEGIN
        -- Checks
        IF(jogador_id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'Jogador with id (%) not found', jogador_id
                    USING HINT = 'Check the inserted id';
        END IF;
        IF ((SELECT jogador.estado FROM jogador WHERE jogador.id == jogador_id) == 'banido' | 'desativado') THEN
            RAISE NOTICE 'Jogador with id (%) is banned or inactive', jogador_id
                    USING HINT = 'Check the inserted id';
        END IF;
        IF ((SELECT joga.id_jogador FROM joga) <> jogador_id) THEN
            RAISE NOTICE 'Jogador with id (%) has not played any games', jogador_id
                    USING HINT = 'Check the inserted id or start a new game';
        END IF;
        -- expected
        -- points from partida normal
        SELECT pontuacao FROM partida_normal WHERE (nr_partida == (
        SELECT partida.nr FROM partida WHERE (
        SELECT joga.nr_partida FROM joga WHERE (joga.id_jogador == jogador_id)))) INTO partidaNormal;

        -- points from partida multijogador
        SELECT pontuacao FROM partida_multijogador WHERE (nr_partida == (
        SELECT partida.nr FROM partida WHERE (
        SELECT joga.nr_partida FROM joga WHERE (joga.id_jogador == jogador_id)))) INTO partidaMultiJogador;

        RETURN partidaNormal + partidaMultiJogador;
    end;
$$;

-- This procedure calculates and returns the total points obtained by a player given their player ID. It is an
-- abstraction with transaction control and it performs the following tasks:
--
-- Sets the transaction isolation level to READ COMMITED because the functionality is read only.
-- Calls the totalPontosJogador function.
-- If the totalPontosJogador function raises a unique_violation exception, it raises a notice indicating that the
-- player is banned or inactive and rolls back the transaction.
-- Example usage:
-- CALL totalPontosJogadorTransaction(1);
CREATE OR REPLACE PROCEDURE totalPontosJogadorTransaction(jogador_id INT)
    LANGUAGE plpgsql
AS
$$
    Declare t1 text;
            t2 text;
            t3 text;
            t4 text;
            t5 text;
    BEGIN
        SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
        SELECT totalPontosJogador(jogador_id);
    EXCEPTION
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT,
                                    t2 = RETURNED_SQLSTATE,
                                    t3 = PG_EXCEPTION_DETAIL,
                                    t4 = PG_EXCEPTION_HINT,
                                    t5 = PG_EXCEPTION_CONTEXT;
            RAISE NOTICE 'MESSAGE_TEXT: %', t1;
            RAISE NOTICE 'SQLSTATE: %', t2;
            RAISE NOTICE 'DETAIL: %', t3;
            RAISE NOTICE 'HINT: %', t4;
            RAISE NOTICE 'CONTEXT: %', t5;
            ROLLBACK;
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (f) Criar a função totalJogosJogador que recebe como parâmetro o identificador de um jogador e devolve o número total
-- de jogos diferentes nos quais o jogador participou.

-- This function calculates and returns the total number of games a player has participated in given their player ID.
-- It performs the following tasks:
--
-- Checks if the given player ID exists in the jogador table. If not, it raises a notice indicating that the player was
-- not found.
-- Checks if the player is banned or inactive. If so, it raises a notice indicating that the player is banned or
-- inactive.
-- Checks if the player has played any games. If not, it raises a notice indicating that the player has not played any
-- games.
-- Retrieves the number of games from the partida_normal and partida_multijogador tables for the player ID.
-- Returns the sum of the number of games from both tables as the total number of games for the player.
-- Example usage:
-- SELECT totalJogosJogador(1);
CREATE OR REPLACE FUNCTION totalJogosJogador(jogador_id INT)
    RETURNS INT
    LANGUAGE plpgsql
AS
$$
    DECLARE
        jogosNormal       INT;
        jogosMultiJogador INT;
    BEGIN
        -- Checks
        IF(jogador_id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'Jogador with id (%) not found', jogador_id
                    USING HINT = 'Check the inserted id';
        END IF;
        IF ((SELECT jogador.estado FROM jogador WHERE jogador.id == jogador_id) == 'banido' | 'desativado') THEN
            RAISE NOTICE 'Jogador with id (%) is banned or inactive', jogador_id
                    USING HINT = 'Check the inserted id';
        END IF;
        IF ((SELECT joga.id_jogador FROM joga) <> jogador_id) THEN
            RAISE NOTICE 'Jogador with id (%) has not played any games', jogador_id
                    USING HINT = 'Check the inserted id or start a new game';
        END IF;
        -- expected
        -- counts the number of partidas normais
        SELECT count() INTO jogosNormal FROM partida_normal WHERE partida_normal.nr_partida == (
        SELECT partida.nr FROM partida WHERE (partida.nr == (
        SELECT joga.nr_partida FROM joga WHERE (joga.id_jogador == jogador_id))));

        -- counts the number of partidas multijogador
        SELECT count() INTO jogosMultiJogador FROM partida_multijogador WHERE partida_multijogador.nr_partida == (
        SELECT partida.nr FROM partida WHERE (partida.nr == (
        SELECT joga.nr_partida FROM joga WHERE (joga.id_jogador == jogador_id))));

        RETURN jogosNormal + jogosMultiJogador;
    END;
$$;

-- This procedure calculates and returns the total number of games a player has participated in given their player ID.
-- It is an abstraction with transaction control and it performs the following tasks:
--
-- Sets the transaction isolation level to READ COMMITED because the functionality is read only.
-- Calls the totalJogosJogador function.
-- If the totalJogosJogador function raises a unique_violation exception, it raises a notice indicating that the
-- player is banned or inactive and rolls back the transaction.
--
-- Example usage:
-- CALL totalJogosJogadorTransaction(1);
CREATE OR REPLACE PROCEDURE totalJogosJogadorTransaction(jogador_id INT)
    LANGUAGE plpgsql
AS
$$
    Declare t1 text;
            t2 text;
            t3 text;
            t4 text;
            t5 text;
    BEGIN
        SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
        call totalJogosJogador(jogador_id);
    EXCEPTION
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT,
                                    t2 = RETURNED_SQLSTATE,
                                    t3 = PG_EXCEPTION_DETAIL,
                                    t4 = PG_EXCEPTION_HINT,
                                    t5 = PG_EXCEPTION_CONTEXT;
            RAISE NOTICE 'MESSAGE_TEXT: %', t1;
            RAISE NOTICE 'SQLSTATE: %', t2;
            RAISE NOTICE 'DETAIL: %', t3;
            RAISE NOTICE 'HINT: %', t4;
            RAISE NOTICE 'CONTEXT: %', t5;
            ROLLBACK;
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo e devolve uma tabela com
-- duas colunas (identificador de jogador, total de pontos) em que cada linha contém o identificador de um jogador e o
-- total de pontos que esse jogador teve nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo.

-- This function calculates and returns the total points obtained by each player in a game given the game ID. It
-- performs the following tasks:
--
-- Example usage:
-- SELECT * FROM PontosJogoPorJogador('1');
CREATE OR REPLACE FUNCTION PontosJogoPorJogador(jogo_id ALPHANUMERIC)
    RETURNS TABLE (id_jogador INT, total_pontos INT)
    LANGUAGE plpgsql
AS
$$
    BEGIN
        IF (jogo_id NOT IN (SELECT jogo.id FROM jogo)) THEN
            RAISE EXCEPTION 'Jogo with id (%) not found', jogo_id
                    USING HINT = 'Check the inserted id';
        END IF;
        RETURN QUERY SELECT joga.id_jogador, totalPontosJogador(joga.id_jogador) FROM joga WHERE joga.id_jogador IN (
        SELECT joga.id_jogador FROM joga WHERE joga.nr_partida IN (
        SELECT partida.nr FROM partida WHERE partida.id_jogo == jogo_id));
    END;
$$;

-- This procedure calculates and returns the total points obtained by each player in a game given the game ID. It
-- performs the following tasks:
--
-- Sets the transaction isolation level to READ COMMITED because the functionality is read only.
-- Calls the PontosJogoPorJogador function.
-- If the PontosJogoPorJogador function raises a unique_violation exception, it raises a notice indicating that the
-- game does not exist and rolls back the transaction.
--
-- Example usage:
-- CALL PontosJogoPorJogadorTransaction('1');
CREATE OR REPLACE PROCEDURE PontosJogoPorJogadorTransaction(jogo_id ALPHANUMERIC)
    LANGUAGE plpgsql
AS
$$
    Declare t1 text;
            t2 text;
            t3 text;
            t4 text;
            t5 text;
    BEGIN
        SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
        SELECT PontosJogoPorJogador(jogo_id);
    EXCEPTION
        WHEN no_data_found THEN
            RAISE NOTICE 'No players have played game with id (%)', jogo_id
                USING HINT = 'Start a new game';
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT,
                                    t2 = RETURNED_SQLSTATE,
                                    t3 = PG_EXCEPTION_DETAIL,
                                    t4 = PG_EXCEPTION_HINT,
                                    t5 = PG_EXCEPTION_CONTEXT;
            RAISE NOTICE 'MESSAGE_TEXT: %', t1;
            RAISE NOTICE 'SQLSTATE: %', t2;
            RAISE NOTICE 'DETAIL: %', t3;
            RAISE NOTICE 'HINT: %', t4;
            RAISE NOTICE 'CONTEXT: %', t5;
            ROLLBACK;
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (l) Criar a vista jogadorTotalInfo que permita aceder à informação sobre identificador, estado, email, username,
-- número total de jogos em que participou, número total de partidas em que participou e número total de pontos que já
-- obteve de todos os jogadores cujo estado seja diferente de “Banido”. Deve implementar na vista os cálculos sem aceder
-- às tabelas de estatísticas.

-- This function returns the total number of matches a player has played given the player ID.
--
-- Example usage:
-- SELECT totalPartidasJogador(1);
CREATE OR REPLACE FUNCTION totalPartidasJogador(jogador_id INT)
    RETURNS INT
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nr_partidas INT;
    BEGIN
        IF (jogador_id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'Player with id (%) not found', jogador_id
                    USING HINT = 'Check the inserted id';
        END IF;
        IF (SELECT jogador.estado FROM jogador WHERE jogador.id == jogador_id) == 'Banido' THEN
            RAISE EXCEPTION 'Player with id (%) is banned', jogador_id
                    USING HINT = 'Check the inserted id';
        END IF;
        IF (jogador_id <> (SELECT joga.id_jogador FROM joga)) THEN
            RAISE NOTICE 'Player with id (%) has not played any matches', jogador_id
                    USING HINT = 'Check the inserted id';
            RETURN 0;
        END IF;
        SELECT COUNT(nr_partida) INTO nr_partidas FROM joga WHERE joga.id_jogador == jogador_id;
        RETURN nr_partidas;
    END;
$$;

-- This procedure returns the total number of matches a player has played given the player ID. It performs the
-- following tasks:
--
-- Sets the transaction isolation level to READ COMMITED because the functionality is read only.
-- Calls the totalPartidasJogador function.
-- If the totalPartidasJogador function raises a unique_violation exception, it raises a notice indicating that the
-- player does not exist and rolls back the transaction.
--
-- Example usage:
-- CALL totalPartidasJogadorTransaction(1);
CREATE OR REPLACE PROCEDURE totalPartidasJogadorTransaction(jogador_id INT)
    LANGUAGE plpgsql
AS
$$
    Declare t1 text;
            t2 text;
            t3 text;
            t4 text;
            t5 text;
    BEGIN
        SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
        SELECT totalPartidasJogador(jogador_id);
    EXCEPTION
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT,
                                    t2 = RETURNED_SQLSTATE,
                                    t3 = PG_EXCEPTION_DETAIL,
                                    t4 = PG_EXCEPTION_HINT,
                                    t5 = PG_EXCEPTION_CONTEXT;
            RAISE NOTICE 'MESSAGE_TEXT: %', t1;
            RAISE NOTICE 'SQLSTATE: %', t2;
            RAISE NOTICE 'DETAIL: %', t3;
            RAISE NOTICE 'HINT: %', t4;
            RAISE NOTICE 'CONTEXT: %', t5;
            ROLLBACK;
    END;
$$;

-- This view returns information about the ID, state, email, username, total number of games the player has played,
-- total number of matches the player has played and total number of points the player has obtained from all players
-- whose state is different from "Banido". It implements the calculations without accessing the statistics tables.
--
-- Example usage:
-- SELECT * FROM jogadorTotalInfo;
CREATE OR REPLACE VIEW jogadorTotalInfo AS
    SELECT  jogador.id,
            jogador.estado,
            jogador.email,
            jogador.username,
            totalJogosJogador(jogador.id) AS total_jogos,
            totalPartidasJogador(jogador.id) AS total_partidas,
            totalPontosJogador(jogador.id) AS total_pontos
    FROM jogador WHERE jogador.estado != 'Banido';

------------------------------------------------------------------------------------------------------------------------
-- (n) Criar os mecanismos necessários para que a execução da instrução DELETE sobre a vista jogadorTotalInfo permita
-- colocar os jogadores envolvidos no estado “Banido”.

-- This function is called when a player is deleted from the view jogadorTotalInfo and it puts the player in the
-- "Banido" state.
--
-- Example usage:
-- DELETE FROM jogadorTotalInfo WHERE jogadorTotalInfo.username == 'jogador1';
CREATE OR REPLACE FUNCTION banirJogador()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    BEGIN
        IF (OLD.id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'Player with id (%) not found', OLD.id
                    USING HINT = 'Check Player ID';
        END IF;
        IF (OLD.estado == 'Banido') THEN
            RAISE EXCEPTION 'Player with id (%) is already banned', OLD.id
                    USING HINT = 'Check Player ID';
        END IF;
        UPDATE jogador SET estado = 'Banido' WHERE jogador.id == OLD.id;
    END;
$$;

-- This trigger is called when a player is deleted from the view jogadorTotalInfo and it puts the player in the
-- "Banido" state.
CREATE OR REPLACE TRIGGER banirJogador
    INSTEAD OF DELETE ON jogadorTotalInfo
    FOR EACH ROW
    EXECUTE FUNCTION banirJogador();

------------------------------------------------------------------------------------------------------------------------
-- Model functionalities
-- 1. Implementing the automatic update of the total number of games a player has played when a player's statistics are.

-- This function is called when a player's statistics are updated and it updates the total number of games the player
-- has played.
--
-- Example usage:
-- INSERT INTO joga VALUES (1, 1);
CREATE OR REPLACE FUNCTION updateTotalPartidasJogador()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    BEGIN
        UPDATE jogador_estatistica SET nr_jogos = totalPartidasJogador(NEW.id_jogador) WHERE jogador_estatistica.id_jogador == NEW.id_jogador;
    END;
$$;

-- This trigger is called when a player's statistics are updated and it updates the total number of games the player
CREATE OR REPLACE TRIGGER update_total_partidas_jogador
AFTER INSERT OR DELETE ON joga
EXECUTE FUNCTION updateTotalPartidasJogador();

-- This function is called when a player's statistics are updated and it updates the total number of points the player
-- has obtained.
CREATE OR REPLACE FUNCTION updateTotalPontosJogador()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    DECLARE jogador_id INT;
    BEGIN
        SELECT joga.id_jogador INTO id_jogador FROM joga WHERE joga.nr_partida == NEW.nr_partida;
        UPDATE jogador_estatistica SET total_pontos = totalPontosJogador(jogador_id) WHERE jogador_estatistica.id_jogador == jogador_id;
    END;
$$;

-- This trigger is called when a player's statistics are updated and it updates the total number of points the player
-- has obtained.
CREATE OR REPLACE TRIGGER update_total_pontos_jogador
AFTER INSERT OR DELETE ON partida_multijogador
EXECUTE FUNCTION updateTotalPontosJogador();

-- This trigger is called when a player's statistics are updated and it updates the total number of points the player
-- has obtained.
CREATE OR REPLACE TRIGGER update_total_pontos_jogador
AFTER INSERT OR DELETE ON partida_normal
EXECUTE FUNCTION updateTotalPontosJogador();

-- This function is called when a player's statistics are updated and it updates the total number of games the player
-- has played.
CREATE OR REPLACE FUNCTION updateTotalJogosJogador()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    BEGIN
        UPDATE jogador_estatistica SET nr_jogos = totalJogosJogador(NEW.id_jogador) WHERE jogador_estatistica.id_jogador == NEW.id_jogador;
    END;
$$;

-- This trigger is called when a player's statistics are updated and it updates the total number of games the player
-- has played.
CREATE OR REPLACE TRIGGER update_total_jogos_jogador
AFTER INSERT OR DELETE ON partida
EXECUTE FUNCTION updateTotalJogosJogador();