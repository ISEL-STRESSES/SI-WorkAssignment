BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- EXERCICIOS
------------------------------------------------------------------------------------------------------------------------
-- (n) Criar os mecanismos necessários para que a execução da instrução DELETE sobre a vista jogadorTotalInfo permita
-- colocar os jogadores envolvidos no estado “Banido”.
DROP TRIGGER banirJogadorTrigger ON jogadorTotalInfo;
DROP FUNCTION banirJogador();

------------------------------------------------------------------------------------------------------------------------
-- (m) Criar os mecanismos necessários para que, de forma automática, quando uma partida termina, se proceda à
-- atribuição de crachás do jogo a que ela pertence.
DROP TRIGGER atribuirCrachasTrigger ON joga;
DROP FUNCTION atribuirCrachas();

------------------------------------------------------------------------------------------------------------------------
-- (l) Criar a vista jogadorTotalInfo que permita aceder à informação sobre identificador, estado, email, username,
-- número total de jogos em que participou, número total de partidas em que participou e número total de pontos que já
-- obteve de todos os jogadores cujo estado seja diferente de “Banido”. Deve implementar na vista os cálculos sem aceder
-- às tabelas de estatísticas.
DROP VIEW jogadorTotalInfo;
DROP FUNCTION totalPartidasJogador(jogador_id INT);

------------------------------------------------------------------------------------------------------------------------
-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.
DROP PROCEDURE enviarmensagemtransacao(jogador_id integer, conversa_id integer, mensagem_texto varchar);
DROP PROCEDURE enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50));

------------------------------------------------------------------------------------------------------------------------
-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.
DROP PROCEDURE juntarConversaTransacao(jogador_id integer, conversa_id integer);
DROP PROCEDURE juntarConversa(jogador_id INT, conversa_id INT);

------------------------------------------------------------------------------------------------------------------------
-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.
DROP PROCEDURE iniciarConversaTransacao(jogador_id integer, nome_conversa varchar, conversa_id OUT integer);
DROP PROCEDURE iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT);
DROP FUNCTION startchat(jogador_id integer, nome_conversa varchar);

------------------------------------------------------------------------------------------------------------------------
-- (h) Criar o procedimento armazenado associarCrachá que recebe como parâmetros o identificador de um jogador, a
-- referência de um jogo e o nome de um crachá desse jogo e atribui o crachá a esse jogador se ele reunir as condições
-- para o obter.
DROP PROCEDURE associarcrachatransacao(jogador_id integer, jogo_id ALPHANUMERIC, cracha_nome varchar);
DROP PROCEDURE associarCracha(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50));

------------------------------------------------------------------------------------------------------------------------
-- (g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo e devolve uma tabela com
-- duas colunas (identificador de jogador, total de pontos) em que cada linha contém o identificador de um jogador e o
-- total de pontos que esse jogador teve nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo.
DROP FUNCTION PontosJogoPorJogador(jogo_id ALPHANUMERIC);

------------------------------------------------------------------------------------------------------------------------
-- (f) Criar a função totalJogosJogador que recebe como parâmetro o identificador de um jogador e devolve o número total
-- de jogos diferentes nos quais o jogador participou.
DROP FUNCTION totalJogosJogador(jogador_id INT);

------------------------------------------------------------------------------------------------------------------------
-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.
DROP FUNCTION totalPontosJogador(jogador_id INT);

--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username e regiao, desativar e banir o
-- jogador;
DROP PROCEDURE create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);
DROP PROCEDURE createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);

DROP PROCEDURE update_estado_jogador(id_jogador INT, new_estado VARCHAR(10));

COMMIT TRANSACTION;
------------------------------------------------------------------------------------------------------------------------
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- Functionalities
-- Jogador
-- 1. Update the nr_partidas of the jogodor_estatistica when a new joga entry is added or removed
DROP TRIGGER update_total_partidas_jogador ON joga;
DROP FUNCTION updateTotalPartidasJogador();

------------------------------------------------------------------------------------------------------------------------
-- 2. Update the nr of points of the jogodor_estatistica when a new joga entry is added or removed
DROP TRIGGER update_total_pontos_jogador ON joga;
DROP FUNCTION updateTotalPontosJogador();

------------------------------------------------------------------------------------------------------------------------
-- 3. Update the nr of jogos of the jogodor_estatistica when a new joga entry is added or removed
DROP TRIGGER update_total_jogos_jogador ON joga;
DROP FUNCTION updateTotalJogosJogador();

------------------------------------------------------------------------------------------------------------------------
-- Jogo
-- 1. Update the nr_partidas of the jogo_estatistica when a new partida is added or removed
DROP TRIGGER updateNrPartidasTrigger ON joga;
DROP FUNCTION updateNrPartidas();

------------------------------------------------------------------------------------------------------------------------
-- 2. Update the nr_jogadores of the jogo_estatistica when a new jogador is added or removed
DROP TRIGGER updateNrJogadoresTrigger ON joga;
DROP FUNCTION updateNrJogadores();

------------------------------------------------------------------------------------------------------------------------
-- 3. Update the total_pontos of the jogo_estatistica when a new partida is added or removed
DROP TRIGGER updatePontuacaoTotalTrigger ON joga;
DROP FUNCTION updatePontuacaoTotal();

COMMIT TRANSACTION;
------------------------------------------------------------------------------------------------------------------------
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- RESTRICOES
-- Model Restrictions
-- 1. The partida must be played by jogadores of the same regiao
DROP TRIGGER checkJogadorPartidaRegiao ON joga;
DROP FUNCTION checkJogadorPartidaRegiao();

------------------------------------------------------------------------------------------------------------------------
-- 2. The mensagem must be sent by a jogador of the conversa
DROP TRIGGER checkJogadorMensagemConversa ON mensagem;
DROP FUNCTION checkJogadorMensagemConversa();

COMMIT TRANSACTION;
------------------------------------------------------------------------------------------------------------------------
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;;
-- Model
-- Domain
DROP DOMAIN IF EXISTS ALPHANUMERIC CASCADE;
DROP DOMAIN IF EXISTS email CASCADE;
DROP DOMAIN IF EXISTS url CASCADE;

-- Table
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
