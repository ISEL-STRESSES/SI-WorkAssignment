--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username, desativar e banir o jogador;

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

DROP PROCEDURE IF EXISTS create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);

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

DROP PROCEDURE IF EXISTS update_status_jogador(id_jogador INT, new_status VARCHAR(10));

-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.

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

DROP FUNCTION IF EXISTS totalPontosJogador(jogador_id INT);

-- (f) Criar a função totalJogosJogador que recebe como parâmetro o identificador de um jogador e devolve o número total
-- de jogos diferentes nos quais o jogador participou.

CREATE FUNCTION totalJogosJogador(jogador_id INT)
    RETURNS INT LANGUAGE plpgsql
AS
$$
    DECLARE
        jogosNormal INT;
        jogosMultiJogador INT;
    BEGIN;
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

DROP FUNCTION IF EXISTS totalJogosJogador(jogador_id INT);

-- (g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo e devolve uma tabela com
-- duas colunas (identificador de jogador, total de pontos) em que cada linha contém o identificador de um jogador e o
-- total de pontos que esse jogador teve nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo.


-- (h) Criar o procedimento armazenado associarCrachá que recebe como parâmetros o identificador de um jogador, a
-- referência de um jogo e o nome de um crachá desse jogo e atribui o crachá a esse jogador se ele reunir as condições
-- para o obter.

-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.

-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.

-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.

-- (l) Criar a vista jogadorTotalInfo que permita aceder à informação sobre identificador, estado, email, username,
-- número total de jogos em que participou, número total de partidas em que participou e número total de pontos que já
-- obteve de todos os jogadores cujo estado seja diferente de “Banido”. Deve implementar na vista os cálculos sem aceder
-- às tabelas de estatísticas.

-- (m) Criar os mecanismos necessários para que, de forma automática, quando uma partida termina, se proceda à
-- atribuição de crachás do jogo a que ela pertence.

-- (n) Criar os mecanismos necessários para que a execução da instrução DELETE sobre a vista jogadorTotalInfo permita
-- colocar os jogadores envolvidos no estado “Banido”.

-- (o) Criar um script autónomo com os testes das funcionalidades de 2d a 2n para cenários normais e de erro. Este
-- script, ao ser executado, deve listar, para cada teste, o seu nome e indicação se ele correu ou não com sucesso;