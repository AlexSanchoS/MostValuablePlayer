package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.domain.HandballPlayer;
import com.zamkovyi.mostvaluableplayer2.domain.Match;
import com.zamkovyi.mostvaluableplayer2.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("HandballPlayerServiceImpl")
public class HandballPlayerServiceImpl implements PlayerService<HandballPlayer> {

    private static Pattern handballPlayerPattern = Pattern.compile("[^;]+;[^;]+;\\d+;[^;]+;\\d+;\\d+");

    @Override
    public HandballPlayer parseLineToPlayer(String line) {
        String[] split = line.split(";");
        Matcher mtch = handballPlayerPattern.matcher(line);
        if (!mtch.matches()){
            throw new IllegalStateException(String.format(
                    "Line %s is not valid", line
            ));
        }
        HandballPlayer player = new HandballPlayer();
        player.setName(split[0]);
        player.setNickname(split[1]);
        player.setNumber(Integer.parseInt(split[2]));
        player.setTeamName(split[3]);
        player.setGoalsMade(Integer.parseInt(split[4]));
        player.setGoalsReceived(Integer.parseInt(split[5]));
        player.setPointsForTeamVictory(player.getGoalsMade());
        ratingPointsCount(player);
        return player;
    }

    @Override
    public void ratingPointsCount(HandballPlayer handballPlayer) {
        int ratingPoints = handballPlayer.getGoalsMade() * 2 -
                handballPlayer.getGoalsReceived() * 1;
        handballPlayer.setRatingPoints(ratingPoints);
    }



    @Override
    public void addRatingPointsForWinners(Match match) {
        match.getPlayers()
                .stream()
                .filter(player -> player.getTeamName().equals(match.getWinner().getName()))
                .forEach(player -> player.addRatingPoints(10));
    }
}
