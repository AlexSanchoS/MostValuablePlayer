package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.MostValuablePlayer2Application;
import com.zamkovyi.mostvaluableplayer2.domain.*;
import com.zamkovyi.mostvaluableplayer2.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MostValuablePlayer2Application.class)
class BasketballPlayerServiceImplTest {

    @Autowired
    @Qualifier("BasketballPlayerServiceImpl")
    private PlayerService underTest;

    @Test
    void shouldThrowExceptionWhenLineIsNotValid() {
        String invalidLine = "player 1;nick1;4;10;Team A;2;7";
        assertThatThrownBy(() -> underTest.parseLineToPlayer(invalidLine))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format(
                        "Line %s is not valid", invalidLine));
    }

    @Test
    void shouldReturnValidBasketballPlayer(){
        String line = "player 1;nick1;4;Team A;10;2;7";
        BasketballPlayer rightAnswer = new BasketballPlayer();
        rightAnswer.setName("player 1");
        rightAnswer.setNickname("nick1");
        rightAnswer.setNumber(4);
        rightAnswer.setTeamName("Team A");
        rightAnswer.setScoredPoints(10);
        rightAnswer.setRebounds(2);
        rightAnswer.setAssists(7);
        BasketballPlayer player = (BasketballPlayer) underTest.parseLineToPlayer(line);
        assertEquals(rightAnswer.getName(), player.getName());
        assertEquals(rightAnswer.getNickname(), player.getNickname());
        assertEquals(rightAnswer.getNumber(), player.getNumber());
        assertEquals(rightAnswer.getScoredPoints(), player.getScoredPoints());
        assertEquals(rightAnswer.getRebounds(), player.getRebounds());
        assertEquals(rightAnswer.getAssists(), player.getAssists());
    }

    @Test
    void shouldCountRatingPoints() {
        BasketballPlayer player = new BasketballPlayer();
        player.setName("player 1");
        player.setNickname("nick1");
        player.setNumber(4);
        player.setTeamName("Team A");
        player.setScoredPoints(10);
        player.setRebounds(2);
        player.setAssists(7);
        underTest.ratingPointsCount(player);
        assertEquals(29,player.getRatingPoints());
    }

    @Test
    void shouldAddRatingPointsForWinners(){
        Match match = new Match();
        Team winnerTeam = new Team();
        winnerTeam.setName("win");
        match.setWinner(winnerTeam);
        BasketballPlayer playerWin = new BasketballPlayer();
        BasketballPlayer playerLose = new BasketballPlayer();
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