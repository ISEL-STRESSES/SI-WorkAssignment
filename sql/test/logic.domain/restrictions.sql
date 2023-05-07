-- 1. The partida must be played by jogadores of the same regiao
BEGIN TRANSACTION;

DROP FUNCTION IF EXISTS checkJogadorPartidaRegiao_init();
DROP PROCEDURE IF EXISTS test_checkJogadorPartidaRegiao_Ok(player_id int, game_id alphanumeric, region_name varchar(255));
DROP PROCEDURE IF EXISTS test_checkJogadorPartidaRegiao_NOK(player_id int, game_id alphanumeric, region_name varchar(255));

-- 1.1 Abstraction done thinking in java test classes, so we can have a clean test, can only be executed one due to have
--     hardcoded data
--     This function will create the data needed for the test, returning the player1_id, player2_id, game_id and
--     region_name
CREATE FUNCTION checkJogadorPartidaRegiao_init()
    RETURNS TABLE(player1_id int, player2_id int, game_id alphanumeric, region_name varchar(255))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name varchar(255) = 'test_region_1';
        player1_name varchar(255) = 'test_player1';
        player2_name varchar(255) = 'test_player2';
        player1_email varchar(255) = 'test_player1@gmail.com';
        player2_email varchar(255) = 'test_player2@hotmail.com';
        player1_id int;
        player2_id int;
        status1 varchar(20) = 'ativo';
        status2 varchar(20) = 'ativo';
        game_id alphanumeric = 'testGame1';
        game_name varchar(255) = 'test_game1';
        game_url varchar(255) = 'https://testGame1.com/?testgame01';
    BEGIN
        insert into regiao (nome) values (region_name);
        insert into jogador (username, email, estado, nome_regiao) values (player1_name, player1_email, status1, region_name);
        insert into jogador (username, email, estado, nome_regiao) values (player2_name, player2_email, status2, region_name);
        insert into jogo (id, nome, url) values (game_id, game_name, game_url);
        select jogador.id into player1_id from jogador where username = player1_name;
        select jogador.id into player2_id from jogador where username = player2_name;
        return QUERY SELECT player1_id, player2_id, game_id, region_name;
    END;
$$;

-- 1.2 Test procedure that given a player_id, game_id and region_name will create a partida and a joga entry, this will
--     trigger a 'trigger' that will check if the player is from the same region as the partida.
--     This procedure will be called with the player1_id, game_id and region_name returned by the
--     checkJogadorPartidaRegiao_init() and should not throw any exception
CREATE PROCEDURE test_checkJogadorPartidaRegiao_Ok(player_id int, game_id alphanumeric, region_name varchar(255))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        mach_nr integer = 1;
        match_difficulty integer = 1;

    BEGIN
        insert into partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) values (mach_nr, game_id, now(), null, region_name);
        insert into partida_normal(id_jogo, nr_partida, dificuldade) values (game_id, mach_nr, match_difficulty);
        insert into joga(id_jogador, nr_partida, id_jogo) VALUES (player_id, mach_nr, game_id);
    END;
$$;

-- 1.3 Test procedure that given a player_id, game_id and region_name will create a partida and a joga entry, this will
--     trigger a 'trigger' that will check if the player is from the same region as the partida.
--     This procedure will be called with the player2_id, game_id and region_name returned by the
--     checkJogadorPartidaRegiao_init() and should throw an exception.
CREATE PROCEDURE test_checkJogadorPartidaRegiao_NOK(player_id int, game_id alphanumeric, region_name varchar(255))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        partida_nr integer = 2;
        partida_dificuldade integer = 1;
        t1 text;
        t2 text;
        t3 text;
        t4 text;
        t5 text;
    BEGIN
        insert into partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) values (partida_nr, game_id, now(), null, region_name);
        insert into partida_normal (id_jogo, nr_partida, dificuldade) values (game_id, partida_nr, partida_dificuldade);
        insert into joga(id_jogador, nr_partida, id_jogo) VALUES (player_id, partida_nr, game_id);
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

COMMIT;

-- 1.4 Test function that will call the checkJogadorPartidaRegiao_init(), test_checkJogadorPartidaRegiao_Ok() and
--     test_checkJogadorPartidaRegiao_NOK() procedures and will return the result of the tests
BEGIN TRANSACTION;
do $$
    DECLARE
        player1_id int;
        player2_id int;
        game_id alphanumeric;
        region_name varchar(255);
        t1 text;
        t2 text;
        t3 text;
        t4 text;
        t5 text;
    BEGIN
        SELECT * FROM checkJogadorPartidaRegiao_init() into player1_id, player2_id, game_id, region_name;
        call test_checkJogadorPartidaRegiao_Ok(player1_id, game_id, region_name);
        IF ((SELECT count(*) FROM joga WHERE id_jogador = player1_id) <> 1) THEN
            RAISE EXCEPTION 'Player1 should have 1 joga entry';
        END IF;
        call test_checkJogadorPartidaRegiao_NOK(player2_id, game_id, region_name);
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
$$ language plpgsql;
COMMIT;

------------------------------------------------------------------------------------------------------------------------

-- 2. The mensagem must be sent by a jogador of the conversa
BEGIN TRANSACTION;

DROP FUNCTION IF EXISTS checkJogadorMensagemConversa_init();
DROP PROCEDURE IF EXISTS test_checkJogadorMensagemConversa_Ok();
DROP PROCEDURE IF EXISTS test_checkJogadorMensagemConversa_NOK();

-- 2.1 Abstraction of the test procedure that will create a conversa and a jogador entry, this will
CREATE FUNCTION checkJogadorMensagemConversa_init()
    RETURNS TABLE (player3_id int, player4_id int, chat_id int)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        player3_name text = 'player3';
        player3_email text = 'player3@iol.com';
        player_status text = 'ativo';
        player_region text = 'test_region2';
        player3_id int;
        player4_name text = 'player4';
        player4_email text = 'player4@edu.pt';
        player4_id int;
        conversa_id int;
        conversa_name text = 'test_chat1';
    BEGIN
        --player
        insert into regiao (nome) values (player_region);
        insert into jogador (username, email, estado, nome_regiao) values (player3_name, player3_email, player_status, player_region);
        insert into jogador (username, email, estado, nome_regiao) values (player4_name, player4_email, player_status, player_region);
        select jogador.id into player3_id from jogador where username = player3_name;
        select jogador.id into player4_id from jogador where username = player4_name;
        -- chat
        insert into conversa (nome) values (conversa_name);
        select conversa.id into conversa_id from conversa where nome = conversa_name;

        -- participates
        insert into participa (id_jogador, id_conversa) values (player3_id, conversa_id);

        return QUERY SELECT player3_id, player4_id, conversa_id;
    END;
$$;

-- 2.2 Test procedure that given a player_id and conversa_id will create a mensagem entry, this will
--     trigger a 'trigger' that will check if the player is from the same conversa as the mensagem.
--     This procedure will be called with the player_id and conversa_id returned by the
--     checkJogadorMensagemConversa_init() and should not throw an exception.
CREATE PROCEDURE test_checkJogadorMensagemConversa_Ok(player_id int, conversa_id int)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        mensagem_text text = 'test_message';
        order_nr int;
        t1 text;
        t2 text;
        t3 text;
        t4 text;
        t5 text;
    BEGIN
        --nr_ordem
        select count(nr_ordem) into order_nr FROM mensagem WHERE id_conversa = conversa_id;
        -- insert message
        insert into mensagem (nr_ordem, id_conversa, id_jogador, texto, data) values (order_nr, conversa_id, player_id, mensagem_text, now());
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

-- 2.3 Test procedure that given a player_id and conversa_id will create a mensagem entry, this will
--     trigger a 'trigger' that will check if the player is from the same conversa as the mensagem.
--     This procedure will be called with the player_id and conversa_id returned by the
--     checkJogadorMensagemConversa_init() and should throw an exception.
CREATE PROCEDURE test_checkJogadorMensagemConversa_NOK(player_id int, conversa_id int)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        mensagem_text text = 'test_message';
        order_nr int;
        t1 text;
        t2 text;
        t3 text;
        t4 text;
        t5 text;
    BEGIN
        --nr_ordem
        select count(nr_ordem) into order_nr FROM mensagem WHERE id_conversa = conversa_id;
        -- insert message
        insert into mensagem (nr_ordem, id_conversa, id_jogador, texto, data) values (order_nr, conversa_id, player_id, mensagem_text, now());
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

COMMIT;

-- 2.4 Test procedure that will call the checkJogadorMensagemConversa_init() and then call the
--     test_checkJogadorMensagemConversa_Ok() and test_checkJogadorMensagemConversa_NOK() procedures.
--     This procedure should not throw an exception.
BEGIN TRANSACTION;
do $$
    DECLARE
        player3_id int;
        player4_id int;
        chat_id int;
        t1 text;
        t2 text;
        t3 text;
        t4 text;
        t5 text;
    BEGIN
        select * into player3_id, player4_id, chat_id from checkJogadorMensagemConversa_init();
        call test_checkJogadorMensagemConversa_Ok(player3_id, chat_id);
        call test_checkJogadorMensagemConversa_NOK(player4_id, chat_id);
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
$$ language plpgsql;

COMMIT;