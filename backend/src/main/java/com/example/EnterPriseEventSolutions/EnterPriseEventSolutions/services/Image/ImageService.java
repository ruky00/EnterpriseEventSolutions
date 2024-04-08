package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.Image;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public interface ImageService {

    public String createImage(MultipartFile imageFile, String imageName, String userName) throws IOException;

    public void deleteImage(String image);


}
