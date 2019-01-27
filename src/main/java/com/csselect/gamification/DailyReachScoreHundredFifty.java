package com.csselect.gamification;

import java.time.LocalDate;

/**
 * A daily challenge that will be completed once a player reaches 150 points
 * in one day.
 */
public class DailyReachScoreHundredFifty extends DailyChallenge {

    private final static String GERMAN_NAME = "Erreiche heute insgesamt 150 Punkte.";
    private final static String ENGLISH_NAME = "Achieve a total score of 150 today.";
    private int dailyScore;

    /**
     * Creates a daily challenge by setting the necessary values.
     */
    DailyReachScoreHundredFifty() {
        this.germanName = GERMAN_NAME;
        this.englishName = ENGLISH_NAME;
        this.date = LocalDate.now();
        this.completed = false;
        this.reward = 50;
        this.dailyScore = 0;
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
}
