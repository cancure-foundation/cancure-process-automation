package org.cancure.cpa.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    public void saveFile(String path, MultipartFile inputFile) throws IOException {
        File file = new File(path +"/"+ inputFile.getOriginalFilename());
        inputFile.transferTo(file);
    }
}
