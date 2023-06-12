BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- Model functionalities

-- 1. Automation of the player stats calculation.
-- This function is called everytime that a player plays a match and the points are updated.
-- It updates the total number of games the player has played, total of matches and total of points that the player has
-- obtained.
CREATE OR REPLACE FUNCTION updatePlayerStats()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$$
    BEGIN
        UPDATE jogador_estatistica SET nr_jogos = totalJogosJogador(OLD.id_jogador),
                                       total_pontos = totalPontosJogador(OLD.id_jogador),
                                       nr_partidas = totalpartidasjogador(OLD.id_Jogador)
                                   WHERE jogador_estatistica.id_jogador = OLD.id_jogador;
        RETURN NEW;
    END;
$$;

-- This trigger is called for recalculating a player's statistics.
CREATE OR REPLACE TRIGGER updatePlayerStats
AFTER UPDATE OR DELETE ON joga
EXECUTE FUNCTION updatePlayerStats();

------------------------------------------------------------------------------------------------------------------------
-- 2. Automation of the game stats calculation.
-- This function is called everytime that the game has been played in a match and the points are updated.
-- It updates the total number of matches the game has been played, the amount of players that have played the game and
-- total of points was obtained.
CREATE OR REPLACE FUNCTION updateGameStats()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    pontos INT;
    nr_matches INT;
    nr_players INT;
BEGIN
    SELECT SUM(pontuacao) INTO pontos FROM joga WHERE joga.id_jogo = OLD.id_jogo;
    SELECT COUNT(*) INTO nr_matches FROM joga WHERE joga.id_jogo = OLD.id_jogo;
    SELECT COUNT(*) INTO nr_players FROM joga WHERE joga.id_jogo = OLD.id_jogo;
    -- not atomic because of multiplayer and single player games
    UPDATE jogo_estatistica
    SET total_pontos = pontos,
        nr_partidas = nr_matches,
        nr_jogadores = nr_players
    WHERE id_jogo = OLD.id_jogo;
    RETURN NEW;
END;
$$;

-- This trigger is called for recalculating a game's statistics.
CREATE OR REPLACE TRIGGER updateGameStats
    AFTER UPDATE OR DELETE ON joga
EXECUTE FUNCTION updateGameStats();

COMMIT TRANSACTION;
