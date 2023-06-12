BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- Encapsulation
-- 1. Encapsulation of the function to start a chat.
-- This function is used to start a chat and return the chat id instead of the iniciarConversa procedure.
CREATE OR REPLACE FUNCTION startChat(jogador_id INT, nome_conversa VARCHAR(50))
    RETURNS INT
    LANGUAGE plpgsql
AS
$$
    DECLARE
        chat_id INT DEFAULT -1;
    BEGIN
        call iniciarconversa(jogador_id, nome_conversa, chat_id);
        RETURN chat_id;
    END;
$$;

COMMIT TRANSACTION;
