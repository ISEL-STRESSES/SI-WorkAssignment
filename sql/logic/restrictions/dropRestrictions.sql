
-- Partida & Jogador restriction on Regiao
DROP FUNCTION IF EXISTS checkJogadorPartidaRegiao() CASCADE;

DROP TRIGGER IF EXISTS checkJogadorPartidaRegiao ON joga;

-- Mensagem & Jogador restriction on Conversa
DROP FUNCTION IF EXISTS checkJogadorMensagemConversa() CASCADE;

DROP TRIGGER IF EXISTS checkJogadorMensagemConversa ON mensagem;
