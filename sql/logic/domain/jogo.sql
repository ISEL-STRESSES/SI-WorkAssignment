------------------------------------------------------------------------------------------------------------------------
-- 3. Update the nr_partidas of the jogo_estatistica when a new joga entry is added or removed

CREATE OR REPLACE FUNCTION updateNrPartidas()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
    BEGIN
        IF (TG_OP = 'INSERT') THEN
            UPDATE jogo_estatistica
                SET nr_partidas = nr_partidas + 1
                WHERE id_jogo = NEW.id_jogo;
            END IF;
        IF (TG_OP = 'DELETE') THEN
            UPDATE jogo_estatistica
                SET nr_partidas = nr_partidas - 1
                WHERE id_jogo = OLD.id_jogo;
        end if;
        RETURN NEW;
    END;
$$;

CREATE OR REPLACE TRIGGER updateNrPartidasTrigger
    AFTER INSERT OR DELETE ON joga
    FOR EACH ROW
    EXECUTE FUNCTION updateNrPartidas();

------------------------------------------------------------------------------------------------------------------------
-- 4. Update the nr_jogadores of the jogo_estatistica when a new jogador is added or removed

CREATE OR REPLACE FUNCTION updateNrJogadores()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        UPDATE jogo_estatistica
        SET nr_jogadores = nr_jogadores + 1
        WHERE id_jogo = NEW.id_jogo;
    ELSIF TG_OP = 'DELETE' THEN
        UPDATE jogo_estatistica
        SET nr_jogadores = nr_jogadores - 1
        WHERE id_jogo = OLD.id_jogo;
    END IF;
    RETURN NULL;
END;
$$;

CREATE OR REPLACE TRIGGER updateNrJogadoresTrigger
    AFTER INSERT OR DELETE ON joga
    FOR EACH ROW
    EXECUTE FUNCTION updateNrJogadores();

------------------------------------------------------------------------------------------------------------------------
-- 5. Update the total_pontos of the jogo_estatistica when a new partida is added or removed

CREATE OR REPLACE FUNCTION updatePontuacaoTotal()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
    DECLARE
        pontos INT;
    BEGIN
        SELECT SUM(COALESCE(pontuacao, 0)) INTO pontos FROM joga WHERE joga.id_jogo = NEW.id_jogo;
        -- not atomic because of multiplayer and single player games
        UPDATE jogo_estatistica
        SET total_pontos = pontos
        WHERE id_jogo = NEW.id_jogo;
        RETURN NEW;
    END;
$$;

CREATE OR REPLACE TRIGGER updatePontuacaoTotalTrigger
    AFTER INSERT OR UPDATE OR DELETE ON joga
    FOR EACH ROW
    EXECUTE FUNCTION updatePontuacaoTotal();