package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.MostValuablePlayer2Application;
import com.zamkovyi.mostvaluableplayer2.domain.Match;
import com.zamkovyi.mostvaluableplayer2.domain.Player;
import com.zamkovyi.mostvaluableplayer2.domain.Tournament;
import com.zamkovyi.mostvaluableplayer2.service.TournamentService;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MostValuablePlayer2Application.class)
class TournamentServiceImplTest {


    @Autowired
    TournamentService underTest;


    @Test
    void shouldSetMvp() {
        Tournament tournament = new Tournament();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setNickname("p1");
        player1.setRatingPoints(10);
        player2.setNickname("p2");
        player2.setRatingPoints(20);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        tournament.setPlayers(players);

        underTest.setMvp(tournament);
        assertEquals(player2, tournament.getMvp());
    }

    @Test
    void shouldSetTwoPlayerListForTournament() {
        Tournament tournament = new Tournament();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setNickname("p1");
        player1.setRatingPoints(10);
        player2.setNickname("p2");
        player2.setRatingPoints(20);
        HashSet<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player2);
        Match match1 = new Match();
        Match match2 = new Match();
        match1.setPlayers(players);
        match2.setPlayers(players);
        ArrayList<Match> matches = new ArrayList<>();
        matches.add(match1);
        matches.add(match2);
        tournament.setMatches(matches);

        underTest.setPlayerListForTournament(tournament);

        assertEquals(2, tournament.getPlayers().size());
        assertNotEquals(tournament.getPlayers().get(0), tournament.getPlayers().get(1));
    }

    @Test
    void shouldFindPlayerByNicknameInTournamentIfExist() {
        Tournament tournament = new Tournament();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setNickname("p1");
        player1.setRatingPoints(10);
        player2.setNickname("p2");
        player2.setRatingPoints(20);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        tournament.setPlayers(players);

        Optional<Player> playerByNicknameInTournament = underTest.findPlayerByNicknameInTournament(tournament, player1.getNickname());

        assertEquals(player1, playerByNicknameInTournament.get());
    }

    @Test
    void shouldReturnEmptyOptionalPlayerByNicknameInTournamentIfNotExist() {
        Tournament tournament = new Tournament();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setNickname("p1");
        player1.setRatingPoints(10);
        player2.setNickname("p2");
        player2.setRatingPoints(20);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player2);
        tournament.setPlayers(players);

        Optional<Player> playerByNicknameInTournament = underTest.findPlayerByNicknameInTournament(tournament, player1.getNickname());

        assertTrue(playerByNicknameInTournament.isEmpty());
    }
}