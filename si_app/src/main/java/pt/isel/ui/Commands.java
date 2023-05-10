package pt.isel.ui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.Map;
import java.util.TreeMap;

public class Commands {
    public static Map<String, Command> buildCommands() {
        // TreeMap is used as solution to order keys alphabetically for the help Command.
        Map<String, Command> commands = new TreeMap<>();

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

    private static Command buildExitCommand() {
        return new Command("Exit the program") {
            @Override
            public void act() {
                System.exit(0);
            }
        };
    }

    // TODO: Remove this test command later on
    private static Command buildTestCommand() {
        return new Command("Carlos, use this test command lmao") {
            @Override
            public void act() {

                final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAEx");
                final EntityManager em = emf.createEntityManager();

                final var tx = em.getTransaction();
                tx.begin();
                // PROCEDURE createJogadorTransaction(regiao_nome VARCHAR(50), new_username VARCHAR(10), new_email EMAIL)
                final Query query = em.createNativeQuery("insert into ligma (name) values (?)")
                        .setParameter(1, "Carlos");

                query.executeUpdate();
                tx.commit();
                System.out.println("Done");
            }
        };
    }

    private static Command build2d() {
        return new Command("Criar um Jogador") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");

            }
        };
    }
    private static Command build2e() {
        return new Command("SET 2E DESCRIPTION") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2f() {
        return new Command("SET 2F DESCRIPTION") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2g() {
        return new Command("SET 2G DESCRIPTION") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2h() {
        return new Command("SET 2H DESCRIPTION") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2i() {
        return new Command("SET 2I DESCRIPTION") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2j() {
        return new Command("SET 2J DESCRIPTION") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2l() {
        return new Command("SET 2L DESCRIPTION") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    private static Command buildHelpCommand(Map<String, Command> commands) {
        return new Command("List all available commands") {
            @Override
            public void act() {
                for(final Map.Entry<String, Command> command : commands.entrySet()) {
                    final String name = command.getKey();
                    final String description = command.getValue().description;
                    System.out.println(String.format("%6s", name) + "\t\t" + description);
                }
            }
        };
    }
}

