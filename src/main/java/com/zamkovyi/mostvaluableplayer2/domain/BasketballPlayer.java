package com.zamkovyi.mostvaluableplayer2.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasketballPlayer extends Player{
    private String name;
    private int number;
    private int scoredPoints;
    private int rebounds;
    private int assists;

}
