package com.softengine.newschain.servives;

import com.softengine.newschain.models.dto.Photo;
import com.softengine.newschain.respository.PhotoRepository;
import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoService {
    private static final String NEWS_BUCKET = "news-bucket";
    private MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("minio_access_key", "minio_secret_key")
                    .build();

    final private PhotoRepository photoRepo;

    @Transactional
    public String addPhoto(MultipartFile file) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        Photo photo = new Photo(file.getName());
        photo = photoRepo.insert(photo);
        photo.setTitle(photo.getId()+"."+file.getOriginalFilename().split("\\.")[1]);
        photo=photoRepo.save(photo);
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(NEWS_BUCKET).build());
        if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(NEWS_BUCKET).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }


        ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());

        minioClient.putObject(
                    PutObjectArgs.builder().bucket(NEWS_BUCKET).object(photo.getTitle()).stream(
                            bais, bais.available(), -1)
                            .build());
        bais.close();
        System.out.println("my-objectname is uploaded successfully");

        return photo.getTitle();
    }

    public Photo getPhoto(String id) { 
        return photoRepo.findById(id).get(); 
    }
}