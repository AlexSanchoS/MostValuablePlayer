package com.zamkovyi.mostvaluableplayer2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Player {

    String nickname;
    String teamName;
    int ratingPoints;
    int pointsForTeamVictory;



    public void addRatingPoints(int points){
        this.ratingPoints+=points;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", ratingPoints=" + ratingPoints +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null){
            return false;
        }
        if (!(obj instanceof Player)){
            return false;
        }
        return this.getNickname().equals(((Player) obj).nickname);
    }
}
