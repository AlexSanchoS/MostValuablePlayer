package com.zamkovyi.mostvaluableplayer2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HandballPlayer extends Player{
    private String name;
    private int number;
    private int goalsMade;
    private int goalsReceived;

    @Override
    public String toString() {
        return "HandballPlayer{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", goalsMade=" + goalsMade +
                ", goalsReceived=" + goalsReceived +
                '}';
    }
}
