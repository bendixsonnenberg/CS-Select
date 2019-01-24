package com.csselect.game;

import com.csselect.Injector;
import com.csselect.TestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.mlserver.MLServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class GameTests extends TestClass {

    private Game game;

    @Override @Before
    public void setUp() {
        this.game = new Game(1);
        this.game.setGamemode(new BinarySelect());
        this.game.setMlserver(Injector.getInjector().getInstance(MLServer.class));
        FeatureSet features = new FeatureSet("abc");
        for (int i = 0; i < 10; i++) {
            features.addFeature(new Feature(i, "a"));
        }
        this.game.setFeatureSet(features);
        Termination term = new NumberOfRoundsTermination(5);
        term.setGame(game);
        this.game.setTermination(term);
    }

    @Override @After
    public void reset() {
        this.game = null;
    }

    @Test
    public void terminateGame() {
        Assert.assertFalse(game.isTerminated());
        game.terminateGame();
        Assert.assertTrue(game.isTerminated());
        game.terminateGame();
        Assert.assertTrue(game.isTerminated());

    }

    @Test
    public void termination() {
        Assert.assertFalse(game.isTerminated());
        Round round = new StandardRound(Injector.getInjector().getInstance(DatabaseAdapter.class).createPlayer("email", "hash", "salt", "username"), 1, 2, 3, 4);
        game.addFinishedRound(round);
        Round round2 = new StandardRound(Injector.getInjector().getInstance(DatabaseAdapter.class).createPlayer("email", "hash", "salt", "username"), 1, 2, 3, 4);
        game.addFinishedRound(round2);
        Round round3 = new StandardRound(Injector.getInjector().getInstance(DatabaseAdapter.class).createPlayer("email", "hash", "salt", "username"), 1, 2, 3, 4);
        game.addFinishedRound(round3);
        Round round4 = new StandardRound(Injector.getInjector().getInstance(DatabaseAdapter.class).createPlayer("email", "hash", "salt", "username"), 1, 2, 3, 4);
        game.addFinishedRound(round4);
        Assert.assertFalse(game.isTerminated());
        Round round5 = new StandardRound(Injector.getInjector().getInstance(DatabaseAdapter.class).createPlayer("email", "hash", "salt", "username"), 1, 2, 3, 4);
        game.addFinishedRound(round5);
        Assert.assertTrue(game.isTerminated());
        Round round6 = new StandardRound(Injector.getInjector().getInstance(DatabaseAdapter.class).createPlayer("email", "hash", "salt", "username"), 1, 2, 3, 4);
        game.addFinishedRound(round6);
        Assert.assertEquals(6, game.getNumberOfRounds());
    }


    @Test
    public void inviteTwoPlayers() {
        invitePlayer1();
        invitePlayer2();
        Assert.assertNotNull(game.getInvitedPlayers());
        Assert.assertEquals(2, game.getInvitedPlayers().size());
    }

    @Test
    public void inviteSamePlayer() {
        invitePlayer1();
        invitePlayer1();
        Assert.assertNotNull(game.getInvitedPlayers());
        Assert.assertEquals(1, game.getInvitedPlayers().size());
    }

    @Test
    public void declineInvite() {
        invitePlayer1();
        invitePlayer2();
        game.declineInvite("email");
        Assert.assertEquals(1, game.getInvitedPlayers().size());
        Assert.assertTrue(game.getInvitedPlayers().contains("emai"));
    }

    @Test
    public void declineWrongEmail() {
        game.declineInvite("xd");
        invitePlayer1();
        game.declineInvite("emai");
        Assert.assertEquals(1, game.getInvitedPlayers().size());
    }

    @Test
    public void addOnePlayer() {
        this.addPlayer1();
        Assert.assertNotNull(game.getPlayingPlayers());
        Assert.assertEquals(1, game.getPlayingPlayers().size());
    }

    @Test
    public void addTwoPlayers() {
        this.addPlayer1();
        this.addPlayer2();
        Assert.assertNotNull(game.getPlayingPlayers());
        Assert.assertEquals(2, game.getPlayingPlayers().size());
    }

    @Test
    public void addPlayerSameID() {
        this.addPlayer1();
        this.addPlayer1();
        Assert.assertNotNull(game.getPlayingPlayers());
        Assert.assertEquals(1, game.getPlayingPlayers().size());
    }

    @Test
    public void addPlayerWrongEmail() {
        game.acceptInvite(3, "a");
        Assert.assertEquals(0, game.getPlayingPlayers().size());
    }


    @Test
    public void createRound() {
        this.addPlayer1();
        Collection<Feature> features = game.startRound(0);
        Assert.assertNotNull(features);
        Assert.assertEquals(10, features.size());
    }

    @Test
    public void createMatrixRound() {
        this.addPlayer1();
        game.setGamemode(new MatrixSelect(8, 3, 5));
        Collection<Feature> features = game.startRound(0);
        Assert.assertNotNull(features);
        Assert.assertEquals(8, features.size());
    }

    @Test
    public void createRoundWrongPlayer() {
        Collection<Feature> features = game.startRound(0);
        Assert.assertNull(features);
    }

    @Test
    public void addFinishedRound() {
        Assert.assertEquals(0, game.getNumberOfRounds());
        Assert.assertEquals(0, game.getRounds().size());
        Round round = new StandardRound(Injector.getInjector().getInstance(DatabaseAdapter.class).createPlayer("email", "hash", "salt", "username"), 1, 2, 3, 4);
        game.addFinishedRound(round);
        Assert.assertEquals(1, game.getNumberOfRounds());
        Assert.assertEquals(1, game.getRounds().size());
    }

    @Test
    public void numberOfRounds() {
        Assert.assertEquals(0, game.getNumberOfRounds());
        addPlayer1();
        game.startRound(0);
        game.startRound(0);
        Assert.assertEquals(0, game.getNumberOfRounds());
        Round round = new StandardRound(Injector.getInjector().getInstance(DatabaseAdapter.class).createPlayer("email", "hash", "salt", "username"), 1, 2, 3, 4);
        game.addFinishedRound(round);
        Assert.assertEquals(1, game.getNumberOfRounds());
    }

    private void addPlayer1() {
        invitePlayer1();
        game.acceptInvite(0, "email");
    }

    private void addPlayer2() {
        invitePlayer2();
        game.acceptInvite(1, "emai");
    }

    private void invitePlayer1() {
        DatabaseAdapter database = Injector.getInjector().getInstance(DatabaseAdapter.class);
        database.createPlayer("email", "hash", "salt", "username");
        Collection<String> emails = new ArrayList<>();
        emails.add("email");
        game.invitePlayers(emails);
    }

    private void invitePlayer2() {
        DatabaseAdapter database = Injector.getInjector().getInstance(DatabaseAdapter.class);
        database.createPlayer("emai", "has", "sal", "usernam");
        Collection<String> emails = new ArrayList<>();
        emails.add("emai");
        game.invitePlayers(emails);
    }
}
