package com.zamkovyi.mostvaluableplayer2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
public class Match {
    private ArrayList<Team> teams = new ArrayList<>();
    private HashSet<Player> players = new HashSet<>();
    private Team winner;

    @Override
    public String toString() {
        return "Match{" +
                "teams=" + teams +
                ", players=" + players +
                ", winner=" + winner +
                '}';
    }
}
