package com.zamkovyi.mostvaluableplayer2.service.impl;

import com.zamkovyi.mostvaluableplayer2.dto.FileDTO;
import com.zamkovyi.mostvaluableplayer2.service.FileService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private List<Path> pathList = new ArrayList<>();


    @Override
    public void getPathListByFolderPath(String path) {

        try {
            pathList = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Path is not valid");
        }

    }

    @Override
    public ArrayList<FileDTO> getFileDTOList(String path) {
        getPathListByFolderPath(path);
        ArrayList<FileDTO> result = new ArrayList<>();

        pathList.stream().forEach(
                myPath -> {
                    FileDTO fileDTO = null;
                    try (FileInputStream inputStream = new FileInputStream(myPath.toString());
                         Scanner sc = new Scanner(inputStream, "UTF-8")) {
                        fileDTO = new FileDTO();
                        if (sc.hasNextLine()) {
                            fileDTO.setGameName(sc.nextLine());
                        }
                        while (sc.hasNextLine()) {
                            fileDTO.getLines().add(sc.nextLine());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    result.add(fileDTO);
                });
        return result;
    }

}
