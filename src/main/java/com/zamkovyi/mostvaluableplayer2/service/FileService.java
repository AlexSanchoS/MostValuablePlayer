package com.zamkovyi.mostvaluableplayer2.service;

import com.zamkovyi.mostvaluableplayer2.dto.FileDTO;

import java.util.ArrayList;

public interface FileService {

    void getPathListByFolderPath(String path);

    ArrayList<FileDTO> getFileDTOList(String path);
}
