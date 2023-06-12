package logic.repsitories;

import dataAccess.JPAContext;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ReposTest {
    private final JPAContext ctx = new JPAContext();

    @Test
    public void regionRepoTest() {
        try {
            this.ctx.connect();
            assertNotNull(ctx.getRegions().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void playerRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getPlayers().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void gameRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getGames().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void chatRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getChats().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void badgeRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getBadges().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void matchRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getMatches().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void messageRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getMessages().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void normalMatchRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getNormalMatches().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void multiplayerMatchRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getMultiPlayerMatches().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void playerStatsRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getPlayersStats().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void gameStatsRepoTest() {
        try {
            ctx.connect();
            assertNotNull(ctx.getGamesStats().findAll());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }


}
