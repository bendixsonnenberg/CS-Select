package com.csselect.gamification;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * An enumeration for all the existing achievements. Every instance
 * has to implement the method getState.
 */
public enum AchievementType {

    /**
     * This type of achievement is completed after having played one round.
     */
    PLAY_ROUND_ONE("roundOneName", "roundOneDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 1) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having played five rounds.
     */
    PLAY_ROUND_FIVE("roundFiveName", "roundFiveDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 5) {
                return AchievementState.FINISHED;
            }

            if (stats.getRoundsPlayed() >= 1) {
                return AchievementState.SHOWN;
            }

            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having played ten rounds.
     */
    PLAY_ROUND_TEN("roundTenName", "roundTenDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 10) {
                return AchievementState.FINISHED;
            }

            if (stats.getRoundsPlayed() >= 5) {
                return AchievementState.SHOWN;
            }

            if (stats.getRoundsPlayed() >= 1) {
                return AchievementState.CONCEALED;
            }

            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having played 42 rounds.
     */
    PLAY_ROUND_FORTYTWO("roundFortyTwoName", "roundFortyTwoDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 42) {
                return AchievementState.FINISHED;
            }

            if (stats.getRoundsPlayed() >= 10) {
                return AchievementState.SHOWN;
            }

            if (stats.getRoundsPlayed() >= 5) {
                return AchievementState.CONCEALED;
            }

            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having played 100 rounds.
     */
    PLAY_ROUND_HUNDRED("roundHundredName", "roundHundredDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 100) {
                return AchievementState.FINISHED;
            }

            if (stats.getRoundsPlayed() >= 42) {
                return AchievementState.SHOWN;
            }

            if (stats.getRoundsPlayed() >= 10) {
                return AchievementState.CONCEALED;
            }

            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a streak of two.
     */
    STREAK_TWO("streakTwoName", "streakTwoDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getHighestStreak() >= 2) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having reached a streak of five.
     */
    STREAK_FIVE ("streakFiveName", "streakFiveDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getHighestStreak() >= 5) {
                return AchievementState.FINISHED;
            }

            if (stats.getHighestStreak() >= 2) {
                return AchievementState.SHOWN;
            }
            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having reached a streak of ten.
     */
    STREAK_TEN("streakTenName", "streakTenDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getHighestStreak() >= 10) {
                return AchievementState.FINISHED;
            }

            if (stats.getHighestStreak() >= 5) {
                return AchievementState.SHOWN;
            }

            if (stats.getHighestStreak() >= 2) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having completed one daily challenge.
     */
    DAILY_ONE("dailyOneName", "dailyOneDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getDailiesCompleted() >= 1) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having completed three daily challenges.
     */
    DAILY_THREE("dailyThreeName", "dailyThreeDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getDailiesCompleted() >= 3) {
                return AchievementState.FINISHED;
            }

            if (stats.getDailiesCompleted() >= 1) {
                return AchievementState.SHOWN;
            }
            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having completed seven daily challenges.
     */
    DAILY_SEVEN("dailySevenName", "dailySevenDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getDailiesCompleted() >= 7) {
                return AchievementState.FINISHED;
            }

            if (stats.getDailiesCompleted() >= 3) {
                return AchievementState.SHOWN;
            }

            if (stats.getDailiesCompleted() >= 1) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 100.
     */
    TOTAL_SCORE_HUNDRED("totalScoreHundredName", "totalScoreHundredDesc")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 100) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 250.
     */
    TOTAL_SCORE_TWOHUNDREDFIFTY("totalScoreTwoHundredFiftyName", "totalScoreTwoHundredFiftyDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 250) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 100) {
                return AchievementState.SHOWN;
            }
            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 500.
     */
    TOTAL_SCORE_FIVEHUNDRED("totalScoreFiveHundredName", "totalScoreFiveHundredDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 500) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 250) {
                return AchievementState.SHOWN;
            }

            if (stats.getScore() >= 100) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 1000.
     */
    TOTAL_SCORE_THOUSAND("totalScoreThousandName", "totalScoreThousandDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 1000) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 500) {
                return AchievementState.SHOWN;
            }

            if (stats.getScore() >= 250) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 2000.
     */
    TOTAL_SCORE_TWOTHOUSAND("totalScoreTwoThousandName", "totalScoreTwoThousandDesc")  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 2000) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 1000) {
                return AchievementState.SHOWN;
            }

            if (stats.getScore() >= 500) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 5000.
     */
    TOTAL_SCORE_FIVETHOUSAND("totalScoreFiveThousandName", "totalScoreFiveThousandDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 5000) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 2000) {
                return AchievementState.SHOWN;
            }

            if (stats.getScore() >= 1000) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a round score of 60.
     */
    ROUND_SCORE_SIXTY("roundScoreSixtyName", "roundScoreSixtyDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 60) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having reached a round score of 70.
     */
    ROUND_SCORE_SEVENTY("roundScoreSeventyName", "roundScoreSeventyDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 70) {
                return AchievementState.FINISHED;
            }

            if (stats.getMaxRoundScore() >= 60) {
                return AchievementState.SHOWN;
            }
            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having reached a round score of 80.
     */
    ROUND_SCORE_EIGHTY("roundScoreEightyName", "roundScoreEightyDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 80) {
                return AchievementState.FINISHED;
            }

            if (stats.getMaxRoundScore() >= 70) {
                return AchievementState.SHOWN;
            }

            if (stats.getMaxRoundScore() >= 60) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a round score of 90.
     */
    ROUND_SCORE_NINETY("roundScoreNinetyName", "roundScoreNinetyDesc") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 90) {
                return AchievementState.FINISHED;
            }

            if (stats.getMaxRoundScore() >= 80) {
                return AchievementState.SHOWN;
            }

            if (stats.getMaxRoundScore() >= 70) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    };

    private final String nameKey;
    private final String descKey;


    AchievementType(String nameKey, String descKey) {
        this.nameKey = nameKey;
        this.descKey = descKey;
    }

    /**
     * Checks the progress of an achievement and then returns a new achievement that
     * represents the progress of that achievement.
     * @param stats The player's stats.
     * @return A new Achievement with the corresponding state and type.
     */
    public final Achievement checkProgress(PlayerStats stats) {
        AchievementState state = getState(stats);
        return new Achievement(state, this);
    }

    /**
     * Gets the current state of the achievement type determined by the players'
     * progress.
     * @param stats The player's stats.
     * @return The current state of the achievement.
     */
    protected abstract AchievementState getState(PlayerStats stats);

    /**
     * Gets the name of the achievement type in the specified language.
     * @param lang The language code in ISO 369-1 format.
     * @return The name in the specified language. Assertion error if language code is not known.
     */
    public final String getName(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("locale/Locale", locale,
                ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));
        return bundle.getString(nameKey);
    }

    /**
     * Gets the description of the achievement in the specified language.
     * @param lang The language code in ISO 639-1 format.
     * @return The The description in the specified language. Assertion error if language code is not known.
     */
    public final String getDescription(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("locale/Locale", locale,
                ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES));
        return bundle.getString(descKey);
    }

}
