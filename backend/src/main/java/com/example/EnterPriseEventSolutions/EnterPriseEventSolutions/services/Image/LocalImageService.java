package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.Image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service("storageService")
@Profile({"dev", "test"})
public class LocalImageService implements ImageService {


    @Value("${spring.web.resources.static-locations}")
    private String STATIC_FOLDER;

    private String staticFolder() {
        return System.getProperty("user.dir") + "/" + STATIC_FOLDER.split(":")[1];
    }

    @Override
    public String createImage(MultipartFile multiPartFile, String imageName, String userName) {
        String fileName = "image_" + UUID.randomUUID() + "_" +multiPartFile.getOriginalFilename();

        File file = new File(staticFolder() + fileName);
        try {
            multiPartFile.transferTo(file);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save image locally", ex);
        }
       final String baseUrl = "https://127.0.0.1:8443";
        return baseUrl + "/" + fileName;
    }

    @Override
    public void deleteImage(String image_url) {
        String[] tokens = image_url.split("/");
        String image_name = tokens[tokens.length -1 ];
        Path path = FileSystems.getDefault().getPath(staticFolder() + "events/" + image_name);
        try {
            Files.deleteIfExists(path);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't delete local image");
        }
    }
}
