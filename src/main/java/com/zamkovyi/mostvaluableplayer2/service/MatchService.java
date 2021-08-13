package com.zamkovyi.mostvaluableplayer2.service;

import com.zamkovyi.mostvaluableplayer2.domain.Match;
import com.zamkovyi.mostvaluableplayer2.dto.FileDTO;

public interface MatchService {

    Match parseFileDtoToMatch(FileDTO fileDTO);

    Match getMatchFromDataDto(FileDTO fileDTO, PlayerService playerService);

    void setTeamsForMatch(Match match);

    void setWinnerForMatch(Match match);
}
