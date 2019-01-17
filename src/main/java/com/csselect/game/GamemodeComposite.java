package com.csselect.game;

import com.csselect.user.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The GamemodeComposite class represents a concrete game mode {@link Gamemode} that combines different game modes into
 * one object by using the composite pattern. It randomly creates a concrete round {@link Round} from the different
 * game modes. {@link Gamemode} it contains.
 */
public class GamemodeComposite extends Gamemode {

    private List<Gamemode> gamemodes;

    /**
     * Constructor for a game mode composite object
     */
    public GamemodeComposite() {
        this.gamemodes = new ArrayList<>();
    }

    /**
     * Creates a round {@link Round} object from one of the game modes {@link Gamemode} it contains, which one
     * is chosen randomly
     * @param player the player {@link Player} who plays the round {@link Round}
     * @param number the number rounds {@link Round} that have already been started in a game {@link Game}
     * @return the randomly created round {@link Round}
     */
    public Round createRound(Player player, int number) {
        if(player == null || this.gamemodes.size() == 0) {
            return null;
        }

        int numberOfGamemodes = this.gamemodes.size();

        int randomMode = (int) (Math.random() * numberOfGamemodes);

        return this.gamemodes.get(randomMode).createRound(player, number);
    }

    /**
     * Adds a game mode {@link Gamemode} to the set of game modes {@link Gamemode}
     * @param gamemode the game mode {@link Gamemode} to be added
     */
    public void add(Gamemode gamemode) {
        if(gamemode == null || this.gamemodes.contains(gamemode)) {
            return;
        }
        this.gamemodes.add(gamemode);
    }

    /**
     * Deletes a game mode {@link Gamemode} from the set of game modes {@link Gamemode}
     * @param gamemode the game mode {@link Gamemode} to be deleted
     */
    public void delete(Gamemode gamemode) {
            this.gamemodes.remove(gamemode);
    }
}
