package com.softengine.newschain.api;

import com.softengine.newschain.models.dto.Photo;
import com.softengine.newschain.models.dto.Video;
import com.softengine.newschain.servives.PhotoService;
import com.softengine.newschain.servives.VideoService;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("api/multimedia")
@RequiredArgsConstructor
public class MultiMediaApi {

    private final PhotoService photoService;
    private final VideoService videoService;


    @PostMapping(value = "/photos/add" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String>addPhoto( @RequestParam("file") MultipartFile image) throws Exception {
        return ResponseEntity.ok(photoService.addPhoto(image));
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<Photo> getPhoto(@PathVariable String id) {
        Photo photo = photoService.getPhoto(id);
        //Base64.getEncoder().encodeToString(photo.getImage().getData());
        return ResponseEntity.ok(photo);
    }

    @PostMapping("/videos/add")
    public String addVideo(@RequestParam("title") String title, @RequestParam("file") MultipartFile file) throws IOException {
        return videoService.addVideo(title, file);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable String id) throws Exception {
        Video video = videoService.getVideo(id);
        return ResponseEntity.ok(video);
    }

    @GetMapping("/videos/stream/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
        Video video = videoService.getVideo(id);
        FileCopyUtils.copy(video.getStream(), response.getOutputStream());
    }

}
