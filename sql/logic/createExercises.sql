--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username, desativar e banir o jogador;
DROP PROCEDURE IF EXISTS create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);

DROP PROCEDURE IF EXISTS createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);

-- This procedure creates a new player given their region name, username, and email. It is an abstraction with
-- transaction control and it performs the following tasks:
--
-- Sets the transaction isolation level to SERIALIZABLE.
-- Calls the create_jogador procedure.
-- If the create_jogador procedure raises a unique_violation exception, it raises a notice indicating that the player
-- already exists and rolls back the transaction.
-- Example usage:
-- CALL createJogadorTransaction('Regiao A', 'user123', 'user123@gmail.com');
CREATE PROCEDURE createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL)
    LANGUAGE plpgsql
AS
$$
    Declare
        t1 text;
        t2 text;
        t3 text;
        t4 text;
        t5 text;
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE; -- default is READ COMMITTED
        call create_jogador(regiao_nome, new_username, new_email);
    EXCEPTION
        WHEN unique_violation THEN
            RAISE NOTICE 'Player % already exists', new_username;
            ROLLBACK;
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
-- This procedure creates a new player given their region name, username, and email. It performs the following tasks:
--
-- Checks if the given region name exists in the regiao table. If not, it inserts a new row with the given region name.
-- Inserts a new row into the jogador table with the given username, email, and region name.
-- Example usage:
-- CALL create_jogador('Regiao A', 'user123', 'user123@example.com');
CREATE PROCEDURE create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL)
    LANGUAGE plpgsql
AS
$$
    BEGIN
        -- Checks
        IF (regiao_nome NOT IN (SELECT regiao.nome FROM regiao)) THEN
            INSERT INTO regiao VALUES (regiao_nome);
        END IF;
        -- expected
        INSERT INTO jogador(username, email, nome_regiao) VALUES (new_username, new_email, regiao_nome);
    END;
$$;

DROP PROCEDURE IF EXISTS update_estado_jogador(id_jogador INT, new_estado VARCHAR(10));

-- This procedure updates the state of a player given their player ID and new state. It performs the following tasks:
--
-- Checks if the given player ID exists in the jogador table. If not, it raises a notice indicating that the player was not found.
-- Checks if the player already has the new state. If so, it raises a notice indicating that the player already has this state.
-- Updates the estado field of the jogador table with the new state for the given player ID.
-- Example usage:
-- CALL update_estado_jogador(1, 'ativo');
CREATE PROCEDURE update_estado_jogador(id_jogador INT, new_estado VARCHAR(10))
    LANGUAGE plpgsql
AS
$$
    BEGIN
        -- Checks
        IF (id_jogador NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE NOTICE 'jogador not found';
        END IF;
        IF ((SELECT jogador.estado FROM jogador WHERE jogador.id == id_jogador) == new_estado) THEN
            RAISE NOTICE 'jogador already has this estado';
        END IF;
        -- expected
        UPDATE jogador SET estado = new_estado WHERE jogador.id = id_jogador;
    END ;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.
DROP FUNCTION IF EXISTS totalPontosJogador(jogador_id INT) CASCADE;

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
CREATE FUNCTION totalPontosJogador(jogador_id INT)
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
            RAISE NOTICE 'jogador not found';
        END IF;
        IF ((SELECT jogador.estado FROM jogador WHERE jogador.id == jogador_id) == 'banido' | 'desativado') THEN
            RAISE NOTICE 'jogador is banned or inactive';
        END IF;
        IF ((SELECT joga.id_jogador FROM joga) <> jogador_id) THEN
            RAISE NOTICE 'jogador has not played any games';
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

------------------------------------------------------------------------------------------------------------------------
-- (f) Criar a função totalJogosJogador que recebe como parâmetro o identificador de um jogador e devolve o número total
-- de jogos diferentes nos quais o jogador participou.
DROP FUNCTION IF EXISTS totalJogosJogador(jogador_id INT);

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
CREATE FUNCTION totalJogosJogador(jogador_id INT)
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
            RAISE NOTICE 'jogador not found';
        END IF;
        IF ((SELECT jogador.estado FROM jogador WHERE jogador.id == jogador_id) = 'banido' or 'desativado') THEN
            RAISE NOTICE 'jogador is banned or inactive';
        END IF;
        IF ((SELECT joga.id_jogador FROM joga) <> jogador_id) THEN
            RAISE NOTICE 'jogador has not played any games';
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

------------------------------------------------------------------------------------------------------------------------
-- (g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo e devolve uma tabela com
-- duas colunas (identificador de jogador, total de pontos) em que cada linha contém o identificador de um jogador e o
-- total de pontos que esse jogador teve nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo.
DROP FUNCTION IF EXISTS PontosJogoPorJogador(jogo_id ALPHANUMERIC);

-- This function calculates and returns the total points obtained by each player in a game given the game ID. It
-- performs the following tasks:
--
-- Example usage:
-- SELECT * FROM PontosJogoPorJogador('1');
CREATE FUNCTION PontosJogoPorJogador(jogo_id ALPHANUMERIC)
    RETURNS TABLE (id_jogador INT, total_pontos INT) LANGUAGE plpgsql
AS
$$
    BEGIN
        RETURN QUERY SELECT joga.id_jogador, totalPontosJogador(joga.id_jogador) FROM joga WHERE joga.id_jogador IN (
        SELECT joga.id_jogador FROM joga WHERE joga.nr_partida IN (
        SELECT partida.nr FROM partida WHERE partida.id_jogo == jogo_id));
    END;
$$;

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
-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.
DROP PROCEDURE IF EXISTS iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT);

-- This procedure starts a chat given the player ID and the name of the chat. The player is automatically associated
-- with the chat and a message is created informing that the player created the chat. The procedure returns the chat
-- ID in an output parameter.
--
-- Example usage:
-- CALL iniciarConversa(1, 'Chat 1', conversa_id);
CREATE PROCEDURE iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        default_conversa_nr_ordem INT := 1;
        nome_conversa VARCHAR(50);
    BEGIN
        SELECT nome INTO nome_conversa FROM conversa WHERE conversa.nome == nome_conversa;
        IF (nome_conversa == NULL) THEN
            INSERT INTO conversa VALUES (nome_conversa);
            SELECT id INTO conversa_id FROM conversa WHERE conversa.nome == nome_conversa;
            INSERT INTO participa VALUES (jogador_id, conversa_id);
            INSERT INTO mensagem VALUES (default_conversa_nr_ordem, 'O jogador criou a conversa', now(),
                                         jogador_id, conversa_id);
        END IF;
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.
DROP PROCEDURE IF EXISTS juntarConversa(jogador_id INT, conversa_id INT);

-- This procedure joins a player to a chat given the player ID and the chat ID. A message is created informing that
-- the player joined the chat.
--
-- Example usage:
-- CALL juntarConversa(1, 1);
CREATE PROCEDURE juntarConversa(jogador_id INT, conversa_id INT)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nr INT;
    BEGIN
        SELECT COUNT(mensagem.nr_ordem) INTO nr FROM mensagem WHERE id_jogador == jogador_id AND mensagem.id_conversa == conversa_id;
        INSERT INTO participa VALUES (jogador_id, conversa_id);
        INSERT INTO mensagem VALUES (nr, 'O jogador entrou na conversa', now(), jogador_id, conversa_id);
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.
DROP PROCEDURE IF EXISTS enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50));

-- This procedure sends a message to a chat given the player ID, the chat ID and the message text. The message is
-- associated with the player and the chat.
--
-- Example usage:
-- CALL enviarMensagem(1, 1, 'Mensagem 1');
CREATE PROCEDURE enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nr INT;
    BEGIN
        SELECT COUNT(mensagem.nr_ordem) INTO nr FROM mensagem WHERE id_jogador == jogador_id AND mensagem.id_conversa == conversa_id;
        INSERT INTO mensagem VALUES (nr, mensagem_texto, now(), jogador_id, conversa_id);
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (l) Criar a vista jogadorTotalInfo que permita aceder à informação sobre identificador, estado, email, username,
-- número total de jogos em que participou, número total de partidas em que participou e número total de pontos que já
-- obteve de todos os jogadores cujo estado seja diferente de “Banido”. Deve implementar na vista os cálculos sem aceder
-- às tabelas de estatísticas.
DROP FUNCTION IF EXISTS totalPartidasJogador(jogador_id INT);

-- This function returns the total number of matches a player has played given the player ID.
--
-- Example usage:
-- SELECT totalPartidasJogador(1);
CREATE FUNCTION totalPartidasJogador(jogador_id INT)
    RETURNS INT
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nr_partidas INT;
    BEGIN
        SELECT COUNT(nr_partida) INTO nr_partidas FROM joga WHERE joga.id_jogador == jogador_id;
        RETURN nr_partidas;
    END;
$$;

DROP VIEW IF EXISTS jogadorTotalInfo;

-- This view returns information about the ID, state, email, username, total number of games the player has played,
-- total number of matches the player has played and total number of points the player has obtained from all players
-- whose state is different from "Banido". It implements the calculations without accessing the statistics tables.
--
-- Example usage:
-- SELECT * FROM jogadorTotalInfo;
CREATE VIEW jogadorTotalInfo AS
    SELECT  jogador.id,
            jogador.estado,
            jogador.email,
            jogador.username,
            totalJogosJogador(jogador.id) AS total_jogos,
            totalPartidasJogador(jogador.id) AS total_partidas,
            totalPontosJogador(jogador.id) AS total_pontos
    FROM jogador WHERE jogador.estado != 'Banido';

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

------------------------------------------------------------------------------------------------------------------------
-- (n) Criar os mecanismos necessários para que a execução da instrução DELETE sobre a vista jogadorTotalInfo permita
-- colocar os jogadores envolvidos no estado “Banido”.
DROP FUNCTION IF EXISTS banirJogador();

-- This function is called when a player is deleted from the view jogadorTotalInfo and it puts the player in the
-- "Banido" state.
--
-- Example usage:
--
CREATE FUNCTION banirJogador()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    DECLARE
        jogador_id INT;
    BEGIN
        SELECT jogador.id INTO jogador_id FROM jogador WHERE jogador.username == OLD.username;
        UPDATE jogador SET estado = 'Banido' WHERE jogador.id == jogador_id;
    END;
$$;

DROP TRIGGER IF EXISTS banirJogador ON jogadorTotalInfo;

-- This trigger is called when a player is deleted from the view jogadorTotalInfo and it puts the player in the
-- "Banido" state.
CREATE TRIGGER banirJogador
    INSTEAD OF DELETE ON jogadorTotalInfo
    FOR EACH ROW
    EXECUTE FUNCTION banirJogador();
