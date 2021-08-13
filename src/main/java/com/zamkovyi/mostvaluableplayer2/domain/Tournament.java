package com.zamkovyi.mostvaluableplayer2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Tournament {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Match> matches = new ArrayList<>();
    private Player mvp;

    @Override
    public String toString() {
        return "Tournament{" +
                "players=" + players +
                ", matches=" + matches +
                ", mvp=" + mvp +
                '}';
    }
}
