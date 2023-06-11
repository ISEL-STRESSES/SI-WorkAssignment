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
        UPDATE jogador_estatistica SET nr_jogos = totalPartidasJogador(NEW.id_jogador) WHERE jogador_estatistica.id_jogador = NEW.id_jogador;
        RETURN NEW;
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
        pontos Int;
    BEGIN
        SELECT joga.id_jogador INTO jogador_id FROM joga WHERE joga.nr_partida = NEW.nr_partida;
        SELECT * Into pontos From totalPontosJogador(jogador_id);
        UPDATE jogador_estatistica SET total_pontos = pontos WHERE jogador_estatistica.id_jogador = jogador_id;
        RETURN NEW;
    END;
$$;

-- This trigger is called when a player's statistics are updated and it updates the total number of points the player
-- has obtained.
CREATE OR REPLACE TRIGGER update_total_pontos_jogador
AFTER INSERT OR DELETE ON joga
EXECUTE FUNCTION updateTotalPontosJogador();


-- This function is called when a player's statistics are updated and it updates the total number of games the player
-- has played.
CREATE OR REPLACE FUNCTION updateTotalJogosJogador()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    BEGIN
        UPDATE jogador_estatistica SET nr_jogos = totalJogosJogador(NEW.id_jogador) WHERE jogador_estatistica.id_jogador = NEW.id_jogador;
        RETURN NEW;
    END;
$$;

-- This trigger is called when a player's statistics are updated and it updates the total number of games the player
-- has played.
CREATE OR REPLACE TRIGGER update_total_jogos_jogador
AFTER INSERT OR DELETE ON joga
EXECUTE FUNCTION updateTotalJogosJogador();