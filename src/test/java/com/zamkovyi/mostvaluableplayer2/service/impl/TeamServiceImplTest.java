package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.MostValuablePlayer2Application;
import com.zamkovyi.mostvaluableplayer2.domain.Team;
import com.zamkovyi.mostvaluableplayer2.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MostValuablePlayer2Application.class)
class TeamServiceImplTest {

    @Autowired
    private TeamService underTest;

    @Test
    void shouldGetTeamFromListByNameIfExist() {
        Team team1 = new Team();
        team1.setName("team1");
        Team team2 = new Team();
        team2.setName("team2");
        ArrayList<Team> list = new ArrayList<>();
        list.add(team1);
        list.add(team2);
        Optional<Team> teamFromListByName = underTest.getTeamFromListByName(list, team2.getName());
        assertEquals(team2, teamFromListByName.get());
    }

    @Test
    void shouldGetEmptyOptionalFromListByNameIfNotExist() {
        ArrayList<Team> list = new ArrayList<>();
        Optional<Team> teamFromListByName = underTest.getTeamFromListByName(list, "team");
        assertTrue(teamFromListByName.isEmpty());
    }
}