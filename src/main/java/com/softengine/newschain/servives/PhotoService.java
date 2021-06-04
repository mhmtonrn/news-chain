package com.softengine.newschain.servives;

import com.softengine.newschain.models.dto.Photo;
import com.softengine.newschain.respository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    final private PhotoRepository photoRepo;

    public List<String> addPhoto(String title, List<MultipartFile> file) throws IOException {
        List<String> imageList = new ArrayList<>();
        for (MultipartFile mp:file) {
            Photo photo = new Photo(title);
            photo.setImage(new Binary(BsonBinarySubType.BINARY, mp.getBytes()));
            photo = photoRepo.insert(photo);
            imageList.add(photo.getId());
        }
        return imageList;
    }

    public Photo getPhoto(String id) { 
        return photoRepo.findById(id).get(); 
    }
}