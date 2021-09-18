package com.softengine.newschain.servives;

import com.softengine.newschain.models.dto.Photo;
import com.softengine.newschain.respository.PhotoRepository;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${minio.server.name}")
    private String minioServer;

    @Value("${minio.server.port}")
    private String minioPort;

    @Value("${minio.server.external.name}")
    private String externalServerName;

    private MinioClient minioClient;


    private static final String NEWS_BUCKET = "news-bucket";


    final private PhotoRepository photoRepo;

    @Transactional
    public String addPhoto(MultipartFile file) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        Photo photo = new Photo(file.getName());
        photo = photoRepo.insert(photo);
        photo.setTitle(photo.getId()+"."+file.getOriginalFilename().split("\\.")[1]);
        photo=photoRepo.save(photo);
        boolean found = getMinioClient().bucketExists(BucketExistsArgs.builder().bucket(NEWS_BUCKET).build());
        if (!found) {
            getMinioClient().makeBucket(MakeBucketArgs.builder().bucket(NEWS_BUCKET).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }


        ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());

        getMinioClient().putObject(
                    PutObjectArgs.builder().bucket(NEWS_BUCKET).object(photo.getTitle()).stream(
                            bais, bais.available(), -1)
                            .build());
        bais.close();
        System.out.println("my-objectname is uploaded successfully");

        return photo.getId();
    }

    public Photo getPhoto(String id) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        Optional<Photo> photo = photoRepo.findById(id);
        if (photo.isPresent()){
//            String url =  getMinioClient().getPresignedObjectUrl( GetPresignedObjectUrlArgs.builder() .method(Method.GET) .bucket(NEWS_BUCKET).object(photo.get().getTitle())   .expiry(2, TimeUnit.HOURS)     .build());
            photo.get().setImageUrl("https://"+externalServerName+"/"+NEWS_BUCKET+"/"+photo.get().getTitle());
            System.out.println(photo.get().getImageUrl());
            return photo.get();

        }
        return null;
    }


    public MinioClient getMinioClient() {
        logger.info("connecting minio server http://"+ minioServer+":"+minioPort);

        if (minioClient == null){
            minioClient= MinioClient.builder()
                    .endpoint("http://"+minioServer+":"+minioPort)
                    .credentials("minio_access_key", "minio_secret_key")
                    .build();
        }
        return minioClient;
    }
}