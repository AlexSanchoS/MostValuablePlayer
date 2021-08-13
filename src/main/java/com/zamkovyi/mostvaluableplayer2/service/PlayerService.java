package com.zamkovyi.mostvaluableplayer2.service;

import com.zamkovyi.mostvaluableplayer2.domain.Match;
import com.zamkovyi.mostvaluableplayer2.domain.Player;

public interface PlayerService<T extends Player>{

    T parseLineToPlayer(String line);

    void ratingPointsCount(T player);

    void addRatingPointsForWinners(Match match);
}
