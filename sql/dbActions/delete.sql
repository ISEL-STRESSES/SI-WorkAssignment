BEGIN TRANSACTION;
-- Model
-- Create domain
DROP DOMAIN IF EXISTS ALPHANUMERIC CASCADE;
DROP DOMAIN IF EXISTS email CASCADE;
DROP DOMAIN IF EXISTS url CASCADE;

-- Create table
DROP TABLE IF EXISTS amigo;
DROP TABLE IF EXISTS participa;
DROP TABLE IF EXISTS ganha;
DROP TABLE IF EXISTS joga;
DROP TABLE IF EXISTS compra;
DROP TABLE IF EXISTS mensagem;
DROP TABLE IF EXISTS cracha;
DROP TABLE IF EXISTS partida_multijogador;
DROP TABLE IF EXISTS partida_normal;
DROP TABLE IF EXISTS partida;
DROP TABLE IF EXISTS jogo_estatistica;
DROP TABLE IF EXISTS jogador_estatistica;
DROP TABLE IF EXISTS conversa;
DROP TABLE IF EXISTS jogo;
DROP TABLE IF EXISTS jogador;
DROP TABLE IF EXISTS regiao;

COMMIT;

------------------------------------------------------------------------------------------------------------------------
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- RESTRICOES
-- Model Restrictions
-- 1. The partida must be played by jogadores of the same regiao
DROP FUNCTION IF EXISTS checkJogadorPartidaRegiao() CASCADE;
DROP TRIGGER IF EXISTS checkJogadorPartidaRegiao ON partida;

------------------------------------------------------------------------------------------------------------------------
-- 2. The mensagem must be sent by a jogador of the conversa
DROP FUNCTION IF EXISTS checkJogadorMensagemConversa() CASCADE;
DROP TRIGGER IF EXISTS checkJogadorMensagemConversa ON mensagem;

COMMIT;

------------------------------------------------------------------------------------------------------------------------
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- Functionalities
-- Jogo
-- 1. Update the nr_partidas of the jogo_estatistica when a new partida is added or removed
DROP FUNCTION IF EXISTS updateNrPartidas() CASCADE;
DROP TRIGGER IF EXISTS updateNrPartidasTrigger ON partida;

------------------------------------------------------------------------------------------------------------------------
-- 2. Update the nr_jogadores of the jogo_estatistica when a new jogador is added or removed
DROP FUNCTION IF EXISTS updateNrJogadores() CASCADE;
DROP TRIGGER IF EXISTS updateNrJogadoresTrigger ON jogador;

------------------------------------------------------------------------------------------------------------------------
-- 3. Update the total_pontos of the jogo_estatistica when a new partida is added or removed
DROP FUNCTION IF EXISTS updatePontuacaoTotal() CASCADE;
DROP TRIGGER IF EXISTS updatePontuacaoTotalTrigger ON partida_multijogador;
DROP TRIGGER IF EXISTS updatePontuacaoTotalTrigger ON partida_normal;

COMMIT TRANSACTION;
------------------------------------------------------------------------------------------------------------------------
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- EXERCICIOS
--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username e regiao, desativar e banir o
-- jogador;
DROP PROCEDURE IF EXISTS createJogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);
DROP PROCEDURE IF EXISTS createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);

DROP PROCEDURE IF EXISTS updateEstadoJogador(id_jogador INT, new_estado VARCHAR(10));
DROP PROCEDURE IF EXISTS updateEstadoJogadorTransaction(id_jogador INT, new_estado VARCHAR(10));

------------------------------------------------------------------------------------------------------------------------
-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.
DROP FUNCTION IF EXISTS totalPontosJogador(jogador_id INT) CASCADE;
DROP PROCEDURE IF EXISTS totalPontosJogadorTransaction(jogador_id INT);

------------------------------------------------------------------------------------------------------------------------
-- (f) Criar a função totalJogosJogador que recebe como parâmetro o identificador de um jogador e devolve o número total
-- de jogos diferentes nos quais o jogador participou.
DROP FUNCTION IF EXISTS totalJogosJogador(jogador_id INT);
DROP PROCEDURE IF EXISTS totalJogosJogadorTransaction(jogador_id INT);

------------------------------------------------------------------------------------------------------------------------
-- (g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo e devolve uma tabela com
-- duas colunas (identificador de jogador, total de pontos) em que cada linha contém o identificador de um jogador e o
-- total de pontos que esse jogador teve nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo.
DROP FUNCTION IF EXISTS PontosJogoPorJogador(jogo_id ALPHANUMERIC);
DROP PROCEDURE IF EXISTS PontosJogoPorJogadorTransaction(jogo_id ALPHANUMERIC);

------------------------------------------------------------------------------------------------------------------------
-- (h) Criar o procedimento armazenado associarCrachá que recebe como parâmetros o identificador de um jogador, a
-- referência de um jogo e o nome de um crachá desse jogo e atribui o crachá a esse jogador se ele reunir as condições
-- para o obter.
DROP PROCEDURE IF EXISTS associarCracha(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50));
DROP PROCEDURE IF EXISTS associarCrachaTransaction(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50));
------------------------------------------------------------------------------------------------------------------------
-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.
DROP PROCEDURE IF EXISTS iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT);
DROP PROCEDURE IF EXISTS iniciarConversaTransaction(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT);

------------------------------------------------------------------------------------------------------------------------
-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.
DROP PROCEDURE IF EXISTS juntarConversa(jogador_id INT, conversa_id INT);
DROP PROCEDURE IF EXISTS juntarConversaTransaction(jogador_id INT, conversa_id INT);

------------------------------------------------------------------------------------------------------------------------
-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.
DROP PROCEDURE IF EXISTS enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50));
DROP PROCEDURE IF EXISTS enviarMensagemTransaction(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50));

------------------------------------------------------------------------------------------------------------------------
-- (l) Criar a vista jogadorTotalInfo que permita aceder à informação sobre identificador, estado, email, username,
-- número total de jogos em que participou, número total de partidas em que participou e número total de pontos que já
-- obteve de todos os jogadores cujo estado seja diferente de “Banido”. Deve implementar na vista os cálculos sem aceder
-- às tabelas de estatísticas.
DROP FUNCTION IF EXISTS totalPartidasJogador(jogador_id INT);
DROP PROCEDURE IF EXISTS totalPartidasJogadorTransaction(jogador_id INT);
DROP VIEW IF EXISTS jogadorTotalInfo;

------------------------------------------------------------------------------------------------------------------------
-- (m) Criar os mecanismos necessários para que, de forma automática, quando uma partida termina, se proceda à
-- atribuição de crachás do jogo a que ela pertence.
DROP FUNCTION IF EXISTS atribuirCrachas() CASCADE;
DROP TRIGGER IF EXISTS atribuirCrachas ON partida;

------------------------------------------------------------------------------------------------------------------------
-- (n) Criar os mecanismos necessários para que a execução da instrução DELETE sobre a vista jogadorTotalInfo permita
-- colocar os jogadores envolvidos no estado “Banido”.
DROP FUNCTION IF EXISTS banirJogador();
DROP TRIGGER IF EXISTS banirJogador ON jogadorTotalInfo;

COMMIT TRANSACTION;