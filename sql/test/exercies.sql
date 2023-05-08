-- 2.(o) Criar um script autónomo com os testes das funcionalidades de 2d a 2n para cenários normais e de erro. Este
-- script, ao ser executado, deve listar, para cada teste, o seu nome e indicação se ele correu ou não com sucesso;
-- (d.1)
do $$
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
        RAISE NOTICE 'Exercice 2d.1';
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
$$ language plpgsql;

-- (d.2)
do $$
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
        RAISE NOTICE 'Exercice 2d.2';
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

$$ language plpgsql;
------------------------------------------------------------------------------------------------------------------------
-- (e)
do $$
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
        RAISE NOTICE 'Exercice 2e';
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
$$ language plpgsql;
------------------------------------------------------------------------------------------------------------------------
-- (f)
do $$
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
        RAISE NOTICE 'Exercice 2f';
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
$$ language plpgsql;
------------------------------------------------------------------------------------------------------------------------
-- (g)
DO $$
    DECLARE
        tabela REFCURSOR;
        jogador1_id INT := 0;
        jogador2_id INT := 0;
        player INT;
        pontos int;
    BEGIN
        insert into regiao values('NA');
        CALL create_jogador('NA','John Kurdo', 'johna@gmail.com');
        SELECT jogador.id INTO jogador1_id FROM jogador WHERE email = 'johna@gmail.com';

        CALL create_jogador('NA','Abigail Kurdo', 'abigail@hotmail.com');
        SELECT jogador.id INTO jogador2_id FROM jogador WHERE email = 'abigail@hotmail.com';

        IF jogador1_id = 0 OR jogador2_id = 0 THEN
            RAISE NOTICE 'Teste G: Obter total de pontos num jogo por jogador: Resultado FAIL';
        END IF;

        INSERT INTO JOGO VALUES('jdfSFJfdaf', 'Os Cavaleiros', 'https://oscavalos.com/index');
        INSERT INTO PARTIDA VALUES(1, 'jdfSFJfdaf', '26 April 23', '27 April 23', 'NA');
        INSERT INTO PARTIDA VALUES(2, 'jdfSFJfdaf',  '26 April 23', '27 April 23', 'NA');
        INSERT INTO PARTIDA VALUES(3, 'jdfSFJfdaf',  '26 April 23', '27 April 23', 'NA');
        INSERT INTO PARTIDA VALUES(4, 'jdfSFJfdaf',  '26 April 23', '27 April 23', 'NA');
        INSERT INTO PARTIDA VALUES(5, 'jdfSFJfdaf',  '26 April 23', '27 April 23', 'NA');
        INSERT INTO PARTIDA VALUES(6, 'jdfSFJfdaf', '26 April 23', '27 April 23', 'NA');

        INSERT INTO PARTIDA_NORMAL VALUES('jdfSFJfdaf', 1, 3, 50);
        INSERT INTO PARTIDA_NORMAL VALUES('jdfSFJfdaf', 3, 3, 125);
        INSERT INTO PARTIDA_MULTIJOGADOR VALUES ('jdfSFJfdaf',5,'Terminada', 25);

        INSERT INTO PARTIDA_NORMAL VALUES('jdfSFJfdaf',2,  3, 50);
        INSERT INTO PARTIDA_MULTIJOGADOR VALUES('jdfSFJfdaf',4,'Terminada', 10);
        INSERT INTO PARTIDA_MULTIJOGADOR VALUES('jdfSFJfdaf',6, 'Terminada',25);

        insert into joga(id_jogador, nr_partida, id_jogo) values(jogador1_id, 1,'jdfSFJfdaf');
        insert into joga(id_jogador, nr_partida, id_jogo) values(jogador1_id, 3,'jdfSFJfdaf');
        insert into joga(id_jogador, nr_partida, id_jogo) values(jogador1_id, 5,'jdfSFJfdaf');
        insert into joga(id_jogador, nr_partida, id_jogo) values(jogador2_id, 2,'jdfSFJfdaf');
        insert into joga(id_jogador, nr_partida, id_jogo) values(jogador2_id, 4,'jdfSFJfdaf');
        insert into joga(id_jogador, nr_partida, id_jogo) values(jogador2_id, 6,'jdfSFJfdaf');
        OPEN tabela FOR SELECT * FROM pontosJogoPorJogador('jdfSFJfdaf');
        FOR i IN 0..1 LOOP
            FETCH NEXT FROM tabela INTO player, pontos;
            RAISE NOTICE 'JOGADOR: %', player;
            RAISE NOTICE 'PONTOS: %', pontos ;

            IF jogador1_id = player AND pontos = 200 OR jogador2_id = player AND pontos = 85 THEN
                RAISE NOTICE 'Teste G: Obter total de pontos num jogo por jogador: Resultado OK';
            ELSE RAISE NOTICE 'Teste G: Obter total de pontos num jogo por jogador: Resultado FAIL';
            END IF;
        END LOOP;
    END;
$$ LANGUAGE plpgsql;
-- (h)
-- (i)
-- (j)
-- (k)
-- (l)
-- (m)
-- (n)
