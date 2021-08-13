package com.zamkovyi.mostvaluableplayer2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Team {
    private String name;
    private ArrayList<Player> players = new ArrayList<>();
    int scoredPoint;

    public void addPoints(int scoredPoints) {
        this.scoredPoint+=scoredPoints;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                ", scoredPoint=" + scoredPoint +
                '}';
    }
}
