package pt.isel;

import org.junit.Test;
import dataAccess.JPAContext;

import static org.junit.Assert.assertNotNull;

public class ReposTest {
    private final JPAContext ctx = new JPAContext();

    @Test
    public void PlayerRepoTest() {
        ctx.connect();
        assertNotNull(ctx.getPlayers().findAll());
        ctx.close();
    }
}
