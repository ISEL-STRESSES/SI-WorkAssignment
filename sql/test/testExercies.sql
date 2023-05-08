-- 2.(o) Criar um script autónomo com os testes das funcionalidades de 2d a 2n para cenários normais e de erro. Este
-- script, ao ser executado, deve listar, para cada teste, o seu nome e indicação se ele correu ou não com sucesso;
-- (d.1)
CREATE OR REPLACE PROCEDURE test_d1()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion11';
        player_name1 varchar := 'TestPlayer11';
        player_name2 varchar := 'TestPlayer12';
        player_name3 varchar := 'TestPlayer13';
        player_email1 varchar := 'testplayer11@gmail.com';
        player_email2 varchar := 'testplayer12@hotmail.com';
        player_email3 varchar := 'testplayer13@iol.pt';
        t1 text;
    BEGIN
        DELETE FROM jogador WHERE email = player_email1;
        DELETE FROM jogador WHERE email = player_email2;
        DELETE FROM jogador WHERE email = player_email3;
        DELETE FROM regiao WHERE nome = region_name1;
        INSERT INTO regiao (nome) VALUES (region_name1);
        RAISE NOTICE 'Exercise 2d.1';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the creation of a new user without status, the default status for a new user is "ativo"';
    BEGIN -- Test 1: Creating a new player with valid parameters should succeed
        CALL create_jogador(region_name1, player_name1, player_email1);
        IF (EXISTS (SELECT * FROM jogador WHERE email = player_email1)) THEN
            RAISE NOTICE 'Test 1 succeeded';
        ELSE
            RAISE EXCEPTION 'Test 1 failed';
        END IF;
    END;
    BEGIN -- Test 2: Creating a new player with an existing username should fail
        CALL create_jogador(region_name1, player_name1, player_email2);
        EXCEPTION
            WHEN raise_exception THEN -- to vague
                GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
                IF (t1 = 'username already in use') THEN
                    RAISE NOTICE 'Test 2 succeeded';
                ELSE
                    RAISE EXCEPTION 'Test 2 failed';
                END IF;
            WHEN OTHERS THEN
                RAISE EXCEPTION 'Test 2 failed';
    END;
    BEGIN -- Test 3: Creating a new player with an existing email should fail
        CALL create_jogador(region_name1, player_name2, player_email1);
    EXCEPTION
        WHEN raise_exception THEN -- to vague
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            IF (t1 = 'email already in use') THEN
                RAISE NOTICE 'Test 3 succeeded';
            END IF;
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            RAISE EXCEPTION 'Test 3 failed with exception: %', t1;
    END;
    BEGIN -- Test 4: Creating a new player with a null username should fail
        CALL create_jogador(region_name1,NULL, player_email3);
    EXCEPTION
        WHEN not_null_violation THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            IF (t1 = 'null value in column "username" of relation "jogador" violates not-null constraint') THEN
                RAISE NOTICE 'Test 4 succeeded';
            ELSE
                RAISE EXCEPTION 'Test 4 failed with message: %', t1;
            END IF;
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            RAISE EXCEPTION 'Test 4 failed with exception: %', t1;
    END;
    BEGIN -- Test 5: Creating a new player with a null email should fail
        CALL create_jogador(region_name1, player_name3, NULL);
    EXCEPTION
        WHEN not_null_violation THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            IF (t1 = 'null value in column "email" of relation "jogador" violates not-null constraint') THEN
                RAISE NOTICE 'Test 5 succeeded';
            ELSE
                RAISE EXCEPTION 'Test 5 failed with message: %', t1;
            END IF;
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            RAISE EXCEPTION 'Test 5 failed with exception: %', t1;
    END;
    BEGIN -- Test 6: Creating a new player with a null region should fail
        CALL create_jogador(NULL, player_name3, player_email3);
    EXCEPTION
        WHEN not_null_violation THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            IF (t1 = 'null value in column "nome_regiao" of relation "jogador" violates not-null constraint') THEN
                RAISE NOTICE 'Test 6 succeeded';
            ELSE
                RAISE EXCEPTION 'Test 6 failed with message: %', t1;
            END IF;
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            RAISE EXCEPTION 'Test 6 failed with exception: %', t1;
    END;
        DELETE FROM jogador WHERE email = player_email1;
        DELETE FROM jogador WHERE email = player_email2;
        DELETE FROM jogador WHERE email = player_email3;
        DELETE FROM regiao WHERE nome = region_name1;
    END;
$$;

-- (d.2)
CREATE OR REPLACE PROCEDURE test_d2()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion12';
        player_name1 varchar := 'TestPlayer14';
        player_id1 integer;
        player_email1 varchar := 'testplayer14@gmail.com';
        t1 text;
    BEGIN

        DELETE FROM regiao WHERE nome = region_name1;
        INSERT INTO regiao (nome) VALUES (region_name1);
        INSERT INTO jogador (username, email, nome_regiao) VALUES (player_name1, player_email1, region_name1);
        SELECT jogador.id INTO player_id1 FROM jogador WHERE email = player_email1;
        RAISE NOTICE 'Exercise 2d.2';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the changing of the status of an jogador';
    BEGIN -- Test 1: Changing the status of an existing player to "inativo" should succeed
        CALL update_estado_jogador(player_id1, 'inativo');
        IF (EXISTS (SELECT * FROM jogador WHERE email = player_email1 AND estado = 'inativo')) THEN
            RAISE NOTICE 'Test 1 succeeded';
        ELSE
            RAISE EXCEPTION 'Test 1 failed';
        END IF;
    END;
    BEGIN -- Test 2: Changing the status of an existing player to the same status
        CALL update_estado_jogador(player_id1, 'inativo');
    EXCEPTION
        WHEN others THEN -- to vague due to notice
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            IF (t1 = 'player already in that state') THEN
                RAISE NOTICE 'Test 2 succeeded';
            ELSE
                RAISE EXCEPTION 'Test 2 failed with message: %', t1;
            END IF;
    END;
    BEGIN -- Test 3: changing the status of an enexisting player should fail
        CALL update_estado_jogador(999999, 'inativo');
    EXCEPTION
        WHEN raise_exception THEN -- to vague
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            IF (t1 = 'jogador not found') THEN
                RAISE NOTICE 'Test 3 succeeded';
            ELSE
                RAISE EXCEPTION 'Test 3 failed with message: %', t1;
            END IF;
        WHEN OTHERS THEN
            GET STACKED DIAGNOSTICS t1 = MESSAGE_TEXT;
            RAISE EXCEPTION 'Test 3 failed with exception: %', t1;
    END;
        DELETE FROM jogador WHERE email = player_email1;
        DELETE FROM regiao WHERE nome = region_name1;
    END;

$$;
------------------------------------------------------------------------------------------------------------------------
-- (e)
CREATE OR REPLACE PROCEDURE test_e()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion21';
        player_id1 integer;
        player_name1 varchar := 'TestPlayer21';
        player_email1 varchar := 'testplayer21@gmail.com';
        player_id2 integer;
        player_name2 varchar := 'TestPlayer22';
        player_email2 varchar := 'testplayer22@something.edu';
        player_banned varchar := 'Banido';
        game_id1 alphanumeric := 'testGame21';
        game_name1 varchar := 'TestGame21';
        game_url1 varchar := 'https://www.testgame21.com/?index';
        game_id2 alphanumeric := 'testGame22';
        game_name2 varchar := 'TestGame22';
        game_url2 varchar := 'https://www.testgame22.com/?index';
        match_start_date date := '2020-01-01';
        match_end_date date := '2020-01-02';
        match_ended varchar := 'Terminada';
        pontos integer;
        expected_pontos integer := 101111;
    BEGIN
        INSERT INTO regiao (nome) VALUES (region_name1);
        INSERT INTO jogador (username, email, nome_regiao) VALUES (player_name1, player_email1, region_name1);
        INSERT INTO jogador (username, email, estado, nome_regiao) VALUES (player_name2, player_email2, player_banned, region_name1);
        SELECT jogador.id INTO player_id1 FROM jogador WHERE email = player_email1;
        SELECT jogador.id INTO player_id2 FROM jogador WHERE email = player_email2;
        INSERT INTO jogo (id, nome, url) VALUES (game_id1, game_name1, game_url1);
        INSERT INTO jogo (id, nome, url) VALUES (game_id2, game_name2, game_url2);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (4, game_id1, match_start_date, null, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (4, game_id2, match_start_date, null, region_name1);
        INSERT INTO partida_multijogador (id_jogo, nr_partida, estado, pontuacao) VALUES (game_id1, 1, match_ended, null);
        INSERT INTO partida_multijogador (id_jogo, nr_partida, estado, pontuacao) VALUES (game_id1, 2, match_ended, 1);
        INSERT INTO partida_multijogador (id_jogo, nr_partida, estado, pontuacao) VALUES (game_id2, 1, match_ended, 10);
        INSERT INTO partida_multijogador (id_jogo, nr_partida, estado, pontuacao) VALUES (game_id2, 2, match_ended, 100);
        INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id1, 3, 1, 1000);
        INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id1, 4, 1, 10000);
        INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id2, 3, 1, 100000);
        INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id2, 4, 1, null);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id1);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 2, game_id1);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 3, game_id1);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 4, game_id1);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id2);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 2, game_id2);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 3, game_id2);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 4, game_id2);
        RAISE NOTICE 'Exercise 2e';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing sum of points from a specific player across all matches';
    BEGIN -- Test 1: Get player's total score across all matches without valid table content
        SELECT totalpontosjogador(player_id1) INTO pontos;
--         RAISE NOTICE 'Test 1: Get players total score across all matches';
        ASSERT pontos = expected_pontos, 'Test 1 failed';
        RAISE NOTICE 'Test 1 succeeded';
    END;
    BEGIN -- Test 2: Get player's total score form inexistent player
        SELECT totalpontosjogador(999999) INTO pontos;
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogador not found') THEN
                RAISE NOTICE 'Test 2 succeeded';
            ELSE
                RAISE NOTICE 'Test 2 failed with error: %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE NOTICE 'Test 2 failed with error: %', SQLERRM;
    END;
    BEGIN -- Test 3: Get player's total score from a banned player
        SELECT totalpontosjogador(player_id2) INTO pontos;
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogador is banned') THEN
                RAISE NOTICE 'Test 3 succeeded';
            ELSE
                RAISE NOTICE 'Test 3 failed with error: %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE NOTICE 'Test 3 failed with error: %', SQLERRM;
    END;
    BEGIN -- Test 4: Get player's total score from a inactive player with matches
        UPDATE jogador SET estado = 'inativo' WHERE id = player_id1;
        SELECT totalpontosjogador(player_id1) INTO pontos;
        ASSERT pontos = expected_pontos, 'Test 4 failed';
        RAISE NOTICE 'Test 4 succeeded';
    END;
    BEGIN -- Test 5: Get player's total score from a player without matches
        UPDATE jogador SET estado = 'ativo' WHERE id = player_id2;
        SELECT totalpontosjogador(player_id2) INTO pontos;
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogador has not played any games') THEN
                RAISE NOTICE 'Test 5 succeeded';
            ELSE
                RAISE NOTICE 'Test 5 failed with error: %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE NOTICE 'Test 5 failed with error: %', SQLERRM;
    END;
        DELETE FROM joga WHERE id_jogador = player_id1;
        DELETE FROM partida_normal WHERE id_jogo = game_id1;
        DELETE FROM partida_normal WHERE id_jogo = game_id2;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id1;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id2;
        DELETE FROM partida WHERE id_jogo = game_id1;
        DELETE FROM partida WHERE id_jogo = game_id2;
        DELETE FROM jogo WHERE id = game_id1;
        DELETE FROM jogo WHERE id = game_id2;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM jogador WHERE id = player_id2;
        DELETE FROM regiao WHERE nome = region_name1;
    END;
$$;
------------------------------------------------------------------------------------------------------------------------
-- (f)
CREATE OR REPLACE PROCEDURE test_f()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion31';
        player_id1 int;
        player_name1 varchar := 'TestPlayer31';
        player_email1 varchar := 'testplayer31@gmail.com';
        player_name2 varchar := 'TestPlayer32';
        player_email2 varchar := 'testplayer32@gmail.com';
        player_banned varchar := 'banido';
        player_id2 int;
        game_id1 alphanumeric := 'testGame31';
        game_name1 varchar := 'TestGame31';
        game_url1 varchar := 'https://www.testgame31.com/?index';
        game_id2 alphanumeric := 'testGame32';
        game_name2 varchar := 'TestGame32';
        game_url2 varchar := 'https://www.testgame32.com/?index';
        game_id3 alphanumeric := 'testGame33';
        game_name3 varchar := 'TestGame33';
        game_url3 varchar := 'https://www.testgame33.com/?index';
        game_id4 alphanumeric := 'testGame34';
        game_name4 varchar := 'TestGame34';
        game_url4 varchar := 'https://www.testgame34.com/?index';
        match_start_date date := '2020-01-01';
        match_end_date date := '2020-01-02';
        match_ended varchar := 'Terminada';
        match_in_progress varchar := 'Em curso';
        games int;
        expected_games int := 3;
    BEGIN
        INSERT INTO regiao (nome) VALUES (region_name1);
        INSERT INTO jogador (username, email, nome_regiao) VALUES (player_name1, player_email1,region_name1);
        INSERT INTO jogador (username, email, estado, nome_regiao) VALUES (player_name2, player_email2, player_banned, region_name1);
        SELECT jogador.id INTO player_id1 FROM jogador WHERE username = player_name1;
        SELECT jogador.id INTO player_id2 FROM jogador WHERE username = player_name2;
        INSERT INTO jogo (id, nome, url) VALUES (game_id1, game_name1, game_url1);
        INSERT INTO jogo (id, nome, url) VALUES (game_id2, game_name2, game_url2);
        INSERT INTO jogo (id, nome, url) VALUES (game_id3, game_name3, game_url3);
        INSERT INTO jogo (id, nome, url) VALUES (game_id4, game_name4, game_url4);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id3, match_start_date, match_end_date, region_name1);
        INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id4, match_start_date, match_end_date, region_name1);
        INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id1, 1, 1, 100);
        INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id2, 1, 1, 100);
        INSERT INTO partida_multijogador (id_jogo, nr_partida, estado, pontuacao) VALUES (game_id3, 1, match_ended, 100);
        INSERT INTO partida_multijogador (id_jogo, nr_partida, estado, pontuacao) VALUES (game_id4, 1, match_in_progress, 100);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id1);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id2);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id3);
        INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id4);
        RAISE NOTICE 'Exercise 2f';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing sum games played by a specific player';
    BEGIN -- Test 1: Gets a player's total games played
        SELECT totalJogosJogador(player_id1) INTO games;
        RAISE NOTICE 'Games played: %', games;
        ASSERT games = expected_games, 'Test 1 failed';
        RAISE NOTICE 'Test 1 succeeded';
    END;
    BEGIN -- Test 2: Get player's total games played from a non-existent player
        SELECT totalJogosJogador(999999) INTO games;
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogador not found') THEN
                RAISE NOTICE 'Test 2 succeeded';
            ELSE
                RAISE NOTICE 'Test 2 failed with error: %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE NOTICE 'Test 2 failed with error: %', SQLERRM;
    END;
    BEGIN -- Test 3: Get player's total games played from a banned player
        SELECT totalJogosJogador(player_id2) INTO games;
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogador is banned') THEN
                RAISE NOTICE 'Test 3 succeeded';
            ELSE
                RAISE NOTICE 'Test 3 failed with error: %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE NOTICE 'Test 3 failed with error: %', SQLERRM;
    END;
    BEGIN -- Test 4: Get player's total games played from a inactive player with matches
        UPDATE jogador SET estado = 'inativo' WHERE id = player_id1;
        SELECT totalJogosJogador(player_id1) INTO games;
        ASSERT games = expected_games, 'Test 4 failed';
        RAISE NOTICE 'Test 4 succeeded';
    END;
    BEGIN -- Test 5: Get player's total score from a player without matches
        UPDATE jogador SET estado = 'ativo' WHERE id = player_id2;
        SELECT totalJogosJogador(player_id2) INTO games;
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogador has not played any games') THEN
                RAISE NOTICE 'Test 5 succeeded';
            ELSE
                RAISE NOTICE 'Test 5 failed with error: %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE NOTICE 'Test 5 failed with error: %', SQLERRM;
    END;
        DELETE FROM joga WHERE id_jogador = player_id1;
        DELETE FROM joga WHERE id_jogador = player_id2;
        DELETE FROM partida_normal WHERE id_jogo = game_id1;
        DELETE FROM partida_normal WHERE id_jogo = game_id2;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id3;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id4;
        DELETE FROM partida WHERE id_jogo = game_id1;
        DELETE FROM partida WHERE id_jogo = game_id2;
        DELETE FROM partida WHERE id_jogo = game_id3;
        DELETE FROM partida WHERE id_jogo = game_id4;
        DELETE FROM jogo WHERE id = game_id1;
        DELETE FROM jogo WHERE id = game_id2;
        DELETE FROM jogo WHERE id = game_id3;
        DELETE FROM jogo WHERE id = game_id4;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM jogador WHERE id = player_id2;
        DELETE FROM regiao WHERE nome = region_name1;
    END;
$$;
------------------------------------------------------------------------------------------------------------------------
-- (g)
CREATE OR REPLACE PROCEDURE test_g()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        tabela REFCURSOR;
        region_name1 varchar := 'TestRegion41';
        player_name1 varchar := 'TestPlayer41';
        player_name2 varchar := 'TestPlayer42';
        player_email1 varchar := 'testplayer41@gmial.com';
        player_email2 varchar := 'testplayer42@gmial.com';
        player_id1 INT;
        player_id2 INT;
        game_id1 alphanumeric := 'testGame41';
        game_id2 alphanumeric := 'testGame42';
        game_name1 varchar := 'TestGame41';
        game_name2 varchar := 'TestGame42';
        game_url1 varchar := 'https://testgame41.com/index';
        game_url2 varchar := 'https://testgame42.com/index';
        match_start_date date := '2020-01-01';
        match_end_date date := '2020-01-02';
        match_ended varchar := 'Terminada';

        player INT;
        pontos int;
    BEGIN
        insert into regiao values(region_name1);
        CALL create_jogador(region_name1,player_name1, player_email1);
        SELECT jogador.id INTO player_id1 FROM jogador WHERE email = player_email1;

        CALL create_jogador(region_name1,player_name2, player_email2);
        SELECT jogador.id INTO player_id2 FROM jogador WHERE email = player_email2;

        INSERT INTO JOGO VALUES(game_id1, game_name1, game_url1);
        INSERT INTO PARTIDA VALUES(1, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO PARTIDA VALUES(2, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO PARTIDA VALUES(3, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO PARTIDA VALUES(4, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO PARTIDA VALUES(5, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO PARTIDA VALUES(6, game_id1, match_start_date, match_end_date, region_name1);

        INSERT INTO PARTIDA_NORMAL VALUES(game_id1, 1, 3, 1);
        INSERT INTO PARTIDA_NORMAL VALUES(game_id1, 3, 3, 10);
        INSERT INTO PARTIDA_MULTIJOGADOR VALUES (game_id1, 5, match_ended, 100);

        INSERT INTO PARTIDA_NORMAL VALUES(game_id1, 2, 3, 1000);
        INSERT INTO PARTIDA_MULTIJOGADOR VALUES(game_id1, 4, match_ended, 10000);
        INSERT INTO PARTIDA_MULTIJOGADOR VALUES(game_id1, 6, match_ended, 100000);

        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id1, 1,game_id1);
        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id1, 3,game_id1);
        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id1, 5,game_id1);
        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id2, 2,game_id1);
        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id2, 4,game_id1);
        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id2, 6,game_id1);
        RAISE NOTICE 'Exercise 2g';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the sum of points of a player in a game';
    BEGIN -- Test 1: Obtaining the total points of 2 player in a game
        OPEN tabela FOR SELECT * FROM pontosJogoPorJogador(game_id1);
        FOR i IN 0..1 LOOP
            FETCH NEXT FROM tabela INTO player, pontos;

            IF player_id1 = player AND pontos = 111 OR player_id2 = player AND pontos = 111000 THEN
                RAISE NOTICE 'Test 1 succeeded';
            ELSE RAISE EXCEPTION 'Test 1 failed';
            END IF;
        END LOOP;
        CLOSE tabela;
    END;
    BEGIN -- Test 2: Obtaining the point of an nonexistent game
        OPEN tabela FOR SELECT * FROM pontosJogoPorJogador(game_id2);
            FETCH NEXT FROM tabela INTO player, pontos;
            IF (player IS NULL AND pontos IS NULL) THEN
                RAISE NOTICE 'Test 2 succeeded';
            ELSE
            RAISE EXCEPTION 'Test 2 failed';
            END IF;
        CLOSE tabela;
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogo not found') THEN
                RAISE NOTICE 'Test 2 succeeded';
            ELSE RAISE EXCEPTION 'Test 2 failed with error %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE EXCEPTION 'Test 2 failed with error %', SQLERRM;
        CLOSE tabela;
    END;
    BEGIN -- Test 3: Obtaining the points of a not played game
        INSERT INTO JOGO VALUES(game_id2, game_name2, game_url2);
        OPEN tabela FOR SELECT * FROM pontosJogoPorJogador(game_id2);
        FETCH NEXT FROM tabela INTO player, pontos;
        RAISE EXCEPTION 'Test 3 failed';
        EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogo has not been played') THEN
                RAISE NOTICE 'Test 3 succeeded';
            ELSE RAISE EXCEPTION 'Test 3 failed with error %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE EXCEPTION 'Test 3 failed with error %', SQLERRM;
        CLOSE tabela;
    END;
        DELETE FROM joga WHERE id_jogador = player_id1;
        DELETE FROM joga WHERE id_jogador = player_id2;
        DELETE FROM partida_normal WHERE id_jogo = game_id1;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id1;
        DELETE FROM partida WHERE id_jogo = game_id1;
        DELETE FROM jogo WHERE id = game_id1;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM jogador WHERE id = player_id2;
        DELETE FROM regiao WHERE nome = region_name1;
    END;
$$;
-- (h)
CREATE OR REPLACE PROCEDURE test_h()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion51';
        player_name1 varchar := 'TestPlayer51';
        player_email1 varchar := 'testplayer51@gmial.com';
        player_id1 INT;
        player_id2 INT;
        game_id1 alphanumeric := 'testGame51';
        game_name1 varchar := 'TestGame51';
        game_url1 varchar := 'https://testgame51.com/index';
        game_id2 alphanumeric := 'testGame52';
        game_name2 varchar := 'TestGame52';
        game_url2 varchar := 'https://testgame52.com/index';
        badge_name1 varchar := 'TestBadge51';
        badge_image1 varchar := 'https://testegame51.com/?badge=testebadge51';
        badge_points1 int := 1;
        badge_name2 varchar := 'TestBadge52';
        badge_image2 varchar := 'https://testegame51.com/?badge=testebadge52';
        badge_points2 int := 10;
        badge_name3 varchar := 'TestBadge53';
        badge_image3 varchar := 'https://testegame52.com/?badge=testebadge53';
        badge_points3 int := 100;
        match_start_date date := '2020-01-01';
        match_end_date date := '2020-01-02';
        match_ended varchar := 'Terminada';
    BEGIN
        insert into regiao values(region_name1);
        CALL create_jogador(region_name1,player_name1, player_email1);
        SELECT jogador.id INTO player_id1 FROM jogador WHERE email = player_email1;

        INSERT INTO JOGO VALUES(game_id1, game_name1, game_url1);
        INSERT INTO cracha(nome, id_jogo, imagem, limite_pontos) values (badge_name1,game_id1, badge_image1, badge_points1);
        INSERT INTO cracha(nome, id_jogo, imagem, limite_pontos) values (badge_name2,game_id1, badge_image2, badge_points2);

        INSERT INTO PARTIDA VALUES(1, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO PARTIDA VALUES(2, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO PARTIDA VALUES(3, game_id1, match_start_date, match_end_date, region_name1);

        INSERT INTO PARTIDA_NORMAL VALUES(game_id1, 1, 3, 1);
        INSERT INTO partida_multijogador(id_jogo, nr_partida, estado, pontuacao) VALUES(game_id1, 2, match_ended, 5);
        INSERT INTO PARTIDA_MULTIJOGADOR VALUES (game_id1, 3, match_ended, null);

        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id1, 1,game_id1);
        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id1, 2,game_id1);
        insert into joga(id_jogador, nr_partida, id_jogo) values(player_id1, 3,game_id1);
        RAISE NOTICE 'Exercise 2h';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the attribution of a badges to a player';
    BEGIN -- Test 1: Giving only a badge to a player
        CALL associarcracha(player_id1, game_id1, badge_name1);
        CALL associarcracha(player_id1, game_id1, badge_name2);
        IF ((Select count(*) from ganha where id_jogador = player_id1 and id_jogo = game_id1) = 1) THEN
            IF ((Select ganha.nome_cracha from ganha where id_jogador = player_id1 and id_jogo = game_id1) = badge_name1) THEN
                RAISE NOTICE 'Test 1 succeeded';
            ELSE RAISE EXCEPTION 'Test 1 failed';
            END IF;
        END IF;
    EXCEPTION
        WHEN others THEN
            RAISE EXCEPTION 'Test 1 failed with error %', SQLERRM;
    END;
    BEGIN -- Test 2: Giving a badge to a player that already has it
        CALL associarcracha(player_id1, game_id1, badge_name1);
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogador already has this cracha') THEN
                RAISE NOTICE 'Test 2 succeeded';
            ELSE RAISE EXCEPTION 'Test 2 failed with error %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE EXCEPTION 'Test 2 failed with error %', SQLERRM;
    END;
    BEGIN -- Test 3: Giving a badge to a player that doesn't exist
        CALL associarcracha(999999, game_id1, badge_name1);
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogador not found') THEN
                RAISE NOTICE 'Test 3 succeeded';
            ELSE RAISE EXCEPTION 'Test 3 failed with error %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE EXCEPTION 'Test 3 failed with error %', SQLERRM;
    END;
    BEGIN -- Test 4: Giving a badge to a player of an nonexistent game
        CALL associarcracha(player_id1, 'abc123def4', badge_name1);
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogo not found') THEN
                RAISE NOTICE 'Test 4 succeeded';
            ELSE RAISE EXCEPTION 'Test 4 failed with error %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE EXCEPTION 'Test 4 failed with error %', SQLERRM;
    END;
    BEGIN -- Test 5: Giving an unexistent badge to a player
        CALL associarcracha(player_id1, game_id1, 'abc123def4');
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'cracha not found') THEN
                RAISE NOTICE 'Test 5 succeeded';
            ELSE RAISE EXCEPTION 'Test 5 failed with error %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE EXCEPTION 'Test 5 failed with error %', SQLERRM;
    END;
    BEGIN -- Test 6: Giving a badge to a player that doesn't have the required points
        CALL associarcracha(player_id1, game_id1, badge_name2);
        IF (SELECT ganha.nome_cracha from ganha where id_jogador = player_id1 and id_jogo = game_id1) = badge_name2 THEN
            RAISE EXCEPTION 'Test 6 failed';
        ELSE RAISE NOTICE 'Test 6 succeeded';
        END IF;
    END;
    BEGIN -- Test 7: Giving a badge to a player from an unplayed game
        insert into jogo(id, nome, url) values(game_id2, game_name2, game_url2);
        insert into cracha(nome, id_jogo, imagem, limite_pontos) values (badge_name3, game_id2, badge_image3, badge_points3);

        CALL associarcracha(player_id1, game_id2, badge_name3);
    EXCEPTION
        WHEN raise_exception THEN
            IF (SQLERRM = 'jogo has not been played') THEN
                RAISE NOTICE 'Test 7 succeeded';
            ELSE RAISE EXCEPTION 'Test 7 failed with error %', SQLERRM;
            END IF;
        WHEN others THEN
            RAISE EXCEPTION 'Test 7 failed with error %', SQLERRM;
    END;
        DELETE FROM joga WHERE id_jogador = player_id1;
        DELETE FROM partida_normal WHERE id_jogo = game_id1;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id1;
        DELETE FROM partida WHERE id_jogo = game_id1;
        DELETE FROM ganha WHERE id_jogo = game_id1;
        DELETE FROM cracha WHERE id_jogo = game_id1;
        DELETE FROM jogo WHERE id = game_id1;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM jogador WHERE id = player_id2;
        DELETE FROM regiao WHERE nome = region_name1;
    END;
$$;
------------------------------------------------------------------------------------------------------------------------
-- (i)
--iniciarConversa tests
CREATE OR REPLACE PROCEDURE test_i()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion61';
        player_name1 varchar := 'TestPlayer61';
        player_email1 varchar := 'testplayer61@gmail.com';
        player_id1 INT;
        chat_name varchar := 'TestChat61';
        chat_id INT :=0;
    BEGIN
        insert into regiao(nome) values(region_name1);
        CALL create_jogador(region_name1, player_name1, player_email1);
        SELECT id INTO player_id1 FROM jogador WHERE email = player_email1;
        RAISE NOTICE 'Exercise 2i';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the creation of a chat';
    BEGIN
        CALL iniciarconversa(player_id1, chat_name, chat_id);
            IF (SELECT COUNT(*) FROM conversa WHERE id = chat_id) = 1 THEN
                RAISE NOTICE 'Test 1 succeeded';
            ELSE RAISE EXCEPTION 'Test 1 failed'; END IF;
    END;
        DELETE FROM mensagem WHERE id_conversa = chat_id;
        DELETE FROM participa WHERE id_conversa = chat_id;
        DELETE FROM conversa WHERE id = chat_id;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM regiao WHERE nome = region_name1;
    END;
$$;
------------------------------------------------------------------------------------------------------------------------
-- (j)
CREATE OR REPLACE PROCEDURE test_j()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion71';
        region_name2 varchar := 'TestRegion72';
        player_name1 varchar := 'TestPlayer71';
        player_email1 varchar := 'testplayer71@gmail.com';
        player_id1 INT;
        player_name2 varchar := 'TestPlayer72';
        player_email2 varchar := 'testplayer72@gmail.com';
        player_id2 INT;
        player_name3 varchar := 'TestPlayer73';
        player_email3 varchar := 'testplayer73@gmail.com';
        player_id3 INT;
        chat_name varchar := 'TestChat61';
        chat_id INT :=0;
        text_message varchar := 'O jogador entrou na conversa';
    BEGIN
        insert into regiao(nome) values(region_name1);
        insert into regiao(nome) values(region_name2);
        CALL create_jogador(region_name1, player_name1, player_email1);
        SELECT id INTO player_id1 FROM jogador WHERE email = player_email1;
        CALL create_jogador(region_name2, player_name2, player_email2);
        SELECT id INTO player_id2 FROM jogador WHERE email = player_email2;
        CALL create_jogador(region_name1, player_name3, player_email3);
        SELECT id INTO player_id3 FROM jogador WHERE email = player_email3;
        CALL iniciarconversa(player_id1, chat_name, chat_id);
        RAISE NOTICE 'Exercise 2j';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the addition of a player to a chat';
    BEGIN
        CALL juntarconversa(player_id2, chat_id);
            IF (SELECT COUNT(mensagem.nr_ordem) FROM mensagem WHERE id_conversa = chat_id) >= 2 THEN
                IF (text_message in (Select texto from mensagem where id_conversa = chat_id and id_jogador = player_id2)) THEN
                    RAISE NOTICE 'Test 1 succeeded';
                ELSE RAISE EXCEPTION 'Test 1 failed 1';
                END IF;
            ELSE RAISE EXCEPTION 'Test 1 failed';
            END IF;
    END;
        DELETE FROM mensagem WHERE id_conversa = chat_id;
        DELETE FROM participa WHERE id_conversa = chat_id;
        DELETE FROM conversa WHERE id = chat_id;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM jogador WHERE id = player_id2;
        DELETE FROM jogador WHERE id = player_id3;
        DELETE FROM regiao WHERE nome = region_name1;
        DELETE FROM regiao WHERE nome = region_name2;
    END;
$$;
------------------------------------------------------------------------------------------------------------------------
-- (k)
CREATE OR REPLACE PROCEDURE test_k()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion81';
        region_name2 varchar := 'TestRegion82';
        player_name1 varchar := 'TestPlayer81';
        player_email1 varchar := 'testplayer81@gmail.com';
        player_id1 INT;
        player_name2 varchar := 'TestPlayer82';
        player_email2 varchar := 'testplayer82@gmail.com';
        player_id2 INT;
        player_name3 varchar := 'TestPlayer83';
        player_email3 varchar := 'testplayer83@gmail.com';
        player_id3 INT;
        chat_name varchar := 'TestChat81';
        chat_id INT :=0;
        text_message varchar := 'TestMessage81';
    BEGIN
        insert into regiao(nome) values(region_name1);
        insert into regiao(nome) values(region_name2);
        CALL create_jogador(region_name1, player_name1, player_email1);
        SELECT id INTO player_id1 FROM jogador WHERE email = player_email1;
        CALL create_jogador(region_name2, player_name2, player_email2);
        SELECT id INTO player_id2 FROM jogador WHERE email = player_email2;
        CALL create_jogador(region_name1, player_name3, player_email3);
        SELECT id INTO player_id3 FROM jogador WHERE email = player_email3;
        CALL iniciarconversa(player_id1, chat_name, chat_id);
        CALL juntarconversa(player_id2, chat_id);
        RAISE NOTICE 'Exercise 2k';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the sending of a message';
    BEGIN
        CALL enviarMensagem(player_id1, chat_id, text_message);
            IF (SELECT COUNT(*) FROM mensagem WHERE id_conversa = chat_id) >= 2 THEN
                IF (text_message in (Select texto from mensagem where id_conversa = chat_id and id_jogador = player_id1)) THEN
                    RAISE NOTICE 'Test 1 succeeded';
                ELSE RAISE EXCEPTION 'Test 1 failed';
                END IF;
            ELSE RAISE EXCEPTION 'Test 1 failed';
            END IF;
    END;
        DELETE FROM mensagem WHERE id_conversa = chat_id;
        DELETE FROM participa WHERE id_conversa = chat_id;
        DELETE FROM conversa WHERE id = chat_id;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM jogador WHERE id = player_id2;
        DELETE FROM jogador WHERE id = player_id3;
        DELETE FROM regiao WHERE nome = region_name1;
        DELETE FROM regiao WHERE nome = region_name2;
    END;
$$;
------------------------------------------------------------------------------------------------------------------------
-- (l) -- only works on empty db
CREATE OR REPLACE PROCEDURE test_l()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion91';
        player_name1 varchar := 'TestPlayer91';
        player_name2 varchar := 'TestPlayer92';
        player_name3 varchar := 'TestPlayer93';
        player_email1 varchar := 'testplayer91@gmail.com';
        player_email2 varchar := 'testplayer92@hotmail.com';
        player_email3 varchar := 'testplayer93@iol.pt';
        player_id1 integer;
        player_id2 integer;
        player_id3 integer;
        game_id1 varchar := 'TestGame91';
        game_name1 varchar := 'TestGame91';
        game_url1 varchar := 'https://testgame91.com';
        game_id2 varchar := 'TestGame92';
        game_name2 varchar := 'TestGame92';
        game_url2 varchar := 'https://testgame92.com';
        game_id3 varchar := 'TestGame93';
        game_name3 varchar := 'TestGame93';
        game_url3 varchar := 'https://testgame93.com';
        match_start_date date := '2020-01-01';
        match_end_date date := '2020-01-02';
        match_ended varchar := 'Terminada';
        vista REFCURSOR;
        player_id Int;
        player_status varchar;
        player_email email;
        player_username varchar;
        player_games int;
        player_points Int;
        Player_matches Int;
    BEGIN
        INSERT INTO regiao(nome) VALUES (region_name1);
        INSERT INTO jogador(username, email, nome_regiao) VALUES (player_name1, player_email1, region_name1);
        INSERT INTO jogador(username, email, nome_regiao) VALUES (player_name2, player_email2, region_name1);
        INSERT INTO jogador(username, email, nome_regiao) VALUES (player_name3, player_email3, region_name1);
        SELECT id INTO player_id1 FROM jogador WHERE username = player_name1;
        SELECT id INTO player_id2 FROM jogador WHERE username = player_name2;
        SELECT id INTO player_id3 FROM jogador WHERE username = player_name3;
        INSERT INTO jogo(id, nome, url) VALUES (game_id1, game_name1, game_url1);
        INSERT INTO jogo(id, nome, url) VALUES (game_id2, game_name2, game_url2);
        INSERT INTO jogo(id, nome, url) VALUES (game_id3, game_name3, game_url3);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id3, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, game_id3, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, game_id3, match_start_date, match_end_date, region_name1);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id1, 1, 1, 1);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id1, 2, 1, 10);
        INSERT INTO partida_multijogador(id_jogo, nr_partida, estado, pontuacao) VALUES (game_id1, 3, match_ended, 100);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id2, 1, 1, 1000);
        INSERT INTO partida_multijogador(id_jogo, nr_partida, estado, pontuacao) VALUES (game_id2, 2, match_ended, 10000);
        INSERT INTO partida_multijogador(id_jogo, nr_partida, estado, pontuacao) VALUES (game_id2, 3, match_ended, 100000);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id3, 1, 1, 1000000);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id3, 2, 1, 10000000);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id3, 3, 1, 100000000);
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id1); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id2); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id3); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 3, game_id1); --multi
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 3, game_id2); --multi
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id2, 2, game_id1); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id2, 2, game_id2); --multi
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id2, 2, game_id3); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id2, 3, game_id1); --multi
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id2, 3, game_id2); --multi
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id3, 3, game_id1); --multi
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id3, 3, game_id2); --multi
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id3, 3, game_id3); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id3, 2, game_id2); --multi
        RAISE NOTICE 'Exercise 2l';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the view jogadorTotalInfo';
   BEGIN -- Test 1: viewing jogadorTotalInfo raw
        OPEN vista FOR SELECT * FROM jogadortotalinfo;
            FOR i IN 0..2 LOOP
                FETCH NEXT FROM vista INTO player_id, player_status, player_email, player_username, player_games, Player_matches, player_points;
                ASSERT player_id = player_id1 OR player_id = player_id2 OR player_id = player_id3, 'Test 1 failed: player_id not in test data';
                ASSERT player_status = 'Ativo' OR player_status = 'Ativo' OR player_status = 'Ativo', 'Test 1 failed: player_status not in test data';
                ASSERT player_email = player_email1 OR player_email = player_email2 OR player_email = player_email3, 'Test 1 failed: player_email not in test data';
                ASSERT player_username = player_name1 OR player_username = player_name2 OR player_username = player_name3, 'Test 1 failed: player_username not in test data';
                ASSERT player_games = 3 OR player_games = 3 OR player_games = 3, 'Test 1 failed: player_games not in test data';
                ASSERT Player_matches = 5 OR Player_matches = 4 OR Player_matches = 5, 'Test 1 failed: Player_matches not in test data';
                ASSERT player_points = 1101101 OR player_points = 10110110 OR player_points = 100110100, 'Test 1 failed: player_points not in test data';
            END LOOP;
        CLOSE vista;
        RAISE NOTICE 'Test 1 passed';
    EXCEPTION
        WHEN OTHERS THEN
            RAISE NOTICE 'Test 1 failed: %', SQLERRM;
   END;
        DELETE FROM joga WHERE id_jogador = player_id1;
        DELETE FROM joga WHERE id_jogador = player_id2;
        DELETE FROM joga WHERE id_jogador = player_id3;
        DELETE FROM partida_normal WHERE id_jogo = game_id1;
        DELETE FROM partida_normal WHERE id_jogo = game_id2;
        DELETE FROM partida_normal WHERE id_jogo = game_id3;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id1;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id2;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id3;
        DELETE FROM partida WHERE id_jogo = game_id1;
        DELETE FROM partida WHERE id_jogo = game_id2;
        DELETE FROM partida WHERE id_jogo = game_id3;
        DELETE FROM jogo WHERE id = game_id1;
        DELETE FROM jogo WHERE id = game_id2;
        DELETE FROM jogo WHERE id = game_id3;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM jogador WHERE id = player_id2;
        DELETE FROM jogador WHERE id = player_id3;
        DELETE FROM regiao WHERE nome = region_name1;
        RAISE NOTICE 'Test data deleted';
    END;
$$;
------------------------------------------------------------------------------------------------------------------------
-- (m)
CREATE OR REPLACE PROCEDURE test_m()
    LANGUAGE plpgsql
AS
$$
DECLARE
    region_name1 varchar := 'TestRegion102';
    player_name1 varchar := 'TestPlayer104';
    player_id1 integer;
    player_email1 varchar := 'testplayer104@gmail.com';
    game_id1 ALPHANUMERIC := 'GAME1234';
    game_name1 varchar := 'TestGame102';
    game_url1 varchar := 'http://testgame102.example.com';
    partida_nr1 INT := 100;
    cracha_name1 VARCHAR(50) := 'TestCracha1';
    match_state varchar := 'Em curso';
    match_ended varchar := 'Terminada';
BEGIN
    -- Prepare test data
    DELETE FROM regiao WHERE nome = region_name1;
    INSERT INTO regiao (nome) VALUES (region_name1);

    CALL create_jogador(region_name1, player_name1, player_email1);
    SELECT jogador.id INTO player_id1 FROM jogador WHERE email = player_email1;

    DELETE FROM jogo WHERE id = game_id1;
    INSERT INTO jogo (id, nome, url) VALUES (game_id1, game_name1, game_url1);

    DELETE FROM partida WHERE nr = partida_nr1;
    INSERT INTO partida (nr, id_jogo, data_inicio, nome_regiao) VALUES (partida_nr1, game_id1, CURRENT_DATE, region_name1);

    DELETE FROM joga WHERE nr_partida = partida_nr1;
    INSERT INTO joga (id_jogador, nr_partida, id_jogo) VALUES (player_id1, partida_nr1, game_id1);

    DELETE FROM partida_multijogador WHERE nr_partida = partida_nr1 AND id_jogo = game_id1;
    INSERT INTO partida_multijogador (nr_partida, id_jogo, estado) VALUES (partida_nr1, game_id1, match_state);

    DELETE FROM cracha WHERE nome = cracha_name1;
    INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES (cracha_name1, game_id1, 'http://test-cracha.example.com', 50);

    -- Tests
    RAISE NOTICE 'Testing atribuirCrachas function';

    -- Test 1: Award badge when total points >= badge points limit
    UPDATE partida_multijogador
    SET estado = match_ended, pontuacao = 60
    WHERE nr_partida = partida_nr1 AND id_jogo = game_id1;

    IF (has_badge(player_id1, cracha_name1, game_id1)) THEN
        RAISE NOTICE 'Test 1 succeeded';
    ELSE
        RAISE EXCEPTION 'Test 1 failed';
    END IF;

    -- Test 2: Do not award badge when total points < badge points limit
    UPDATE partida_multijogador
    SET estado = match_ended, pontuacao = 40
    WHERE nr_partida = partida_nr1 AND id_jogo = game_id1;

    IF (NOT has_badge(player_id1, cracha_name1, game_id1)) THEN
        RAISE NOTICE 'Test 2 succeeded';
    ELSE
        RAISE EXCEPTION 'Test 2 failed';
    END IF;
    -- Clean up test data
    DELETE FROM ganha WHERE id_jogador = player_id1;
    DELETE FROM joga WHERE id_jogador = player_id1;
    DELETE FROM partida_normal WHERE nr_partida = partida_nr1;
    DELETE FROM partida WHERE nr = partida_nr1;
    DELETE FROM cracha WHERE nome = cracha_name1;
    DELETE FROM jogo WHERE id = game_id1;
    DELETE FROM jogador WHERE id = player_id1;
    DELETE FROM regiao WHERE nome = region_name1;

END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (n)
CREATE OR REPLACE PROCEDURE test_n()
    LANGUAGE plpgsql
AS
$$
    DECLARE
        region_name1 varchar := 'TestRegion111';
        player_name1 varchar := 'TestPlayer111';
        player_email1 varchar := 'testplayer111@gmail.com';
        player_id1 integer;
        game_id1 varchar := 'TestGam111';
        game_name1 varchar := 'TestGam111';
        game_url1 varchar := 'https://testgame111.com';
        game_id2 varchar := 'TestGam112';
        game_name2 varchar := 'TestGam112';
        game_url2 varchar := 'https://testgame112.com';
        game_id3 varchar := 'TestGam113';
        game_name3 varchar := 'TestGam113';
        game_url3 varchar := 'https://testgame113.com';
        match_start_date date := '2020-01-01';
        match_end_date date := '2020-01-02';
        match_ended varchar := 'Terminada';
        player_id Int;
        player_status varchar;
        player_email email;
        player_username varchar;
        player_games int;
        player_points Int;
        Player_matches Int;
    BEGIN
        INSERT INTO regiao(nome) VALUES (region_name1);
        INSERT INTO jogador(username, email, nome_regiao) VALUES (player_name1, player_email1, region_name1);
        SELECT id INTO player_id1 FROM jogador WHERE username = player_name1;
        INSERT INTO jogo(id, nome, url) VALUES (game_id1, game_name1, game_url1);
        INSERT INTO jogo(id, nome, url) VALUES (game_id2, game_name2, game_url2);
        INSERT INTO jogo(id, nome, url) VALUES (game_id3, game_name3, game_url3);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, game_id1, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, game_id2, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, game_id3, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, game_id3, match_start_date, match_end_date, region_name1);
        INSERT INTO partida(nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, game_id3, match_start_date, match_end_date, region_name1);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id1, 1, 1, 1);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id1, 2, 1, 10);
        INSERT INTO partida_multijogador(id_jogo, nr_partida, estado, pontuacao) VALUES (game_id1, 3, match_ended, 100);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id2, 1, 1, 1000);
        INSERT INTO partida_multijogador(id_jogo, nr_partida, estado, pontuacao) VALUES (game_id2, 2, match_ended, 10000);
        INSERT INTO partida_multijogador(id_jogo, nr_partida, estado, pontuacao) VALUES (game_id2, 3, match_ended, 100000);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id3, 1, 1, 1000000);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id3, 2, 1, 10000000);
        INSERT INTO partida_normal(id_jogo, nr_partida, dificuldade, pontuacao) VALUES (game_id3, 3, 1, 100000000);
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id1); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id2); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 1, game_id3); --normal
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 3, game_id1); --multi
        INSERT INTO joga(id_jogador, nr_partida, id_jogo) VALUES (player_id1, 3, game_id2); --multi
        RAISE NOTICE 'Exercise 2n';
        RAISE NOTICE 'Test data created';
        RAISE NOTICE 'Testing the view jogadorTotalInfo';
    BEGIN -- Test 1: Deleting over the player 1
        SELECT * FROM jogadortotalinfo into player_id, player_status, player_email, player_username, player_games, Player_matches, player_points;
        RAISE NOTICE 'PLAYER VIEW %, PLAYER TABLE %', player_username, player_name1;
        ASSERT player_id = player_id1, 'Test 1 failed: player_id not in test data';
        ASSERT player_status = 'Ativo', 'Test 1 failed: player_status not in test data';
        ASSERT player_email = player_email1, 'Test 1 failed: player_email not in test data';
        ASSERT player_username = player_name1 , 'Test 1 failed: player_username not in test data';
        ASSERT player_games = 3, 'Test 1 failed: player_games not in test data';
        ASSERT Player_matches = 5, 'Test 1 failed: Player_matches not in test data';
        ASSERT player_points = 1101101, 'Test 1 failed: player_points not in test data';
        DELETE FROM jogadorTotalInfo WHERE jogadortotalinfo.username = player_name1;
        SELECT jogador.estado into player_status from jogador WHERE jogador.username = player_name1;
        IF (player_status = 'Ativo') THEN
            RAISE NOTICE 'Test 1 failed: player not deleted';
        ELSIF (player_status = 'Banido') THEN
            RAISE NOTICE 'Test 1 succeeded';
        END IF;
        EXCEPTION
            WHEN OTHERS THEN
                RAISE NOTICE 'Test 1 failed: %', SQLERRM;
    END;
        DELETE FROM joga WHERE id_jogador = player_id1;
        DELETE FROM partida_normal WHERE id_jogo = game_id1;
        DELETE FROM partida_normal WHERE id_jogo = game_id2;
        DELETE FROM partida_normal WHERE id_jogo = game_id3;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id1;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id2;
        DELETE FROM partida_multijogador WHERE id_jogo = game_id3;
        DELETE FROM partida WHERE id_jogo = game_id1;
        DELETE FROM partida WHERE id_jogo = game_id2;
        DELETE FROM partida WHERE id_jogo = game_id3;
        DELETE FROM jogo WHERE id = game_id1;
        DELETE FROM jogo WHERE id = game_id2;
        DELETE FROM jogo WHERE id = game_id3;
        DELETE FROM jogador WHERE id = player_id1;
        DELETE FROM regiao WHERE nome = region_name1;
        RAISE NOTICE 'Test data deleted';
    END;
$$;

CALL test_d1();
CALL test_d2();
CALL test_e();
CALL test_f();
CALL test_g();
CALL test_h();
CALL test_i();
CALL test_j();
CALL test_k();
CALL test_l();
CALL test_m();
CALL test_n();
