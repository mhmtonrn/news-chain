package com.softengine.newschain.api;

import com.softengine.newschain.models.dto.Photo;
import com.softengine.newschain.models.dto.Video;
import com.softengine.newschain.servives.PhotoService;
import com.softengine.newschain.servives.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/multimedia")
@RequiredArgsConstructor
public class MultiMediaApi {

    private final PhotoService photoService;
    private final VideoService videoService;


    @PostMapping("/photos/add")
    public ResponseEntity<List<String>>addPhoto(@RequestParam("title") String title, @RequestParam("image") List<MultipartFile> image) throws IOException {
        return ResponseEntity.ok(photoService.addPhoto(title, image));
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
