BEGIN TRANSACTION;
INSERT INTO regiao (nome) VALUES ('Japan');
INSERT INTO regiao (nome) VALUES ('Congo');
INSERT INTO regiao (nome) VALUES ('Papua New Guinea');
INSERT INTO regiao (nome) VALUES ('Cape Verde');
INSERT INTO regiao (nome) VALUES ('Central African Rep');
INSERT INTO regiao (nome) VALUES ('Belize');
INSERT INTO regiao (nome) VALUES ('Spain');
INSERT INTO regiao (nome) VALUES ('Canada');
INSERT INTO regiao (nome) VALUES ('Antigua & Deps');
INSERT INTO regiao (nome) VALUES ('Lithuania');
COMMIT;

BEGIN TRANSACTION;
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('AlarmPiercer', 'alarmpiercer@hotmail.com', 'ativo', 'Lithuania');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('Goodwill', 'goodwill@hotmail.com', 'banido', 'Antigua & Deps');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('LongjohnsReply', 'longjohnsreply@hotmail.com', 'banido', 'Antigua & Deps');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('Emotion', 'emotion@icloud.com', 'ativo', 'Antigua & Deps');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('Alarm', 'alarm@outlook.com', 'ativo', 'Canada');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('LongjohnsCorrupt', 'longjohnscorrupt@hotmail.com', 'banido', 'Papua New Guinea');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('Unguarded', 'unguarded@outlook.com', 'inativo', 'Papua New Guinea');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('Infuse', 'infuse@aol.com', 'ativo', 'Canada');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('Reply', 'reply@outlook.com', 'banido', 'Central African Rep');
INSERT INTO jogador (username, email, estado, nome_regiao) VALUES ('Piercer', 'piercer@aol.com', 'inativo', 'Cape Verde');
COMMIT;

BEGIN TRANSACTION;
INSERT INTO jogo (id, nome, url) VALUES ('dRiuhNLeeu', 'The Snooty Gamer', 'https://TheSnootyGamer.com/?dRiuhNLeeu');
INSERT INTO jogo (id, nome, url) VALUES ('WvKx6LoOFa', 'FragileBrainsLMAO', 'https://FragileBrainsLMAO.com/?WvKx6LoOFa');
INSERT INTO jogo (id, nome, url) VALUES ('3AQKJDN4aE', 'Martine Callous Brains', 'https://MartineCallousBrains.com/?3AQKJDN4aE');
INSERT INTO jogo (id, nome, url) VALUES ('i7px2DVUdf', 'KoalaMilk', 'https://KoalaMilk.com/?i7px2DVUdf');
INSERT INTO jogo (id, nome, url) VALUES ('bfyC8sFDL5', 'Gaming With Martine', 'https://GamingWithMartine.com/?bfyC8sFDL5');
INSERT INTO jogo (id, nome, url) VALUES ('B86C76ooav', 'Koala Person', 'https://KoalaPerson.com/?B86C76ooav');
INSERT INTO jogo (id, nome, url) VALUES ('eSgk6NkJfC', 'SnootyBrainsLOL', 'https://SnootyBrainsLOL.com/?eSgk6NkJfC');
INSERT INTO jogo (id, nome, url) VALUES ('wcEePgDiw6', 'Uber Snooty Koala', 'https://UberSnootyKoala.com/?wcEePgDiw6');
INSERT INTO jogo (id, nome, url) VALUES ('6FdjoYdZ1v', 'FragileLegsOMG', 'https://FragileLegsOMG.com/?6FdjoYdZ1v');
INSERT INTO jogo (id, nome, url) VALUES ('KFMGS4D8w9', 'CallousLegsLOL', 'https://CallousLegsLOL.com/?KFMGS4D8w9');
COMMIT;

BEGIN TRANSACTION;
INSERT INTO conversa (nome) VALUES ('cvs0');
INSERT INTO conversa (nome) VALUES ('cvs1');
INSERT INTO conversa (nome) VALUES ('cvs2');
INSERT INTO conversa (nome) VALUES ('cvs3');
INSERT INTO conversa (nome) VALUES ('cvs4');
INSERT INTO conversa (nome) VALUES ('cvs5');
INSERT INTO conversa (nome) VALUES ('cvs6');
INSERT INTO conversa (nome) VALUES ('cvs7');
INSERT INTO conversa (nome) VALUES ('cvs8');
INSERT INTO conversa (nome) VALUES ('cvs9');
COMMIT;

BEGIN TRANSACTION;
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (0, 'WvKx6LoOFa', '2023-05-03', '2023-05-16', 'Cape Verde');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (0, 'B86C76ooav', '2023-05-03', null, 'Antigua & Deps');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (0, 'KFMGS4D8w9', '2023-05-03', '2023-05-17', 'Belize');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (1, 'KFMGS4D8w9', '2023-05-03', '2023-05-29', 'Spain');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (0, 'i7px2DVUdf', '2023-05-03', '2023-05-29', 'Japan');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (2, 'KFMGS4D8w9', '2023-05-03', null, 'Canada');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (0, 'dRiuhNLeeu', '2023-05-03', null, 'Papua New Guinea');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (3, 'KFMGS4D8w9', '2023-05-03', null, 'Antigua & Deps');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (4, 'KFMGS4D8w9', '2023-05-03', '2023-05-22', 'Congo');
INSERT INTO partida (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (5, 'KFMGS4D8w9', '2023-05-03', '2023-05-23', 'Papua New Guinea');

INSERT INTO partida_multijogador (id_jogo, nr_partida, estado) VALUES ('KFMGS4D8w9', 0, 'Em curso');
INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade)  VALUES ('KFMGS4D8w9', 1, 1);
INSERT INTO partida_multijogador (id_jogo, nr_partida, estado) VALUES ('KFMGS4D8w9', 2, 'Terminada');
INSERT INTO partida_multijogador (id_jogo, nr_partida, estado) VALUES ('KFMGS4D8w9', 3, 'Por iniciar');
INSERT INTO partida_multijogador (id_jogo, nr_partida, estado) VALUES ('KFMGS4D8w9', 4, 'Terminada');
INSERT INTO partida_multijogador (id_jogo, nr_partida, estado) VALUES ('KFMGS4D8w9', 5, 'Terminada');

INSERT INTO partida_multijogador (id_jogo, nr_partida, estado) VALUES ('WvKx6LoOFa', 0, 'A aguardar jogadores');
INSERT INTO partida_multijogador (id_jogo, nr_partida, estado) VALUES ('i7px2DVUdf', 0, 'Terminada');
INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade)  VALUES ('B86C76ooav', 0, 2);
INSERT INTO partida_normal (id_jogo, nr_partida, dificuldade)  VALUES ('dRiuhNLeeu', 0, 1);


COMMIT;

BEGIN TRANSACTION;
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha The Snooty Gamer', 'dRiuhNLeeu', 'https://TheSnootyGamer.com/?CrachaTheSnootyGamer', 95799);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha FragileBrainsLMAO', 'WvKx6LoOFa', 'https://FragileBrainsLMAO.com/?CrachaFragileBrainsLMAO', 93001);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha Martine Callous Brains', '3AQKJDN4aE', 'https://MartineCallousBrains.com/?CrachaMartineCallousBrains', 83284);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha KoalaMilk', 'i7px2DVUdf', 'https://KoalaMilk.com/?CrachaKoalaMilk', 15558);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha Gaming With Martine', 'bfyC8sFDL5', 'https://GamingWithMartine.com/?CrachaGamingWithMartine', 78473);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha Koala Person', 'B86C76ooav', 'https://KoalaPerson.com/?CrachaKoalaPerson', 85059);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha SnootyBrainsLOL', 'eSgk6NkJfC', 'https://SnootyBrainsLOL.com/?CrachaSnootyBrainsLOL', 34669);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha Uber Snooty Koala', 'wcEePgDiw6', 'https://UberSnootyKoala.com/?CrachaUberSnootyKoala', 40889);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha FragileLegsOMG', '6FdjoYdZ1v', 'https://FragileLegsOMG.com/?CrachaFragileLegsOMG', 62406);
INSERT INTO cracha (nome, id_jogo, imagem, limite_pontos) VALUES ('Cracha CallousLegsLOL', 'KFMGS4D8w9', 'https://CallousLegsLOL.com/?CrachaCallousLegsLOL', 33791);
COMMIT;
