package dataAccess;

import model.types.Email;
import model.types.PlayerState;
import org.junit.Test;

import static dataAccess.JPAContextTestInit.createPlayerInit;
import static dataAccess.JPAContextTestInit.playerTotalPointsInit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JPAContextTest {
    @Test
    public void createPlayerTest() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            createPlayerInit();
            Email testEmail = new Email("testJpa@gmail.com");
            String testUsername = "testJpa";
            String testRegion = "testRegion";
            ctx.createPlayer(testEmail, testUsername, testRegion);
            ctx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            assertNotNull(ctx.getPlayers().findByUsername("testJpa"));
        }
    }

    @Test
    public void updatePlayerTest() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            ctx.updatePlayerStatus("testJpa", PlayerState.INACTIVE);
            ctx.commit();
            ctx.beginTransaction();
            assertEquals(PlayerState.INACTIVE, ctx.getPlayers().findByUsername("testJpa").getState());
            ctx.commit();
        }
    }

    @Test
    public void playerTotalPointsTest() {
        try (JPAContext ctx = new JPAContext()) {
            ctx.connect();
            ctx.beginTransaction();
            playerTotalPointsInit();
            Integer points = ctx.playerTotalPoints("testJpa");
            ctx.commit();
            assertNotNull(points);
        }
    }
}
