package com.zamkovyi.mostvaluableplayer2.domain;

public enum GameName {
    BASKETBALL("BASKETBALL"), HANDBALL("HANDBALL");

    private String name;


    GameName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
