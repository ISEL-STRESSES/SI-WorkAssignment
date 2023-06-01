package ui;

import dataAccess.JPAContext;
import model.entities.player.Jogador;
import model.entities.player.Player;
import model.entities.region.Region;
import model.types.Email;
import model.types.PlayerState;

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
        commands.put("2l", build2l());
        commands.put("test", buildTest());
        commands.put("help", buildHelpCommand(commands));
        commands.put("exit", buildExitCommand());
        return commands;
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

    /**
     * Builds the command for the 2d exercise.
     *
     * @return the command for the 2d exercise.
     */
    private static Command build2d1() {
        return new Command("Criar um Jogador") {

            /**
             * Executes the command of the 2d exercise.
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
                    ctx.updatePlayerStatus(player, newState);
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
            @Override
            public void act() {
                final String username = promptUsername("Insert the username of the player to get the total points."); //"testJpa";
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
        return new Command("Associar crachá.") {
            @Override
            public void act() {

            }
        };
    }

    /**
     * Builds the command for the 2h exercise.
     *
     * @return the command for the 2h exercise.
     */
    private static Command build2h() {
        return new Command("Iniciar conversa.") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    /**
     * Builds the command for the 2i exercise.
     *
     * @return the command for the 2i exercise.
     */
    private static Command build2i() {
        return new Command("Juntar a uma conversa.") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    /**
     * Builds the command for the 2j exercise.
     *
     * @return the command for the 2j exercise.
     */
    private static Command build2j() {
        return new Command("Enviar uma mensagem para uma conversa.") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
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

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
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
}

