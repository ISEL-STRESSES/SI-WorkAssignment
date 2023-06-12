BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- Integrity Restrictions
-- Model Restrictions
-- 1. The partida must be played by jogadores of the same regiao
DROP TRIGGER checkJogadorPartidaRegiao ON joga;
DROP FUNCTION checkJogadorPartidaRegiao();

------------------------------------------------------------------------------------------------------------------------
-- 2. The mensagem must be sent by a jogador of the conversa
DROP TRIGGER checkJogadorMensagemConversa ON mensagem;
DROP FUNCTION checkJogadorMensagemConversa();

COMMIT TRANSACTION;
