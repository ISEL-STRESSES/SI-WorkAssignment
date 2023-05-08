------------------------------------------------------------------------------------------------------------------------
-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.

-- This procedure starts a chat given the player ID and the name of the chat. The player is automatically associated
-- with the chat and a message is created informing that the player created the chat. The procedure returns the chat
-- ID in an output parameter.
--
-- Example usage:
-- CALL iniciarConversa(1, 'Chat 1', conversa_id);
CREATE OR REPLACE PROCEDURE iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        default_conversa_nr_ordem INT := 1;
        nome_conversa VARCHAR(50);
    BEGIN
        SELECT nome INTO nome_conversa FROM conversa WHERE conversa.nome == nome_conversa;
        IF (nome_conversa == NULL) THEN
            INSERT INTO conversa VALUES (nome_conversa);
            SELECT id INTO conversa_id FROM conversa WHERE conversa.nome == nome_conversa;
            INSERT INTO participa VALUES (jogador_id, conversa_id);
            INSERT INTO mensagem VALUES (default_conversa_nr_ordem, 'O jogador criou a conversa', now(),
                                         jogador_id, conversa_id);
        END IF;
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.

-- This procedure joins a player to a chat given the player ID and the chat ID. A message is created informing that
-- the player joined the chat.
--
-- Example usage:
-- CALL juntarConversa(1, 1);
CREATE OR REPLACE PROCEDURE juntarConversa(jogador_id INT, conversa_id INT)
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nr INT;
    BEGIN
        SELECT COUNT(mensagem.nr_ordem) INTO nr FROM mensagem WHERE id_jogador == jogador_id AND mensagem.id_conversa == conversa_id;
        INSERT INTO participa VALUES (jogador_id, conversa_id);
        INSERT INTO mensagem VALUES (nr, 'O jogador entrou na conversa', now(), jogador_id, conversa_id);
    END;
$$;

------------------------------------------------------------------------------------------------------------------------
-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.

-- This procedure sends a message to a chat given the player ID, the chat ID and the message text. The message is
-- associated with the player and the chat.
--
-- Example usage:
-- CALL enviarMensagem(1, 1, 'Mensagem 1');
CREATE OR REPLACE PROCEDURE enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50))
    LANGUAGE plpgsql
AS
$$
    DECLARE
        nr INT;
    BEGIN
        --checks
        IF (jogador_id NOT IN (SELECT jogador.id FROM jogador)) THEN
            RAISE EXCEPTION 'O jogador com o id (%) não existe', jogador_id;
        END IF;
        IF (conversa_id NOT IN (SELECT conversa.id FROM conversa)) THEN
            RAISE EXCEPTION 'A conversa com o id (%) não existe', conversa_id;
        END IF;
        IF (mensagem_texto == NULL) THEN
            RAISE EXCEPTION 'A mensagem (%) não tem conteúdo', mensagem_texto;
        END IF;
        --expected
        IF (jogador_id NOT IN (SELECT id_jogador FROM participa WHERE participa.id_conversa == conversa_id)) THEN
            INSERT INTO participa VALUES (jogador_id, conversa_id);
        END IF;
        SELECT COUNT(mensagem.nr_ordem) INTO nr FROM mensagem WHERE id_jogador == jogador_id AND mensagem.id_conversa == conversa_id;
        INSERT INTO mensagem VALUES (nr, mensagem_texto, now(), jogador_id, conversa_id);
    END;
$$;
