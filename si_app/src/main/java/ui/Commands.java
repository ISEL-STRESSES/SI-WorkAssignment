package ui;

import dataAccess.JPAContext;
import model.entities.player.Player;
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
        commands.put("1d1", build1d1());
        commands.put("1d2", build1d2());
        commands.put("1e", build1e());
        commands.put("1f", build1f());
        commands.put("1g", build1g());
        commands.put("1h1", build1h());
        commands.put("1h2", build1hManually());
        commands.put("1i", build1i());
        commands.put("1j", build1j());
        commands.put("1k", build1k());
        commands.put("1l", build1l());
        commands.put("2a", build2a());
        commands.put("2c", build2c());
        commands.put("help", buildHelpCommand(commands));
        commands.put("exit", buildExitCommand());
        return commands;
    }

    /**
     * Builds the command for the 1d1 exercise.
     *
     * @return the command for the 1d1 exercise.
     */
    private static Command build1d1() {
        return new Command("Criar um Jogador.") {

            /**
             * Executes the command of the 1d exercise.
             * Creates a new player given the email, region and username.
             * It's required to already exist a region with the given name.
             * The email and/or username must be unique.
             */
            @Override
            public void act() {
                final Email email = promptEmail("Insert a new email for the new player.");
                final String username = promptUsername("Insert a new username for the new player.");
                final String regionName = promptRegion("Insert a region for the new player.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    ctx.createPlayer(email, username, regionName);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 1d2 exercise.
     *
     * @return the command for the 1d2 exercise.
     */
    private static Command build1d2() {
        return new Command("Atualizar o estado de um jogador") {

            /**
             * Executes the command of the 1d2 exercise.
             * Updates the state of a player given the username.
             */
            @Override
            public void act() {
                final String username = promptUsername("Insert the username of the player to update.");
                final PlayerState newState = promptPlayerState("Insert the new state of the player.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    Player updatedPlayer = ctx.updatePlayerStatus(username, newState);
                    System.out.println("Player with username " + updatedPlayer.getUsername() +
                            " now has state " + updatedPlayer.getState());
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 1e exercise.
     *
     * @return the command for the 1e exercise.
     */
    private static Command build1e() {
        return new Command("Obter pontos totais de um jogador.") {
            /**
             * Executes the command of the 1e exercise.
             * Gets the total points of a player given the username.
             */
            @Override
            public void act() {
                final String username = promptUsername("Insert the username of the player to get the total points.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    System.out.println("Total points: " + ctx.playerTotalPoints(username));
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 1f exercise.
     *
     * @return the command for the 1f exercise.
     */
    private static Command build1f() {
        return new Command("Obter número de jogos de um jogador.") {
            /**
             * Executes the command of the 1f exercise.
             * Gets the number of games of a player given the username.
             */
            @Override
            public void act() {
                final String username = promptUsername("Insert the username of the player to get the number of games.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    System.out.println("Number of games: " + ctx.playerTotalGames(username));
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 1g exercise.
     *
     * @return the command for the 1g exercise.
     */
    private static Command build1g() {
        return new Command("Obter todos os pontos de jogadores de um jogo.") {
            /**
             * Executes the command of the 1g exercise.
             * Gets the total points of all players of a game given the game id or name.
             */
            @Override
            public void act() {
                //final Alphanumeric gameId = promptGameId("Insert the game identifier to get the total points of all players.");
                final String gameName = promptGameName("Insert the game name to get the total points of all players.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    List<Object[]> points = ctx.gamePointsByPlayer(gameName);
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
     * Builds the command for the 1h exercise.
     *
     * @return the command for the 1h exercise.
     */
    private static Command build1h() {
        return new Command("Associar crachá.") {
            /**
             * Executes the command of the 1h exercise.
             * Associates a badge to a player given the badge name, the player id and game id.
             */
            @Override
            public void act() {
                final String badgeName = promptBadgeName("Insert the badge name to associate to a player.");
                //final Alphanumeric gameId = promptGameId("Insert the game identifier to associate the badge to a player.");4
                final String gameName = promptGameName("Insert the game name to associate the badge to a player.");
                //final Integer playerId = promptId("Insert the player id to associate the badge to a player.");
                final String username = promptUsername("Insert the username to associate the badge to a player.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.giveBadgeToPlayer(username, gameName, badgeName);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 1i exercise.
     *
     * @return the command for the 1i exercise.
     */
    private static Command build1i() {
        return new Command("Iniciar conversa.") {

            /**
             * Executes the command of the 1i exercise.
             * Starts a chat given the name of the conversation and the player id.
             */
            @Override
            public void act() {
                final String chatName = promptChatName("Insert the name of the chat to start.");
                // final Integer playerId = promptId("Insert the player id to start the chat.");
                final String username = promptUsername("Insert the username to start the chat.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    Integer chatId = ctx.initiateChat(username, chatName);
                    System.out.println("Chat with id " + chatId + " created.");
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 1j exercise.
     *
     * @return the command for the 1j exercise.
     */
    private static Command build1j() {
        return new Command("Juntar a uma conversa.") {

            /**
             * Executes the command of the 1j exercise.
             * Joins a player to the given the id of the conversation with the id of the conversation and player.
             */
            @Override
            public void act() {
                // final Integer chatId = promptId("Insert the id of the chat to join.");
                final String chatName = promptChatName("Insert the name of the chat to join.");
                // final Integer playerId = promptId("Insert the player id to join the chat.");
                final String username = promptUsername("Insert the username to join the chat.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.addPlayerToChat(username, chatName);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 1k exercise.
     *
     * @return the command for the 1k exercise.
     */
    private static Command build1k() {
        return new Command("Enviar uma mensagem para uma conversa.") {

            /**
             * Executes the command of the 1k exercise.
             * Sends a message to a chat given the id of the conversation, the player id and the text of the message.
             */
            @Override
            public void act() {
                // final Integer chatId = promptId("Insert the id of the chat to send a message.");
                // final Integer playerId = promptId("Insert the player id to send a message to the chat.");
                final String chatName = promptChatName("Insert the name of the chat to send a message.");
                final String username = promptUsername("Insert the username to send a message to the chat.");
                final String messageText = promptLength("Insert the text of the message to send to the chat.", 20);
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    ctx.sendMessage(username, chatName, messageText);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 1l exercise.
     *
     * @return the command for the 1l exercise.
     */
    private static Command build1l() {
        return new Command("Obter informação total dos jogadores.") {

            /**
             * Executes the command of the 1l exercise.
             * Gets the total information of all players that are not banned.
             */
            @Override
            public void act() {
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    ctx.getPlayersTotalInfoFromDB().forEach(System.out::println);
                    ctx.commit();
                }
            }
        };
    }

    /**
     * Builds the command for the 1h manually exercise.
     *
     * @return the command for the 1h manually exercise.
     */
    private static Command build1hManually() {
        return new Command("Associar um cracha a um jogador manualmente.") {

            /**
             * Executes the command of the 1h manually exercise.
             * Gives a badge to a player given the name of the badge and the player id.
             */
            @Override
            public void act() {
                final String badgeName = promptBadgeName("Insert the badge name to associate to a player.");
                final String gameName = promptGameName("Insert the game name to associate the badge to a player.");
                final String username = promptUsername("Insert the username to associate the badge to a player.");
                // final Alphanumeric gameId = promptGameId("Insert the game identifier to associate the badge to a player.");
                // final Integer playerId = promptId("Insert the player id to associate the badge to a player.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    ctx.giveBadgeToPlayerManual(username, gameName, badgeName);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2a exercise.
     *
     * @return the command for the 2a exercise.
     */
    private static Command build2a() {
        return new Command("Aumentar os pontos de um cracha em 20% com optimistic locking.") {

            /**
             * Executes the command of the 2a exercise.
             * Increases the points of a badge by 20% given the name of the badge.
             * Uses optimistic locking.
             */
            @Override
            public void act() {
                final String badgeName = promptBadgeName("Insert the name of the badge to increase the points.");
                final Alphanumeric gameId = promptGameId("Insert the game id to increase the points of the badge.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    ctx.raisePointsToEarnBadgeOptimistic(badgeName, gameId);
                    ctx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Builds the command for the 2c exercise.
     *
     * @return the command for the 2c exercise.
     */
    private static Command build2c() {
        return new Command("Aumentar os pontos de um cracha em 20% com pessimistic locking.") {

            /**
             * Executes the command of the 2c exercise.
             * Increases the points of a badge by 20% given the name of the badge.
             * Uses pessimistic locking.
             */
            @Override
            public void act() {
                final String badgeName = promptBadgeName("Insert the name of the badge to increase the points.");
                final Alphanumeric gameId = promptGameId("Insert the game id to increase the points of the badge.");
                try (JPAContext ctx = new JPAContext()) {
                    ctx.connect();
                    ctx.beginTransaction();
                    ctx.raisePointsToEarnBadgePessimistic(badgeName, gameId);
                    ctx.commit();
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

