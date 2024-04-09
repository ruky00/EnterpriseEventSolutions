package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
@Profile("prod")
public class AmazonS3Service {


    private final S3Client s3Client;
    private final String bucketName = "enterpriseeventsolutions";

    @Autowired
    public AmazonS3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String keyName, File file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.putObject(request, file.toPath());

        return "https://" + bucketName + ".s3.eu-west-2.amazonaws.com/"+ keyName;
    }
    public byte[] downloadFile(String keyName) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        ResponseInputStream<GetObjectResponse> objectResponse = s3Client.getObject(getObjectRequest);
        // Leer bytes desde InputStream y devolver
        // Puedes adaptar esto según tus necesidades
        return objectResponse.readAllBytes();
    }

    public void deleteFile(String keyName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
