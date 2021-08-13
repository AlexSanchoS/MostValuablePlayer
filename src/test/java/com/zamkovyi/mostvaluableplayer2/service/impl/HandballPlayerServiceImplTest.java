package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.MostValuablePlayer2Application;
import com.zamkovyi.mostvaluableplayer2.domain.HandballPlayer;
import com.zamkovyi.mostvaluableplayer2.domain.Match;
import com.zamkovyi.mostvaluableplayer2.domain.Player;
import com.zamkovyi.mostvaluableplayer2.domain.Team;
import com.zamkovyi.mostvaluableplayer2.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MostValuablePlayer2Application.class)
class HandballPlayerServiceImplTest {

    @Autowired
    @Qualifier("HandballPlayerServiceImpl")
    private PlayerService underTest;

    @Test
    void shouldThrowExceptionWhenLineIsNotValid() {
        String invalidLine = "player 1;nick1;4;Team A;0;a";
        assertThatThrownBy(() -> underTest.parseLineToPlayer(invalidLine))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format(
                        "Line %s is not valid", invalidLine));
    }

    @Test
    void shouldReturnValidHandballPlayer(){
        String line = "player 1;nick1;4;Team A;0;10";
        HandballPlayer rightAnswer = new HandballPlayer();
        rightAnswer.setName("player 1");
        rightAnswer.setNickname("nick1");
        rightAnswer.setNumber(4);
        rightAnswer.setTeamName("Team A");
        rightAnswer.setGoalsMade(0);
        rightAnswer.setGoalsReceived(10);
        HandballPlayer player = (HandballPlayer) underTest.parseLineToPlayer(line);
        assertEquals(rightAnswer.getName(), player.getName());
        assertEquals(rightAnswer.getNickname(), player.getNickname());
        assertEquals(rightAnswer.getNumber(), player.getNumber());
        assertEquals(rightAnswer.getGoalsMade(), player.getGoalsMade());
        assertEquals(rightAnswer.getGoalsReceived(), player.getGoalsReceived());
    }

    @Test
    void shouldCountRatingPoints() {
        HandballPlayer player = new HandballPlayer();
        player.setName("player 1");
        player.setNickname("nick1");
        player.setNumber(4);
        player.setTeamName("Team A");
        player.setGoalsMade(2);
        player.setGoalsReceived(10);
        underTest.ratingPointsCount(player);
        assertEquals(-6,player.getRatingPoints());
    }

    @Test
    void shouldAddRatingPointsForWinners(){
        Match match = new Match();
        Team winnerTeam = new Team();
        winnerTeam.setName("win");
        match.setWinner(winnerTeam);
        HandballPlayer playerWin = new HandballPlayer();
        HandballPlayer playerLose = new HandballPlayer();
        playerWin.setNickname("pl1");
        playerWin.setRatingPoints(5);
        playerWin.setTeamName(winnerTeam.getName());
        playerLose.setNickname("pl2");
        playerLose.setRatingPoints(10);
        playerLose.setTeamName("lose");
        HashSet<Player> players = new HashSet<>();
        players.add(playerLose);
        players.add(playerWin);
        match.setPlayers(players);

        underTest.addRatingPointsForWinners(match);

        assertEquals(15, playerWin.getRatingPoints());
        assertEquals(10, playerLose.getRatingPoints());

    }

}