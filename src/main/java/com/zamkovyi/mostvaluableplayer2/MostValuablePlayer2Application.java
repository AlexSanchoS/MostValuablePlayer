package com.zamkovyi.mostvaluableplayer2;

import com.zamkovyi.mostvaluableplayer2.service.TournamentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class MostValuablePlayer2Application {

    private static final String path = "src/main/resources/files";


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MostValuablePlayer2Application.class);

        TournamentService service = context.getBean(TournamentService.class);

        service.tournamentProcessingFromFiles(path);
    }

}
