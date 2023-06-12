BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- Utilities
-- 1. This function checks if a player has a badge.
-- Not a requirement but useful for testing.
CREATE OR REPLACE FUNCTION has_badge(player_id INTEGER, badge_name VARCHAR, game_id ALPHANUMERIC)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
DECLARE
    badge_exists BOOLEAN;
BEGIN
    SELECT EXISTS (SELECT 1 FROM ganha WHERE id_jogador = player_id AND nome_cracha = badge_name AND id_jogo = game_id)
    INTO badge_exists;

    RETURN badge_exists;
END;
$$;

COMMIT TRANSACTION;
