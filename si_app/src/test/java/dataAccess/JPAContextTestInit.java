package dataAccess;

import model.entities.player.Jogador;
import model.entities.player.Player;
import model.entities.region.Regiao;
import model.types.Email;

public class JPAContextTestInit {
    public static void createPlayerInit() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            try {
                Regiao testRegion = ctx.getRegions().findByKey("testRegion");
                if (testRegion != null) {
                    ctx.em.remove(testRegion);
                    ctx.commit();
                    ctx.beginTransaction();
                }
                ctx.commit();
                ctx.beginTransaction();
            } catch (Exception ignored) {
            }
            ctx.em.persist(new Regiao("testRegion"));
            try {
                Player testPlayer = ctx.getPlayers().findByUsername("testJpa");
                if (testPlayer != null) {
                    ctx.em.remove(testPlayer);
                    ctx.commit();
                    ctx.beginTransaction();
                }
                ctx.commit();
                ctx.beginTransaction();
            } catch (Exception ignored) {
            }
            ctx.commit();
        }
    }

    public static void playerTotalPointsInit() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            try {
                Player testPlayer = ctx.getPlayers().findByUsername("testJpa");
                if (testPlayer != null) {
                    ctx.em.remove(testPlayer);
                    ctx.commit();
                    ctx.beginTransaction();
                }
                ctx.commit();
                ctx.beginTransaction();
            } catch (Exception ignored) {
            }
            ctx.em.persist(new Regiao("testRegion"));
            ctx.commit();
            ctx.beginTransaction();
            ctx.em.persist(new Jogador("testJpa", new Email("testJpa@gmail.com"), ctx.getRegions().findByKey("testRegion")));
            ctx.commit();
        }
    }
}
