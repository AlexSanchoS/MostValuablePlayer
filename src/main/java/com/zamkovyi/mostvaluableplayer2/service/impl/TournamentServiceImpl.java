package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.domain.Match;
import com.zamkovyi.mostvaluableplayer2.domain.Player;
import com.zamkovyi.mostvaluableplayer2.domain.Tournament;
import com.zamkovyi.mostvaluableplayer2.dto.FileDTO;
import com.zamkovyi.mostvaluableplayer2.service.FileService;
import com.zamkovyi.mostvaluableplayer2.service.MatchService;
import com.zamkovyi.mostvaluableplayer2.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final FileService fileService;
    private final MatchService matchService;

    @Autowired
    public TournamentServiceImpl(FileService fileService, MatchService matchService) {
        this.fileService = fileService;
        this.matchService = matchService;
    }

    private List<FileDTO> fileDTOs;


    @Override
    public void tournamentProcessingFromFiles(String path) {

        fileDTOs = fileService.getFileDTOList(path);

        Tournament tournament = new Tournament();
        for (FileDTO fileDTO : fileDTOs) {
            Match matchByFileDTO = matchService.parseFileDtoToMatch(fileDTO);
            tournament.getMatches().add(matchByFileDTO);
        }

        setPlayerListForTournament(tournament);
        setMvp(tournament);
        System.out.println(tournament.getMvp());

    }

    @Override
    public void setMvp(Tournament tournament) {
        tournament.setMvp(tournament.getPlayers().stream()
                .max(Comparator.comparingInt(Player::getRatingPoints))
                .get());
    }

    @Override
    public void setPlayerListForTournament(Tournament tournament) {
        for (Match match : tournament.getMatches()) {
            for (Player player : match.getPlayers()) {
                Optional<Player> playerByNickname = findPlayerByNicknameInTournament(tournament, player.getNickname());
                if (playerByNickname.isPresent()) {
                    playerByNickname.get().addRatingPoints(player.getRatingPoints());
                } else {
                    Player newPlayer = new Player();
                    newPlayer.setNickname(player.getNickname());
                    newPlayer.setRatingPoints(player.getRatingPoints());
                    tournament.getPlayers().add(newPlayer);
                }
            }
        }
    }

    @Override
    public Optional<Player> findPlayerByNicknameInTournament(Tournament tournament, String nickname) {
        return Optional.of(tournament.getPlayers().stream()
                .filter(player -> player.getNickname().equals(nickname))
                .findFirst()).orElse(Optional.empty());
    }

}
