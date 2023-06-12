--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username, desativar e banir o jogador;

-- This procedure creates a new player given their region name, username, and email. It is an abstraction with
-- transaction control and it performs the following tasks:
--
-- Sets the transaction isolation level to SERIALIZABLE.
-- Calls the create_jogador procedure.
-- If the create_jogador procedure raises a unique_violation exception, it raises a notice indicating that the player
-- already exists and rolls back the transaction.
-- Example usage:
-- CALL createJogadorTransaction('Regiao A', 'user123', 'user123@gmail.com');
CREATE OR REPLACE PROCEDURE createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(50), new_email EMAIL)
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
        ROLLBACK;
        SET TRANSACTION ISOLATION LEVEL REPEATABLE READ ; -- default is READ COMMITTED
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
CREATE OR REPLACE PROCEDURE create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(50), new_email EMAIL)
    LANGUAGE plpgsql
AS
$$
    BEGIN
        -- Checks
        IF (regiao_nome NOT IN (SELECT regiao.nome FROM regiao)) THEN
            RAISE EXCEPTION 'regiao not found' USING
                HINT = 'Before creating a new jogador, create a new regiao with the given regiao_nome';
        END IF;
        IF (new_username IN (SELECT jogador.username FROM jogador)) THEN
            RAISE EXCEPTION 'username already in use' USING
                HINT = 'Change to a different username';
        END IF;
        IF (new_email IN (SELECT jogador.email FROM jogador)) THEN
            RAISE EXCEPTION 'email already in use' USING
                HINT = 'Change to a different account';
        END IF;
        -- expected
        INSERT INTO jogador(username, email, nome_regiao) VALUES (new_username, new_email, regiao_nome);
    END;
$$;

-- This procedure updates the state of a player given their player ID and new state. It performs the following tasks:
--
-- Checks if the given player ID exists in the jogador table. If not, it raises a notice indicating that the player was not found.
-- Checks if the player already has the new state. If so, it raises a notice indicating that the player already has this state.
-- Updates the estado field of the jogador table with the new state for the given player ID.
-- Example usage:
-- CALL update_estado_jogador(1, 'ativo');
CREATE OR REPLACE PROCEDURE update_estado_jogador(id_jogador INT, new_estado VARCHAR(10))
    LANGUAGE plpgsql
AS
$$
    BEGIN
        -- Checks
        IF (id_jogador NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'jogador not found';
        END IF;
        IF ((SELECT jogador.estado FROM jogador WHERE jogador.id = id_jogador) = new_estado) THEN
            RAISE NOTICE 'jogador already has this estado';
            RETURN;
        END IF;
        -- expected
        UPDATE jogador SET estado = new_estado WHERE jogador.id = id_jogador;
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.

-- This function calculates and returns the total points obtained by a player given their player ID. It performs the
-- following tasks:
--
-- Checks if the given player ID exists in the jogador table. If not, it raises a exception indicating that the player was
-- not found.
-- Checks if the player is banned. If so, it raises a exception.
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
        pontos_normal INT;
        pontos_multi INT;
        estado_jogador   VARCHAR(10);
    BEGIN
        -- Checks
        IF(jogador_id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'jogador not found';
        END IF;
        SELECT jogador.estado into estado_jogador FROM jogador WHERE jogador.id = jogador_id;
        IF (estado_jogador ~* '^(banido)$') THEN
            RAISE EXCEPTION 'jogador is banned';
        ELSIF (estado_jogador ~* '^(inativo)$') THEN
            RAISE NOTICE 'jogador is inactive';
        END IF;
        IF (jogador_id not in (SELECT joga.id_jogador FROM joga)) THEN
            RAISE EXCEPTION 'jogador has not played any games';
        END IF;
        -- expected
        -- counts the points of partidas normais
        SELECT SUM(joga.pontuacao) INTO pontos_normal FROM joga
        JOIN partida_normal pn on joga.id_jogo = pn.id_jogo AND joga.nr_partida = pn.nr
        JOIN partida p ON joga.id_jogo = p.id_jogo AND joga.nr_partida = p.nr
        WHERE joga.id_jogador = jogador_id AND p.data_fim IS NOT NULL;

        -- counts the points of partidas multijogador
        SELECT SUM(joga.pontuacao) INTO pontos_multi FROM joga
        JOIN partida_multijogador pm ON joga.id_jogo = pm.id_jogo AND joga.nr_partida = pm.nr
        JOIN partida p ON joga.id_jogo = p.id_jogo AND joga.nr_partida = p.nr
        WHERE joga.id_jogador = jogador_id AND pm.estado ~* '^(terminada)$' AND p.data_fim IS NOT NULL;

        RETURN pontos_normal + pontos_multi;
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
        jogos INT;
        estado_jogador VARCHAR(10);
    BEGIN
        -- Checks
        IF(jogador_id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'jogador not found';
        END IF;
        SELECT jogador.estado into estado_jogador FROM jogador WHERE jogador.id = jogador_id;
                IF (estado_jogador ~* '^(banido)$') THEN
            RAISE EXCEPTION 'jogador is banned';
        ELSIF (estado_jogador ~* '^(inativo)$') THEN
            RAISE NOTICE 'jogador is inactive';
        END IF;
        IF (jogador_id NOT IN (SELECT joga.id_jogador FROM joga)) THEN
            RAISE EXCEPTION 'jogador has not played any games';
        END IF;
        -- expected
        -- counts the number of partidas
        WITH multiplayer_games AS (
        SELECT DISTINCT joga.id_jogo
        FROM joga
        JOIN partida ON joga.id_jogo = partida.id_jogo AND joga.nr_partida = partida.nr
        JOIN partida_multijogador pm ON joga.id_jogo = pm.id_jogo AND joga.nr_partida = pm.nr
        WHERE joga.id_jogador = jogador_id AND pm.estado ~* '^(terminada)$' AND partida.data_fim IS NOT NULL
    ), normal_games AS (
        SELECT DISTINCT joga.id_jogo
        FROM joga
        JOIN partida ON joga.id_jogo = partida.id_jogo AND joga.nr_partida = partida.nr
        JOIN partida_normal pn ON joga.id_jogo = pn.id_jogo AND joga.nr_partida = pn.nr
        WHERE joga.id_jogador = jogador_id AND partida.data_fim IS NOT NULL
    )
    SELECT COUNT(*) INTO jogos
    FROM (SELECT id_jogo FROM multiplayer_games UNION SELECT id_jogo FROM normal_games) AS all_games;

        RETURN jogos;
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
            RAISE EXCEPTION 'jogo not found';
        END IF;
        IF (jogo_id NOT IN (SELECT joga.id_jogo FROM joga)) THEN
            RAISE EXCEPTION 'jogo has not been played';
        END IF;

        RETURN QUERY SELECT joga.id_jogador, SUM(COALESCE(joga.pontuacao, 0))::integer AS total_pontos FROM joga
        JOIN partida p ON joga.id_jogo = p.id_jogo AND joga.nr_partida = p.nr
        LEFT JOIN partida_normal pn ON joga.id_jogo = pn.id_jogo AND joga.nr_partida = pn.nr
        LEFT JOIN partida_multijogador pm ON joga.id_jogo = pm.id_jogo AND joga.nr_partida = pm.nr AND pm.estado ~* '^(terminada)$'
        WHERE joga.id_jogo = jogo_id AND p.data_fim IS NOT NULL
        GROUP BY joga.id_jogador;
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (h) Criar o procedimento armazenado associarCrachá que recebe como parâmetros o identificador de um jogador, a
-- referência de um jogo e o nome de um crachá desse jogo e atribui o crachá a esse jogador se ele reunir as condições
-- para o obter.

-- This procedure associates a crachá with a player if they meet the conditions to obtain it.
--
-- Example usage:
-- CALL associarCracha(1, '1', 'Crachá de Ouro');
CREATE OR REPLACE PROCEDURE associarCracha(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nome_cracha VARCHAR(50);
        pontos INT;
    BEGIN
        IF (jogador_id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'jogador not found';
        END IF;
        IF (jogo_id NOT IN (SELECT jogo.id FROM jogo)) THEN
            RAISE EXCEPTION 'jogo not found';
        END IF;
        IF (jogo_id NOT IN (SELECT joga.id_jogo FROM joga)) THEN
            RAISE EXCEPTION 'jogo has not been played';
        END IF;
        IF (NOT EXISTS(SELECT * FROM cracha WHERE cracha.nome = cracha_nome AND cracha.id_jogo = jogo_id)) THEN
            RAISE EXCEPTION 'cracha not found';
        END IF;
        IF (cracha_nome IN (SELECT ganha.nome_cracha FROM ganha WHERE ganha.id_jogador = jogador_id and ganha.id_jogo = jogo_id)) THEN
            RAISE EXCEPTION 'jogador already has this cracha';
        END IF;
        SELECT nome INTO nome_cracha FROM cracha WHERE cracha.nome = cracha_nome;
        SELECT total_pontos INTO pontos FROM PontosJogoPorJogador(jogo_id) pontos_jogo WHERE pontos_jogo.id_jogador = jogador_id;
        IF (pontos >= (SELECT limite_pontos FROM cracha WHERE cracha.nome = cracha_nome)) THEN
            INSERT INTO ganha (id_jogador, id_jogo, nome_cracha) VALUES (jogador_id, jogo_id, cracha_nome);
        END IF;
    END
$$;

CREATE OR REPLACE PROCEDURE associarCrachatransacao(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50))
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
        ROLLBACK;
        SET TRANSACTION ISOLATION LEVEL REPEATABLE READ ; -- default is READ COMMITTED
        Call associarCracha(jogador_id, jogo_id, cracha_nome);
        COMMIT;
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
-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.

-- This procedure starts a chat given the player ID and the name of the chat. The player is automatically associated
-- with the chat and a message is created informing that the player created the chat. The procedure returns the chat
-- ID in an output parameter.
--
-- Example usage:
-- CALL iniciarConversa(1, 'Chat 1', conversa_id);
CREATE OR REPLACE PROCEDURE iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        default_conversa_nr_ordem INT := 1;
    BEGIN
        -- Check if a conversation with the same name already exists
        PERFORM 1 FROM conversa WHERE conversa.nome = nome_conversa;
        IF NOT FOUND THEN
            -- Create a new conversation
            INSERT INTO conversa (nome) VALUES (nome_conversa);
            SELECT id INTO conversa_id FROM conversa WHERE conversa.nome = nome_conversa;
            -- Associate the player with the conversation
            INSERT INTO participa (id_jogador, id_conversa) VALUES (jogador_id, conversa_id);
            -- Add a message that the player created the conversation
            INSERT INTO mensagem (nr_ordem, id_jogador, id_conversa, texto, data)
            VALUES (default_conversa_nr_ordem, jogador_id, conversa_id,'O jogador criou a conversa', LOCALTIMESTAMP);
        END IF;
    END;
$$;

CREATE OR REPLACE PROCEDURE iniciarConversatransacao(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT)
    LANGUAGE plpgsql
    AS
$$
    Declare
        conversa_id INT;
        t1 text;
        t2 text;
        t3 text;
        t4 text;
        t5 text;
    BEGIN
        ROLLBACK;
        SET TRANSACTION ISOLATION LEVEL REPEATABLE READ ; -- default is READ COMMITTED
        Call iniciarConversa(jogador_id, nome_conversa, conversa_id);
        COMMIT;
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
-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.

-- This procedure joins a player to a chat given the player ID and the chat ID. A message is created informing that
-- the player joined the chat.
--
-- Example usage:
-- CALL juntarConversa(1, 1);
CREATE OR REPLACE PROCEDURE juntarConversa(jogador_id INT, conversa_id INT)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nr INT;
    BEGIN
        -- Check if the chat exists in the conversa table
        IF (NOT EXISTS (SELECT 1 FROM conversa WHERE id = conversa_id)) THEN
            RAISE EXCEPTION 'Chat with ID % not found', conversa_id;
        END IF;

        -- Associate the player with the chat
        INSERT INTO participa VALUES (jogador_id, conversa_id);

        SELECT COUNT(mensagem.nr_ordem) INTO nr FROM mensagem WHERE id_jogador = jogador_id AND mensagem.id_conversa = conversa_id;
        nr := nr + 1;
        INSERT INTO mensagem(nr_ordem, id_conversa, id_jogador, texto, data) VALUES (nr, conversa_id, jogador_id, 'O jogador entrou na conversa', LOCALTIMESTAMP);
    END;
$$;

CREATE OR REPLACE PROCEDURE juntarConversatransacao(jogador_id INT, conversa_id INT)
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
        ROLLBACK;
        SET TRANSACTION ISOLATION LEVEL REPEATABLE READ ; -- default is READ COMMITTED
        Call juntarConversa(jogador_id, conversa_id);
        COMMIT;
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
-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.

-- This procedure sends a message to a chat given the player ID, the chat ID and the message text. The message is
-- associated with the player and the chat.
--
-- Example usage:
-- CALL enviarMensagem(1, 1, 'Mensagem 1');
CREATE OR REPLACE PROCEDURE enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nr INT;
    BEGIN
        --checks
        IF (jogador_id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'O jogador com o id (%) não existe', jogador_id;
        END IF;
        IF (conversa_id NOT IN (SELECT conversa.id FROM conversa)) THEN
            RAISE EXCEPTION 'A conversa com o id (%) não existe', conversa_id;
        END IF;
        IF (mensagem_texto IS NULL) THEN
            RAISE EXCEPTION 'A mensagem (%) não tem conteúdo', mensagem_texto;
        END IF;
        --expected
        IF (jogador_id NOT IN (SELECT id_jogador FROM participa WHERE participa.id_conversa = conversa_id)) THEN
            INSERT INTO participa VALUES (jogador_id, conversa_id);
        END IF;
        SELECT COUNT(mensagem.nr_ordem) INTO nr FROM mensagem WHERE id_jogador = jogador_id AND mensagem.id_conversa = conversa_id;
        nr := nr + 1;
        INSERT INTO mensagem(nr_ordem, id_conversa, id_jogador, texto, data) VALUES (nr, conversa_id, jogador_id, mensagem_texto, LOCALTIMESTAMP);
    END;
$$;

CREATE OR REPLACE PROCEDURE enviarMensagemtransacao(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50))
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
        ROLLBACK;
        SET TRANSACTION ISOLATION LEVEL REPEATABLE READ ; -- default is READ COMMITTED
        Call enviarMensagem(jogador_id, conversa_id, mensagem_texto);
        COMMIT;
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
        nrs INT;
    BEGIN
        SELECT COUNT(nr_partida) INTO nrs FROM joga WHERE joga.id_jogador = jogador_id;
        RETURN nrs;
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
    FROM jogador WHERE jogador.estado !~* '^(banido)$';

------------------------------------------------------------------------------------------------------------------------
-- (m) Criar os mecanismos necessários para que, de forma automática, quando uma partida termina, se proceda à
-- atribuição de crachás do jogo a que ela pertence.

-- This function is called when a match ends and it assigns badges to the players who have played the match.
--
-- Example usage:
--
CREATE OR REPLACE FUNCTION atribuirCrachas()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
    BEGIN
        -- Check if the match is a normal match or a multiplayer match
        IF EXISTS (
            SELECT 1
            FROM partida_normal pn
            WHERE pn.id_jogo = NEW.id_jogo AND pn.nr = NEW.nr_partida
        ) THEN
            -- For normal matches, assign badges based on the player's score if it exceeds the badge's limite_pontos
            INSERT INTO ganha (id_jogador, id_jogo, nome_cracha)
            SELECT j.id_jogador, j.id_jogo, c.nome
            FROM joga j
            JOIN cracha c ON j.id_jogo = c.id_jogo
            WHERE j.nr_partida = NEW.nr_partida
                AND j.id_jogo = NEW.id_jogo
                AND j.pontuacao >= c.limite_pontos;

        ELSE
            -- For multiplayer matches, check if the match is "Terminada" and assign badges based on the player's score
            IF EXISTS (
                SELECT 1
                FROM partida_multijogador pm
                WHERE pm.id_jogo = NEW.id_jogo
                    AND pm.nr = NEW.nr_partida
                    AND pm.estado ~* '^(terminada)$'
            ) THEN
                -- Check if the player's score exceeds the badge's limite_pontos
                INSERT INTO ganha (id_jogador, id_jogo, nome_cracha)
                SELECT j.id_jogador, j.id_jogo, c.nome
                FROM joga j
                JOIN cracha c ON j.id_jogo = c.id_jogo
                WHERE j.nr_partida = NEW.nr_partida
                    AND j.id_jogo = NEW.id_jogo
                    AND j.pontuacao >= c.limite_pontos;
            END IF;
        END IF;

        RETURN NEW;
    END;
$$;

-- This trigger is called when a match ends and it assigns badges to the players who have played the match if they
-- have obtained enough points.
CREATE OR REPLACE TRIGGER atribuirCrachasTrigger
    AFTER UPDATE OF pontuacao ON joga
    FOR EACH ROW
    WHEN (NEW.pontuacao >= 0)
    EXECUTE PROCEDURE atribuirCrachas();

------------------------------------------------------------------------------------------------------------------------
-- (n) Criar os mecanismos necessários para que a execução da instrução DELETE sobre a vista jogadorTotalInfo permita
-- colocar os jogadores envolvidos no estado “Banido”.

-- This function is called when a player is deleted from the view jogadorTotalInfo and it puts the player in the
-- "Banido" state.
--
-- Example usage:
--
CREATE OR REPLACE FUNCTION banirJogador()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
DECLARE
    jogador_id INT;
BEGIN
    SELECT jogador.id INTO jogador_id FROM jogador WHERE jogador.username = OLD.username;
    UPDATE jogador SET estado = 'banido' WHERE jogador.id = jogador_id;
    RETURN OLD;
END;
$$;

-- This trigger is called when a player is deleted from the view jogadorTotalInfo and it puts the player in the
-- "Banido" state.
CREATE OR REPLACE TRIGGER banirJogadorTrigger
    INSTEAD OF DELETE ON jogadorTotalInfo
    FOR EACH ROW
EXECUTE FUNCTION banirJogador();
