package com.csselect.gamification;

/**
 * Represents an Achievement. Achievements are represented by their type and
 * change their state depending on the player's current progress.
 */
public class Achievement {

    private String name;
    private String description;
    private AchievementState state;
    private AchievementType type;

    /**
     * Creates a new Achievement by setting its type and the current state.
     * @param state the current state of the achievement for the player.
     * @param type The type of the achievement.
     */
    public Achievement(AchievementState state, AchievementType type) {
        this.state = state;
        this.type = type;
    }

    /**
     * Gets the name of the achievement.
     * @return The name of the achievement.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the achievement.
     * @return The description of the achievement.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the state of the achievement.
     * @return The state of the achievement.
     */
    public AchievementState getState() {
        return state;
    }

    /**
     * Gets the type of the achievement.
     * @return The type of the achievement.
     */
    public AchievementType getType() {
        return type;
    }
}
