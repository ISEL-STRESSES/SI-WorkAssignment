--(d) Criar os mecanismos que permitam criar o jogador, dados os seus email e username, desativar e banir o jogador;
DROP PROCEDURE IF EXISTS create_jogador(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL);

DROP PROCEDURE IF EXISTS update_estado_jogador(id_jogador INT, new_estado VARCHAR(10));

-- (e) Criar a função totalPontosJogador que recebe como parâmetro o identificador de um jogador e devolve o número
-- total de pontos obtidos pelo jogador.
DROP FUNCTION IF EXISTS totalPontosJogador(jogador_id INT) CASCADE;

-- (f) Criar a função totalJogosJogador que recebe como parâmetro o identificador de um jogador e devolve o número total
-- de jogos diferentes nos quais o jogador participou.
DROP FUNCTION IF EXISTS totalJogosJogador(jogador_id INT);

-- (g) Criar a função PontosJogoPorJogador que recebe como parâmetro a referência de um jogo e devolve uma tabela com
-- duas colunas (identificador de jogador, total de pontos) em que cada linha contém o identificador de um jogador e o
-- total de pontos que esse jogador teve nesse jogo. Apenas devem ser devolvidos os jogadores que tenham jogado o jogo.
DROP FUNCTION IF EXISTS PontosJogoPorJogador(jogo_id ALPHANUMERIC);

-- (h) Criar o procedimento armazenado associarCrachá que recebe como parâmetros o identificador de um jogador, a
-- referência de um jogo e o nome de um crachá desse jogo e atribui o crachá a esse jogador se ele reunir as condições
-- para o obter.
DROP PROCEDURE IF EXISTS associarCracha(jogador_id INT, jogo_id ALPHANUMERIC, cracha_nome VARCHAR(50));

-- (i) Criar o procedimento armazenado iniciarConversa que permite iniciar uma conversa (chat) dados o identificador de
-- um jogador e o nome da conversa. O jogador deve ficar automaticamente associado à conversa e deve ser criada uma
-- mensagem a informar que o jogador criou a conversa. O procedimento deve devolver num parâmetro de saída o
-- identificador da conversa criada.
DROP PROCEDURE IF EXISTS iniciarConversa(jogador_id INT, nome_conversa VARCHAR(50), conversa_id OUT INT);

-- (j) Criar o procedimento armazenado juntarConversa que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e junta esse jogador a essa conversa. Deve ser criada uma mensagem a informar que o jogador entrou na
-- conversa.
DROP PROCEDURE IF EXISTS juntarConversa(jogador_id INT, conversa_id INT);

-- (k) Criar o procedimento armazenado enviarMensagem que recebe como parâmetros os identificadores de um jogador e de
-- uma conversa e o texto de uma mensagem e procede ao envio dessa mensagem para a conversa indicada, associando-a ao
-- jogador também indicado.
DROP PROCEDURE IF EXISTS enviarMensagem(jogador_id INT, conversa_id INT, mensagem_texto VARCHAR(50));

-- (l) Criar a vista jogadorTotalInfo que permita aceder à informação sobre identificador, estado, email, username,
-- número total de jogos em que participou, número total de partidas em que participou e número total de pontos que já
-- obteve de todos os jogadores cujo estado seja diferente de “Banido”. Deve implementar na vista os cálculos sem aceder
-- às tabelas de estatísticas.
DROP FUNCTION IF EXISTS totalPartidasJogador(jogador_id INT);

DROP VIEW IF EXISTS jogadorTotalInfo;

-- (m) Criar os mecanismos necessários para que, de forma automática, quando uma partida termina, se proceda à
-- atribuição de crachás do jogo a que ela pertence.
DROP FUNCTION IF EXISTS atribuirCrachas() CASCADE;

DROP TRIGGER IF EXISTS atribuirCrachas ON partida;

-- (n) Criar os mecanismos necessários para que a execução da instrução DELETE sobre a vista jogadorTotalInfo permita
-- colocar os jogadores envolvidos no estado “Banido”.
DROP FUNCTION IF EXISTS banirJogador();

DROP TRIGGER IF EXISTS banirJogador ON jogadorTotalInfo;
