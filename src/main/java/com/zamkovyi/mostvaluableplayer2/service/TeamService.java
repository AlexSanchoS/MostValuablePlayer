package com.zamkovyi.mostvaluableplayer2.service;

import com.zamkovyi.mostvaluableplayer2.domain.Team;

import java.util.ArrayList;
import java.util.Optional;

public interface TeamService {

    Optional<Team> getTeamFromListByName(ArrayList<Team> teams, String name);
}
