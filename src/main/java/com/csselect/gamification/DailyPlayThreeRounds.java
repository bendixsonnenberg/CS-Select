package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player plays three games
 * in one day.
 */
public class DailyPlayThreeRounds extends DailyChallenge {

    private int dailyRounds;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    public DailyPlayThreeRounds() {
        this.name = "";
        this.description = "";
        this.date = null;
        this.completed = false;
        this.reward = 50;
        this.dailyRounds = 0;
    }

    public DailyPlayThreeRounds(LocalDate date, boolean completed, int dailyRounds) {
        this.name = "";
        this.description = "";
        this.date = date;
        this.completed = completed;
        this.reward = 50;
        this.dailyRounds = dailyRounds;
    }

    @Override
    public boolean checkFinished(PlayerStats stats) {
        dailyRounds++;

        boolean finished = dailyRounds >= 3;

        if (finished) {
            completed = true;
        }

        return finished;
    }

    @Override
    public void resetDaily() {
        completed = false;
        dailyRounds = 0;
    }
}
