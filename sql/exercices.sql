--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username, desativar e banir o jogador;
DROP PROCEDURE IF EXISTS create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);

CREATE PROCEDURE create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL)
    LANGUAGE plpgsql
AS
$$
    BEGIN
        IF (regiao_nome NOT IN (SELECT regiao.nome FROM regiao)) THEN
            INSERT INTO regiao VALUES (regiao_nome);
        END IF;
        INSERT INTO jogador(username, email, nome_regiao) VALUES (new_username, new_email, regiao_nome);
    END;
$$;

DROP PROCEDURE IF EXISTS update_status_jogador(id_jogador INT, new_status VARCHAR(10));

CREATE PROCEDURE update_status_jogador(id_jogador INT, new_status VARCHAR(10))
    LANGUAGE plpgsql
AS
$$
    BEGIN
        IF (id_jogador NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE NOTICE 'jogador not found';
        END IF ;
        UPDATE jogador SET status = new_status WHERE jogador.id = id_jogador;
    END ;
$$;

-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.
DROP FUNCTION IF EXISTS totalPontosJogador(jogador_id INT) CASCADE;

CREATE FUNCTION totalPontosJogador(jogador_id INT)
    RETURNS INT LANGUAGE plpgsql
AS
$$
    DECLARE
        partidaNormal INT;
        partidaMultiJogador INT;
    BEGIN
        SELECT pontuacao FROM partida_normal WHERE (nr_partida == (
        SELECT partida.nr FROM partida WHERE (
        SELECT joga.nr_partida FROM joga WHERE (joga.id_jogador == jogador_id)))) INTO partidaNormal;

        SELECT pontuacao FROM partida_multijogador WHERE (nr_partida == (
        SELECT partida.nr FROM partida WHERE (
        SELECT joga.nr_partida FROM joga WHERE (joga.id_jogador == jogador_id)))) INTO partidaMultiJogador;
        -- DUVIDA: temos de dar insert na tabela de estatisca do jogador
        RETURN partidaNormal + partidaMultiJogador;
    end;
$$;

-- (f) Criar a função totalJogosJogador que recebe como parâmetro o identificador de um jogador e devolve o número total
-- de jogos diferentes nos quais o jogador participou.
DROP FUNCTION IF EXISTS totalJogosJogador(jogador_id INT);

CREATE FUNCTION totalJogosJogador(jogador_id INT)
    RETURNS INT LANGUAGE plpgsql
AS
$$
    DECLARE
        jogosNormal INT;
        jogosMultiJogador INT;
    BEGIN
        SELECT count() INTO jogosNormal FROM partida_normal WHERE partida_normal.nr_partida == (
        SELECT partida.nr FROM partida WHERE (partida.nr == (
        SELECT joga.nr_partida FROM joga WHERE (joga.id_jogador == jogador_id))));

        SELECT count() INTO jogosMultiJogador FROM partida_multijogador WHERE partida_multijogador.nr_partida == (
        SELECT partida.nr FROM partida WHERE (partida.nr == (
        SELECT joga.nr_partida FROM joga WHERE (joga.id_jogador == jogador_id))));
        -- DUVIDA: temos de dar insert na tabela de estatisca do jogador
        RETURN jogosNormal + jogosMultiJogador;
    END;
$$;

-- (g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo e devolve uma tabela com
-- duas colunas (identificador de jogador, total de pontos) em que cada linha contém o identificador de um jogador e o
-- total de pontos que esse jogador teve nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo.
DROP FUNCTION IF EXISTS PontosJogoPorJogador(jogo_id ALPHANUMERIC);

CREATE FUNCTION PontosJogoPorJogador(jogo_id ALPHANUMERIC)
    RETURNS TABLE (id_jogador INT, total_pontos INT) LANGUAGE plpgsql
AS
$$
    DECLARE

    BEGIN
        RETURN QUERY SELECT joga.id_jogador, totalPontosJogador(joga.id_jogador) FROM joga WHERE joga.id_jogador IN (
        SELECT joga.id_jogador FROM joga WHERE joga.nr_partida IN (
        SELECT partida.nr FROM partida WHERE partida.id_jogo == jogo_id));
    END;
$$;

-- (h) Criar o procedimento armazenado associarCrachá que recebe como parâmetros o identificador de um jogador, a
-- referência de um jogo e o nome de um crachá desse jogo e atribui o crachá a esse jogador se ele reunir as condições
-- para o obter.
DROP PROCEDURE IF EXISTS associarCrachá(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50));

CREATE PROCEDURE associarCrachá(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nome_cracha VARCHAR(50);
        total_pontos INT;
    BEGIN
        SELECT nome INTO nome_cracha FROM cracha WHERE cracha.nome == cracha_nome;
        SELECT totalPontosJogador(jogador_id) INTO total_pontos;
        IF (total_pontos >= (SELECT limite_pontos FROM cracha WHERE cracha.nome == cracha_nome)) THEN
            INSERT INTO ganha VALUES (jogador_id, cracha_nome);
        END IF;
    END;
$$;

-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.
DROP PROCEDURE IF EXISTS iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT);

CREATE PROCEDURE iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nome_conversa VARCHAR(50);
    BEGIN
        SELECT nome INTO nome_conversa FROM conversa WHERE conversa.nome == nome_conversa;
        IF (nome_conversa == NULL) THEN
            INSERT INTO conversa VALUES (nome_conversa);
            SELECT id INTO conversa_id FROM conversa WHERE conversa.nome == nome_conversa;
            INSERT INTO participa VALUES (jogador_id, conversa_id);
            INSERT INTO mensagem VALUES ('O jogador criou a conversa', now(), jogador_id, conversa_id);
        END IF;
    END;
$$;

-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.
DROP PROCEDURE IF EXISTS juntarConversa(jogador_id INT, conversa_id INT);

CREATE PROCEDURE juntarConversa(jogador_id INT, conversa_id INT)
    LANGUAGE plpgsql
AS
$$
    BEGIN
        INSERT INTO participa VALUES (jogador_id, conversa_id);
        INSERT INTO mensagem VALUES ('O jogador entrou na conversa', now(), jogador_id, conversa_id);
    END;
$$;

-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.
DROP PROCEDURE IF EXISTS enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50));

CREATE PROCEDURE enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50))
    LANGUAGE plpgsql
AS
$$
    BEGIN
        INSERT INTO mensagem VALUES (mensagem_texto, now(), jogador_id, conversa_id);
    END;
$$;

-- (l) Criar a vista jogadorTotalInfo que permita aceder à informação sobre identificador, estado, email, username,
-- número total de jogos em que participou, número total de partidas em que participou e número total de pontos que já
-- obteve de todos os jogadores cujo estado seja diferente de “Banido”. Deve implementar na vista os cálculos sem aceder
-- às tabelas de estatísticas.
DROP FUNCTION IF EXISTS totalPartidasJogador(jogador_id INT);

CREATE FUNCTION totalPartidasJogador(jogador_id INT)
    RETURNS INT LANGUAGE plpgsql
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

CREATE VIEW jogadorTotalInfo AS
    SELECT jogador.id, jogador.status, jogador.email, jogador.username, totalJogosJogador(jogador.id) AS total_jogos,
    totalPartidasJogador(jogador.id) AS total_partidas, totalPontosJogador(jogador.id) AS total_pontos
    FROM jogador WHERE jogador.status != 'Banido';

-- (m) Criar os mecanismos necessários para que, de forma automática, quando uma partida termina, se proceda à
-- atribuição de crachás do jogo a que ela pertence.
DROP FUNCTION IF EXISTS atribuirCrachas() CASCADE;

CREATE FUNCTION atribuirCrachas()
    RETURNS trigger LANGUAGE plpgsql
AS
$$
    DECLARE
        jogo_id ALPHANUMERIC;
        jogador_id INT;
        nome_cracha VARCHAR(50);
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
            INSERT INTO ganha VALUES (jogador_id, nome_cracha);
        END IF;
    END;
$$;

DROP TRIGGER IF EXISTS atribuirCrachas ON partida;

CREATE TRIGGER atribuirCrachas AFTER INSERT ON partida
    FOR EACH ROW
    EXECUTE PROCEDURE atribuirCrachas();

-- (n) Criar os mecanismos necessários para que a execução da instrução DELETE sobre a vista jogadorTotalInfo permita
-- colocar os jogadores envolvidos no estado “Banido”.
DROP FUNCTION IF EXISTS banirJogador();

CREATE FUNCTION banirJogador()
    RETURNS trigger LANGUAGE plpgsql
AS
$$
    DECLARE
        jogador_id INT;
    BEGIN
        SELECT id INTO jogador_id FROM jogador WHERE jogador.username == OLD.username;
        UPDATE jogador SET status = 'Banido' WHERE jogador.id == jogador_id;
    END;
$$;

DROP TRIGGER IF EXISTS banirJogador ON jogadorTotalInfo;

CREATE TRIGGER banirJogador INSTEAD OF DELETE ON jogadorTotalInfo
    FOR EACH ROW
    EXECUTE PROCEDURE banirJogador();
