package ui;

import dataAccess.JPAContext;
import model.entities.chat.Chat;
import model.entities.game.Game;
import model.entities.game.badge.Badge;
import model.entities.game.badge.CrachaId;
import model.entities.player.Jogador;
import model.entities.player.Player;
import model.entities.region.Region;
import model.types.Alphanumeric;
import model.types.Email;
import model.types.PlayerState;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static ui.Prompts.*;

/**
 * Represents the commands available to the user.
 */
public class Commands {

    /**
     * Builds the commands available to the user.
     *
     * @return the commands available to the user
     */
    public static Map<String, Command> buildCommands() {
        Map<String, Command> commands = new TreeMap<>();
        commands.put("2d1", build2d1());
        commands.put("2d2", build2d2());
        commands.put("2e", build2e());
        commands.put("2f", build2f());
        commands.put("2g", build2g());
        commands.put("2h", build2h());
        commands.put("2i", build2i());
        commands.put("2j", build2j());
        commands.put("2k", build2k());
        commands.put("2l", build2l());
        commands.put("test", buildTest());
        commands.put("help", buildHelpCommand(commands));
        commands.put("exit", buildExitCommand());
        return commands;
    }

    /**
     * Builds the command for the 2d exercise.
     *
     * @return the command for the 2d exercise.
     */
    private static Command build2d1() {
        return new Command("Criar um Jogador") {

            /**
             * Executes the command of the 2d exercise.
             * Creates a new player given the email, region and username.
             * It's required to already exist a region with the given name.
             * The email and/or username must be unique.
             */
            @Override
            public void act() {
                final Email email = promptEmail("Insert a new email for the new player."); //new Email("test@jpa.com");
                final String username = promptUsername("Insert a new username for the new player."); //"testJpa";
                final String regionName = promptRegion("Insert a new region for the new player."); //"testRegion";
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Region region = ctx.getRegions().findByKey(regionName);
                    if (region == null) {
                        System.out.println("Region not found.");
                        return;
                    }
                    Player player = new Jogador(username, email, region);
                    ctx.createPlayer(player);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private static Command build2d2() {
        return new Command("Atualizar o estado de um jogador") {

            /**
             * Executes the command of the 2d2 exercise.
             * Updates the state of a player given the username.
             * TODO ask in the S.out if the user want to find player by username or email or id(dont want id)
             */
            @Override
            public void act() {
                final String username = promptUsername("Insert the username of the player to update."); //"testJpa";
                final PlayerState newState = promptPlayerState("Insert the new state of the player."); //PlayerState.ACTIVE;
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Player player = ctx.getPlayers().findByUsername(username);
                    if (player == null) {
                        System.out.println("Player not found.");
                        return;
                    }
                    Player updatedPlayer = ctx.updatePlayerStatus(player, newState);
                    System.out.println("Player with username " + updatedPlayer.getUsername() + " now has state " + updatedPlayer.getState());
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2e exercise.
     *
     * @return the command for the 2e exercise.
     */
    private static Command build2e() {
        return new Command("Obter pontos totais de um jogador.") {
            /**
             * Executes the command of the 2e exercise.
             * Gets the total points of a player given the username.
             * TODO ask in the S.out if the user want to find player by username or email or id(dont want id)
             */
            @Override
            public void act() {
                final String username = promptUsername("Insert the username of the player to get the total points."); //"testJpa";
                // final Email email = promptEmail("Insert the email of the player to get the total points."); //new Email("");
                // final Integer id = promptId("Insert the id of the player to get the total points."); //1;
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Player player = ctx.getPlayers().findByUsername(username);
                    if (player == null) {
                        System.out.println("Player not found.");
                        return;
                    }
                    System.out.println("Total points: " + ctx.playerTotalPoints(player));
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2f exercise.
     *
     * @return the command for the 2f exercise.
     */
    private static Command build2f() {
        return new Command("Obter número de jogos de um jogador.") {
            /**
             * Executes the command of the 2f exercise.
             * Gets the number of games of a player given the username.
             * TODO ask in the S.out if the user want to find player by username or email or id(dont want id)
             */
            @Override
            public void act() {
                final String username = promptUsername("Insert the username of the player to get the number of games."); //"testJpa";
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Player player = ctx.getPlayers().findByUsername(username);
                    if (player == null) {
                        System.out.println("Player not found.");
                        return;
                    }
                    System.out.println("Number of games: " + ctx.playerTotalGames(player));
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2g exercise.
     *
     * @return the command for the 2g exercise.
     */
    private static Command build2g() {
        return new Command("Obter todos os pontos de jogadores de um jogo.") {
            /**
             * Executes the command of the 2g exercise.
             * Gets the total points of all players of a game given the game id or name.
             */
            @Override
            public void act() {
                final Alphanumeric gameId = promptGameId("Insert the game identifier to get the total points of all players.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Game game = ctx.getGames().findByKey(gameId);
                    if (game == null) {
                        System.out.println("Game not found.");
                        return;
                    }
                    List<Object[]> points = ctx.gamePointsByPlayer(game);
                    System.out.println("Total points of all players: ");
                    for (Object[] point : points) {
                        System.out.println("Player: " + point[0] + " Points: " + point[1]);
                    }
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2h exercise.
     *
     * @return the command for the 2h exercise.
     */
    private static Command build2h() {
        return new Command("Associar crachá.") {
            /**
             * Executes the command of the 2h exercise.
             * Associates a badge to a player given the badge name, the player id and game id.
             */
            @Override
            public void act() {
                final String badgeName = promptBadgeName("Insert the badge name to associate to a player."); //"testJpa";
                final Alphanumeric gameId = promptGameId("Insert the game identifier to associate the badge to a player.");
                final Integer playerId = promptId("Insert the player id to associate the badge to a player.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Player player = ctx.getPlayers().findByKey(playerId);
                    if (player == null) {
                        System.out.println("Player not found.");
                        return;
                    }
                    Game game = ctx.getGames().findByKey(gameId);
                    if (game == null) {
                        System.out.println("Game not found.");
                        return;
                    }
                    CrachaId crachaId = new CrachaId(gameId, badgeName);
                    Badge badge = ctx.getBadges().findByKey(crachaId);
                    if (badge == null) {
                        System.out.println("Badge not found.");
                        return;
                    }
                    ctx.giveBadgeToPlayer(player, game, badge);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2i exercise.
     *
     * @return the command for the 2i exercise.
     */
    private static Command build2i() {
        return new Command("Iniciar conversa.") {

            /**
             * Executes the command of the 2i exercise.
             * Starts a chat given the name of the conversation and the player id.
             */
            @Override
            public void act() {
                final String chatName = promptChatName("Insert the name of the chat to start."); //"testJpa";
                final Integer playerId = promptId("Insert the player id to start the chat.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Player player = ctx.getPlayers().findByKey(playerId);
                    if (player == null) {
                        System.out.println("Player not found.");
                        return;
                    }
                    Chat chat = ctx.getChats().findByName(chatName);
                    ctx.initiateChat(player, chat);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2j exercise.
     *
     * @return the command for the 2j exercise.
     */
    private static Command build2j() {
        return new Command("Juntar a uma conversa.") {

            /**
             * Executes the command of the 2j exercise.
             * Joins a player to the given the id of the conversation with the id of the conversation and player.
             */
            @Override
            public void act() {
                final Integer chatId = promptId("Insert the id of the chat to join."); //"testJpa";
                final Integer playerId = promptId("Insert the player id to join the chat.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Player player = ctx.getPlayers().findByKey(playerId);
                    if (player == null) {
                        System.out.println("Player not found.");
                        return;
                    }
                    Chat chat = ctx.getChats().findByKey(chatId);
                    if (chat == null) {
                        System.out.println("Chat not found.");
                        return;
                    }
                    ctx.addPlayerToChat(player, chat);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2k exercise.
     *
     * @return the command for the 2k exercise.
     */
    private static Command build2k() {
        return new Command("Enviar uma mensagem para uma conversa.") {

            /**
             * Executes the command of the 2k exercise.
             * Sends a message to a chat given the id of the conversation, the player id and the text of the message.
             */
            @Override
            public void act() {
                final Integer chatId = promptId("Insert the id of the chat to send a message."); //"testJpa";
                final Integer playerId = promptId("Insert the player id to send a message to the chat.");
                final String messageText = promptLength("Insert the text of the message to send to the chat.", 20);
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    Player player = ctx.getPlayers().findByKey(playerId);
                    if (player == null) {
                        System.out.println("Player not found.");
                        return;
                    }
                    Chat chat = ctx.getChats().findByKey(chatId);
                    if (chat == null) {
                        System.out.println("Chat not found.");
                        return;
                    }
                    ctx.sendMessage(player, chat, messageText);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2l exercise.
     *
     * @return the command for the 2l exercise.
     */
    private static Command build2l() {
        return new Command("Obter informação total de um jogador") {

            /**
             * Executes the command of the 2l exercise.
             * Gets the total information of all players that are not banned.
             *
             */
            @Override
            public void act() {
                try (JPAContext ctx = new JPAContext()) {
                    ctx.getPlayersTotalInfoFromDB().forEach(System.out::println);
                }
            }
        };
    }

    private static Command buildTest() {
        return new Command("test") {

            /**
             * Executes a test command.
             */
            @Override
            public void act() {
                try (JPAContext ctx = new JPAContext()) {
                    ctx.test();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the help command.
     *
     * @param commands the commands available to the user
     * @return the command for the help command
     */
    private static Command buildHelpCommand(Map<String, Command> commands) {
        return new Command("Liste todos os comandos disponíveis.") {
            @Override
            public void act() {
                for (final Map.Entry<String, Command> command : commands.entrySet()) {
                    final String name = command.getKey();
                    final String description = command.getValue().description;
                    System.out.println(String.format("%6s", name) + "\t\t" + description);
                }
            }
        };
    }

    /**
     * Builds the exit command.
     *
     * @return the exit command
     */
    private static Command buildExitCommand() {
        return new Command("Sair do programa.") {
            @Override
            public void act() {
                System.exit(0);
            }
        };
    }
}

