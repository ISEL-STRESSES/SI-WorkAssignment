package dataAccess;


import jakarta.persistence.RollbackException;
import model.entities.game.Game;
import model.entities.game.Jogo;
import model.entities.game.badge.Badge;
import model.entities.game.badge.Cracha;
import model.entities.game.badge.CrachaId;
import model.types.Alphanumeric;
import model.types.URL;
import org.eclipse.persistence.exceptions.OptimisticLockException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BadgeTest {
    private JPAContext ctx;

    @Before
    public void setup() {
        ctx = new JPAContext();
    }

    @Test
    public void testRaisePointsToEarnBadgeOptimistic1() {
        try {
            ctx.connect();
            ctx.beginTransaction();

            // Create a test game and badge
            Game testGame = new Jogo();
            testGame.setId(new Alphanumeric("TestGame"));
            testGame.setName("Test Game");
            testGame.setUrl(new URL("https://example.com"));
            ctx.em.persist(testGame);

            Badge testBadge = new Cracha();
            CrachaId badgeId = new CrachaId(testGame.getId(), "TestBadge");
            testBadge.setId(badgeId);
            testBadge.setGame(testGame);
            testBadge.setPoints(100);
            testBadge.setImage(new URL("https://example.com/image.png"));
            ctx.em.persist(testBadge);

            // Commit the initial transaction
            ctx.commit();

            // Create a concurrent thread to alter the badge points simultaneously
            Thread concurrentThread = new Thread(() -> {
                try (JPAContext concurrentCtx = new JPAContext()) {
                    concurrentCtx.connect();
                    concurrentCtx.beginTransaction();

                    // Retrieve the badge in the concurrent thread
                    Badge concurrentBadge = concurrentCtx.getBadges().findByKey(badgeId);

                    // Increase the badge points by 20%
                    int currentPoints = concurrentBadge.getPoints();
                    concurrentBadge.setPoints((int) (currentPoints * 1.2));
                    concurrentCtx.em.merge(concurrentBadge);

                    // Commit the changes in the concurrent thread
                    concurrentCtx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Start the concurrent thread to alter the badge points
            concurrentThread.start();

            // Call the raisePointsToEarnBadgeOptimistic method in the main thread
            ctx.raisePointsToEarnBadgeOptimistic("TestBadge", testGame.getId());

            try {
                // Wait for the concurrent thread to finish execution
                concurrentThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retrieve the updated badge from the repository
            Badge updatedBadge = ctx.getBadges().findByKey(badgeId);

            // Assert that the points have been increased by 20%
            assertEquals(120, (int) updatedBadge.getPoints());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

    @Test
    public void testRaisePointsToEarnBadgeOptimistic() {
        try {
            ctx.connect();
            ctx.beginTransaction();

            // Create a test game and badge
            Game testGame = new Jogo(new Alphanumeric("TestGame"), "Test Game", new URL("https://example.com"));
            Badge testBadge = new Cracha(testGame.getId(), "TestBadge", 100, new URL("https://example.com/image.png"));
            testBadge.setGame(testGame);
            testBadge.setImage(new URL("https://example.com/image.png"));

            // Save the test game and badge in the repository
            ctx.em.persist(testGame);
            ctx.em.persist(testBadge);

            // Commit the initial transaction
            ctx.commit();

            int nreps = 3;

            do {
                try {
                    --nreps;
                    ctx.beginTransaction();

                    // Retrieve the badge with optimistic locking
                    Badge retrievedBadge = ctx.getBadges().findByKey(new CrachaId(testGame.getId(), "TestBadge"));

                    if (retrievedBadge == null) {
                        throw new RuntimeException("Badge not found");
                    }

                    // Increase the badge points by 20%
                    int increasedPoints = (int) (retrievedBadge.getPoints() * 1.2);
                    retrievedBadge.setPoints(increasedPoints);

                    // Commit the changes
                    ctx.commit();
                } catch (RollbackException | OptimisticLockException e) {
                    if (e.getCause() instanceof OptimisticLockException || e instanceof OptimisticLockException) {
                        if (ctx.em.getTransaction().isActive()) {
                            ctx.rollback();
                        }
                        if (nreps == 0) {
                            throw new RuntimeException("Concurrent modification error");
                        }
                    } else {
                        throw e;
                    }
                }
            } while (nreps > 0);

            // Retrieve the updated badge from the repository
            Badge updatedBadge = ctx.getBadges().findByKey(new CrachaId(testGame.getId(), "TestBadge"));

            // Assert that the points have been increased by 20%
            assertNotEquals(120, (int) updatedBadge.getPoints());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctx.close();
        }
    }

}