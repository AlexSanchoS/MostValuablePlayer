package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.domain.BasketballPlayer;
import com.zamkovyi.mostvaluableplayer2.domain.Match;
import com.zamkovyi.mostvaluableplayer2.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("BasketballPlayerServiceImpl")
public class BasketballPlayerServiceImpl implements PlayerService<BasketballPlayer> {

    private static Pattern basketballPlayerPattern = Pattern.compile("[^;]+;[^;]+;\\d+;[^;]+;\\d+;\\d+;\\d+");

    @Override
    public BasketballPlayer parseLineToPlayer(String line) {
        Matcher mtch = basketballPlayerPattern.matcher(line);
        if (!mtch.matches()){
            throw new IllegalStateException(String.format(
                    "Line %s is not valid", line
            ));
        }
        String[] split = line.split(";");
        BasketballPlayer player = new BasketballPlayer();
        player.setName(split[0]);
        player.setNickname(split[1]);
        player.setNumber(Integer.parseInt(split[2]));
        player.setTeamName(split[3]);
        player.setScoredPoints(Integer.parseInt(split[4]));
        player.setRebounds(Integer.parseInt(split[5]));
        player.setAssists(Integer.parseInt(split[6]));
        player.setPointsForTeamVictory(player.getScoredPoints());
        ratingPointsCount(player);
        return player;
    }

    @Override
    public void ratingPointsCount(BasketballPlayer basketballPlayer) {
        int ratingPoints = basketballPlayer.getScoredPoints() * 2 +
                basketballPlayer.getRebounds() * 1 +
                basketballPlayer.getAssists() * 1;
        basketballPlayer.setRatingPoints(ratingPoints);
    }

    @Override
    public void addRatingPointsForWinners(Match match) {
        match.getPlayers()
                .stream()
                .filter(player -> player.getTeamName().equals(match.getWinner().getName()))
                .forEach(player -> player.addRatingPoints(10));
    }
}
