package com.csselect.gamification;

import com.csselect.game.Round;
import com.csselect.user.Player;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Represent a sorting strategy. It sorts the players according to their points they reached all time.
 */
public class SortScoreLastWeek extends LeaderboardSortingStrategy {

    @Override
    protected Map<Player, Integer> sort(List<Player> players) {
        Map<Player, Integer> scoreLastWeek = new TreeMap<>();

        for (Player player : players) {
            int sum = 0;
            for (Round round : player.getRounds().stream()
                    .filter(r -> r.getTime().toLocalDate().isAfter(LocalDate.now().minusDays(7)))
                    .collect(Collectors.toList())) {
                sum += round.getPoints();
            }
            scoreLastWeek.put(player, sum);
        }

        Map<Player, Integer> sortedMap = scoreLastWeek.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }
}
