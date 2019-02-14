package com.csselect.game.gamecreation.patterns;

/**
 * The Pattern class represents a memento which can store a {@link GameOptions} object for creating games.
 */
public class Pattern {
    private String title;
    private GameOptions gameOptions;

    /**
     * This method creates a new Pattern-Object which is there for storing GameOptions
     * @param gameOptions Object of GameOption representing options of a game (e.g Title, mode, ...)
     * @param title How the pattern is gonna be named
     */
    public Pattern(GameOptions gameOptions, String title) {
        this.title = title;
        this.gameOptions = gameOptions;
    }

    /**
     * This method returns the String representing the title of the pattern
     * @return Name of the pattern
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for game options. Creates a cloned object to not manipulate the old object.
     * @return {@link GameOptions} object saved in Pattern
     */
    public GameOptions getGameOptions() {
        return (GameOptions) gameOptions.clone();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Pattern)) {
            return false;
        } else {
            Pattern pattern = (Pattern) o;
            return title.equals(pattern.title);
        }
    }

    @Override
    public final int hashCode() {
        return title.hashCode();
    }
}
