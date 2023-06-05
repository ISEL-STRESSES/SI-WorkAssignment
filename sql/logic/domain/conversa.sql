
CREATE OR REPLACE FUNCTION startChat(jogador_id INT, nome_conversa VARCHAR(50))
    RETURNS INT
    LANGUAGE plpgsql
AS
$$
    DECLARE
        chat_id INT;
    BEGIN
        call iniciarconversa(jogador_id, nome_conversa, chat_id);
        RETURN chat_id;
    END;
$$;
