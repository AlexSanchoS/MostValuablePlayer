package com.zamkovyi.mostvaluableplayer2.dto;

import java.util.ArrayList;

public class FileDTO {
    private String gameName;
    private ArrayList<String> lines = new ArrayList<>();

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

}
