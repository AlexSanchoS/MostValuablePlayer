package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.domain.GameName;
import com.zamkovyi.mostvaluableplayer2.domain.Match;
import com.zamkovyi.mostvaluableplayer2.domain.Player;
import com.zamkovyi.mostvaluableplayer2.domain.Team;
import com.zamkovyi.mostvaluableplayer2.dto.FileDTO;
import com.zamkovyi.mostvaluableplayer2.service.MatchService;
import com.zamkovyi.mostvaluableplayer2.service.PlayerService;
import com.zamkovyi.mostvaluableplayer2.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    private final PlayerService handballPlayerService;

    private final PlayerService basketballPlayerService;

    private final TeamService teamService;

    @Autowired
    public MatchServiceImpl(@Qualifier("HandballPlayerServiceImpl") PlayerService handballPlayerService,
                            @Qualifier("BasketballPlayerServiceImpl") PlayerService basketballPlayerService,
                            TeamService teamService) {
        this.handballPlayerService = handballPlayerService;
        this.basketballPlayerService = basketballPlayerService;
        this.teamService = teamService;
    }


    @Override
    public Match parseFileDtoToMatch(FileDTO fileDTO) {
        if (fileDTO.getGameName().equals(GameName.BASKETBALL.getName())) {
            return getMatchFromDataDto(fileDTO, basketballPlayerService);
        }
        if (fileDTO.getGameName().equals(GameName.HANDBALL.getName())) {
            return getMatchFromDataDto(fileDTO, handballPlayerService);
        }
        throw new IllegalStateException(String.format("Unknown game %s", fileDTO.getGameName()));

    }

    @Override
    public Match getMatchFromDataDto(FileDTO fileDTO, PlayerService playerService) {
        Match match = new Match();
        for (String line : fileDTO.getLines()) {
            match.getPlayers().add(playerService.parseLineToPlayer(line));
        }
        if (fileDTO.getLines().size()>match.getPlayers().size()){
            throw new IllegalStateException("One of the players has two roles in one match");
        }
        setTeamsForMatch(match);
        setWinnerForMatch(match);
        handballPlayerService.addRatingPointsForWinners(match);
        return match;
    }

    @Override
    public void setTeamsForMatch(Match match){
        for (Player player: match.getPlayers()) {
            Optional<Team> optionalTeam = teamService.getTeamFromListByName(match.getTeams(), player.getTeamName());
            if (optionalTeam.isPresent()){
                optionalTeam.get().addPoints(player.getPointsForTeamVictory());
                optionalTeam.get().getPlayers().add(player);
            }else {
                Team team = new Team();
                team.setName(player.getTeamName());
                team.addPoints(player.getPointsForTeamVictory());
                team.getPlayers().add(player);
                match.getTeams().add(team);
            }
        }
        if (match.getTeams().size()!=2){
            throw new IllegalStateException("For match should be two teams");
        }
    }


    @Override
    public void setWinnerForMatch(Match match){
        match.getTeams().sort(Comparator.comparingInt(Team::getScoredPoint).reversed());
        if (match.getTeams().get(0).getScoredPoint()==(match.getTeams().get(1).getScoredPoint())){
            throw new IllegalStateException(String.format("The match %s has no winner", match));
        }
        match.setWinner(match.getTeams().get(0));
    }


}
