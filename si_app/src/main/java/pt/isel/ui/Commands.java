package pt.isel.ui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class Commands {
    public static Map<String, ICommand> buildCommands() {
        // TreeMap is used as solution to order keys alphabetically for the help Command.
        Map<String, ICommand> commands = new TreeMap<>();

        // TODO remove this test command later on
        commands.put("test", buildTestCommand());

        commands.put("2d", build2d());
        commands.put("2e", build2e());
        commands.put("2f", build2f());
        commands.put("2g", build2g());
        commands.put("2h", build2h());
        commands.put("2i", build2i());
        commands.put("2j", build2j());
        commands.put("2l", build2l());
        commands.put("help", buildHelpCommand(commands));
        commands.put("exit", buildExitCommand());

        return commands;
    }

    private static ICommand buildExitCommand() {
        return new ICommand("Exit the program") {
            @Override
            public void act() {
                System.exit(0);
            }
        };
    }

    // TODO: Remove this test command later on
    private static ICommand buildTestCommand() {
        return new ICommand("Carlos, use this test command lmao") {
            @Override
            public void act() {

                final EntityManagerFactory emf = Persistence.createEntityManagerFactory("aaaa");
                final EntityManager em = emf.createEntityManager();

                final var tx = em.getTransaction();
                tx.begin();
                // PROCEDURE createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL)
                final Query query = em.createNativeQuery("call createJogadorTransaction(?1, ?2, ?3)")
                        .setParameter(1, "Carlosland")
                        .setParameter(2, "Carlos")
                        .setParameter(3, "carlos@gmail.com");

                query.executeUpdate();
                tx.commit();
            }
        };
    }

    private static ICommand build2d() {
        return new ICommand("Criar um Jogador") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");

            }
        };
    }
    private static ICommand build2e() {
        return new ICommand("SET 2E DESCRIPTION") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static ICommand build2f() {
        return new ICommand("SET 2F DESCRIPTION") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static ICommand build2g() {
        return new ICommand("SET 2G DESCRIPTION") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static ICommand build2h() {
        return new ICommand("SET 2H DESCRIPTION") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static ICommand build2i() {
        return new ICommand("SET 2I DESCRIPTION") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static ICommand build2j() {
        return new ICommand("SET 2J DESCRIPTION") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static ICommand build2l() {
        return new ICommand("SET 2L DESCRIPTION") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    private static ICommand buildHelpCommand(Map<String, ICommand> commands) {
        return new ICommand("List all available commands") {
            @Override
            public void act() {
                for(final Map.Entry<String, ICommand> command : commands.entrySet()) {
                    final String name = command.getKey();
                    final String description = command.getValue().description;
                    System.out.println(String.format("%6s", name) + "\t\t" + description);
                }
            }
        };
    }
}

