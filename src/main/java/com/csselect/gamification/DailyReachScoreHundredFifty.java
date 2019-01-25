package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player reaches 150 points
 * in one day.
 */
public class DailyReachScoreHundredFifty extends DailyChallenge {

    private final static String GERMAN_NAME = "Erreiche heute insgesamt 100 Punkte.";
    private final static String ENGLISH_NAME = "Achieve a total score of 100 today.";
    private int dailyScore;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    public DailyReachScoreHundredFifty() {
        this.germanName = GERMAN_NAME;
        this.englishName = ENGLISH_NAME;
        this.date = null;
        this.completed = false;
        this.reward = 50;
        this.dailyScore = 0;
    }

    public DailyReachScoreHundredFifty(LocalDate date, boolean completed, int dailyScore) {
        this.date = date;
        this.completed = completed;
        this.reward = 50;
        this.dailyScore = dailyScore;
    }

    @Override
    public boolean checkFinished(PlayerStats stats) {
        dailyScore += stats.getLastScore();

        boolean finished = dailyScore >= 150;

        if (finished) {
            completed = true;
        }

        return finished;
    }

    @Override
    public void resetDaily() {
        completed = false;
        dailyScore = 0;
    }
}
