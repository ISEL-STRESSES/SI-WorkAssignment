package pt.isel.ui;

import jakarta.persistence.*;

import java.util.Map;
import java.util.TreeMap;

import static pt.isel.ui.Prompts.promptEmail;

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
        return new Command("Sair do programa.") {
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

                final EntityTransaction tx = em.getTransaction();
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
                final String email = promptEmail("Insert a new email for the new player.");

                throw new UnsupportedOperationException("Not supported yet.");

            }
        };
    }
    private static Command build2e() {
        return new Command("Obter pontos totais de um jogador.") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2f() {
        return new Command("Obter número de jogos de um jogador.") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2g() {
        return new Command("Associar crachá.") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2h() {
        return new Command("Iniciar conversa.") {
            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2i() {
        return new Command("Juntar a uma conversa.") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2j() {
        return new Command("Enviar uma mensagem para uma conversa.") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    private static Command build2l() {
        return new Command("Obter informação total de um jogador") {

            @Override
            public void act() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    private static Command buildHelpCommand(Map<String, Command> commands) {
        return new Command("Liste todos os comandos disponíveis.") {
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

