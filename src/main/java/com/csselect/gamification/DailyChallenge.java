package com.csselect.gamification;

import java.time.LocalDate;

/**
 * Represents an abstract DailyChallenge. This is a task that a player
 * can finish in order to get a higher score. All subclasses must
 * implement the checkFinished and resetDaily method.
 */
public abstract class DailyChallenge {

    String germanName;
    String englishName;
    LocalDate date;
    boolean completed;
    int reward;

    /**
     * This method is abstract and has to be implemented in all subclasses. This method
     * determines whether the goal to finish the daily challenge has been reached.
     * @param stats The corresponding PlayerStats object of a player.
     * @return True, if challenge has been completed (after playing a round). False, otherwise.
     */
    public abstract boolean checkFinished(PlayerStats stats);

    /**
     * Resets the daily challenge, meaning setting the necessary attributes back to their
     * default value.
     */
    public abstract void resetDaily();

    /**
     * Gets the German name of the daily challenge.
     * @return The name of the daily.
     */
    public String getGermanName() {
        return germanName;
    }

    /**
     * Gets the English name of the daily challenge.
     * @return The name of the daily.
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * Gets the date of the daily challenge.
     * @return The date if the daily.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the daily challenge to the specified date.
     * @param currentDate The date to be set.
     */
    public void setDate(LocalDate currentDate) {
        date = currentDate;
    }

    /**
     * Checks if the daily challenge has already been completed.
     * @return True, if challenge is completed. False, if challenge has not been completed yet.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Gets the reward for the challenge. This is a small amount of points between 50 and 100.
     * @return The reward for the daily.
     */
    public int getReward() {
        return reward;
    }



}
