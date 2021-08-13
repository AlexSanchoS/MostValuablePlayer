package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.MostValuablePlayer2Application;
import com.zamkovyi.mostvaluableplayer2.domain.*;
import com.zamkovyi.mostvaluableplayer2.dto.FileDTO;
import com.zamkovyi.mostvaluableplayer2.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = MostValuablePlayer2Application.class)
class MatchServiceImplTest {

    @Autowired
    private MatchService underTest;

    @Test
    void shouldThrowExceptionWhenUnknownSport(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setGameName("Unknown");
        assertThatThrownBy(() -> underTest.parseFileDtoToMatch(fileDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Unknown game %s", fileDTO.getGameName()));
    }

    @Test
    void shouldSetTwoDifferentTeamForMatch() {
        HandballPlayer player1 = new HandballPlayer();
        player1.setNickname("p1");
        player1.setTeamName("team1");
        HandballPlayer player2 = new HandballPlayer();
        player2.setNickname("p2");
        player2.setTeamName("team1");
        HandballPlayer player3 = new HandballPlayer();
        player3.setNickname("p3");
        player3.setTeamName("team2");
        Match match = new Match();
        HashSet<Player> players= new HashSet<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        match.setPlayers(players);

        underTest.setTeamsForMatch(match);

        assertEquals(2,match.getTeams().size());
        assertNotEquals(match.getTeams().get(0), match.getTeams().get(1));
    }


    @Test
    void shouldParseBasketballMatchWithTwoTeams(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setGameName(GameName.BASKETBALL.getName());
        ArrayList<String> lines = new ArrayList<>();
        String line1 = "player 1;nick1;4;Team A;10;2;7";
        String line2 = "player 2;nick2;4;Team B;15;2;7";
        lines.add(line1);
        lines.add(line2);
        fileDTO.setLines(lines);

        Match match = underTest.parseFileDtoToMatch(fileDTO);

        assertEquals(2, match.getTeams().size());
        assertEquals(2, match.getPlayers().size());

    }

    @Test
    void shouldParseHandballMatchWithTwoTeams(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setGameName(GameName.HANDBALL.getName());
        ArrayList<String> lines = new ArrayList<>();
        String line1 = "player 1;nick1;4;Team A;0;20";
        String line2 = "player 4;nick4;16;Team B;1;25";
        lines.add(line1);
        lines.add(line2);
        fileDTO.setLines(lines);

        Match match = underTest.parseFileDtoToMatch(fileDTO);

        assertEquals(2, match.getTeams().size());
        assertEquals(2, match.getPlayers().size());
    }

    @Test
    void shouldSetWinnerForMatch(){
        Team team1 = new Team();
        team1.setName("team1");
        team1.setScoredPoint(20);
        Team team2 = new Team();
        team2.setName("team2");
        team2.setScoredPoint(40);
        Match match = new Match();
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        match.setTeams(teams);

        underTest.setWinnerForMatch(match);

        assertEquals(team2, match.getWinner());
    }

    @Test
    void shouldThrowExceptionWhenMatchHasNoWinner(){
        Team team1 = new Team();
        team1.setName("team1");
        team1.setScoredPoint(20);
        Team team2 = new Team();
        team2.setName("team2");
        team2.setScoredPoint(20);
        Match match = new Match();
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        match.setTeams(teams);

        assertThatThrownBy(() -> underTest.setWinnerForMatch(match))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format(
                        "The match %s has no winner", match));
    }

    @Test
    void shouldThrowExceptionWhenMatchHasOneTeam(){
        BasketballPlayer player1 = new BasketballPlayer();
        player1.setNickname("p1");
        player1.setTeamName("team1");
        BasketballPlayer player2 = new BasketballPlayer();
        player2.setNickname("p2");
        player2.setTeamName("team1");
        Match match = new Match();
        HashSet<Player> players= new HashSet<>();
        players.add(player1);
        players.add(player2);
        match.setPlayers(players);

        assertThatThrownBy(() -> underTest.setTeamsForMatch(match))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("For match should be two teams");
    }



    @Test
    void shouldThrowExceptionWhenPlayerHasTwoRolesInOneMatch(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setGameName(GameName.BASKETBALL.getName());
        ArrayList<String> lines = new ArrayList<>();
        String line1 = "player 1;nick1;4;Team A;10;2;7";
        String line2 = "player 2;nick1;4;Team B;15;2;7";
        lines.add(line1);
        lines.add(line2);
        fileDTO.setLines(lines);

        assertThatThrownBy(() -> underTest.parseFileDtoToMatch(fileDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("One of the players has two roles in one match");
    }


}