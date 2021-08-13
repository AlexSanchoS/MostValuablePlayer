package com.zamkovyi.mostvaluableplayer2.service;

import com.zamkovyi.mostvaluableplayer2.domain.Player;
import com.zamkovyi.mostvaluableplayer2.domain.Tournament;

import java.util.Optional;

public interface TournamentService {

    void tournamentProcessingFromFiles(String path);

    void setMvp(Tournament tournament);

    void setPlayerListForTournament(Tournament tournament);

    Optional<Player> findPlayerByNicknameInTournament(Tournament tournament, String nickname);
}
