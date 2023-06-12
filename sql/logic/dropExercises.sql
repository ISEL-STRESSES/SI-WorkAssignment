BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
-- Exercises
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
DROP FUNCTION totalPartidasJogador(jogador_id INTEGER);

------------------------------------------------------------------------------------------------------------------------
-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.
DROP PROCEDURE enviarmensagemtransacao(jogador_id INTEGER, conversa_id INTEGER, mensagem_texto VARCHAR);
DROP PROCEDURE enviarMensagem(jogador_id INTEGER, conversa_id INTEGER, mensagem_texto VARCHAR(50));

------------------------------------------------------------------------------------------------------------------------
-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.
DROP PROCEDURE juntarConversaTransacao(jogador_id INTEGER, conversa_id INTEGER);
DROP PROCEDURE juntarConversa(jogador_id INTEGER, conversa_id INTEGER);

------------------------------------------------------------------------------------------------------------------------
-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.
DROP PROCEDURE iniciarConversaTransacao(jogador_id INTEGER, nome_conversa VARCHAR, conversa_id OUT INTEGER);
DROP PROCEDURE iniciarConversa(jogador_id INTEGER, nome_conversa VARCHAR(50), conversa_id OUT INTEGER);
DROP FUNCTION startchat(jogador_id INTEGER, nome_conversa VARCHAR);

------------------------------------------------------------------------------------------------------------------------
-- (h) Criar o procedimento armazenado associarCrachá que recebe como parâmetros o identificador de um jogador, a
-- referência de um jogo e o nome de um crachá desse jogo e atribui o crachá a esse jogador se ele reunir as condições
-- para o obter.
DROP PROCEDURE associarcrachatransacao(jogador_id INTEGER, jogo_id ALPHANUMERIC, cracha_nome VARCHAR);
DROP PROCEDURE associarCracha(jogador_id INTEGER, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50));

------------------------------------------------------------------------------------------------------------------------
-- (g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo e devolve uma tabela com
-- duas colunas (identificador de jogador, total de pontos) em que cada linha contém o identificador de um jogador e o
-- total de pontos que esse jogador teve nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo.
DROP FUNCTION PontosJogoPorJogador(jogo_id ALPHANUMERIC);

------------------------------------------------------------------------------------------------------------------------
-- (f) Criar a função totalJogosJogador que recebe como parâmetro o identificador de um jogador e devolve o número total
-- de jogos diferentes nos quais o jogador participou.
DROP FUNCTION totalJogosJogador(jogador_id INTEGER);

------------------------------------------------------------------------------------------------------------------------
-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.
DROP FUNCTION totalPontosJogador(jogador_id INTEGER);

--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username e regiao, desativar e banir o
-- jogador;
DROP PROCEDURE create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);
DROP PROCEDURE createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);

DROP PROCEDURE update_estado_jogador(id_jogador INTEGER, new_estado VARCHAR(10));

COMMIT TRANSACTION;