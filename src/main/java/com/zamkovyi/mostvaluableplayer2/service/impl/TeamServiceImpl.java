package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.domain.Team;
import com.zamkovyi.mostvaluableplayer2.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @Override
    public Optional<Team> getTeamFromListByName(ArrayList<Team> teams, String name) {
        return Optional.of(
                teams.stream().filter(team -> team.getName().equals(name)).findFirst()
        ).orElse(Optional.empty());
    }


}
