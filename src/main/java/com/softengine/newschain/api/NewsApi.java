package com.softengine.newschain.api;

import com.softengine.newschain.exception.RecordNotFoundException;
import com.softengine.newschain.models.dto.NewsDTO;
import com.softengine.newschain.models.entity.News;
import com.softengine.newschain.servives.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("api/news")
@RequiredArgsConstructor
public class NewsApi {

    private final NewsService newsService;

    @PostMapping("/addNews")
    public ResponseEntity<NewsDTO> addNews(@RequestBody NewsDTO newsDTO){
        return ResponseEntity.ok(newsService.addNews(newsDTO));
    }

    @GetMapping("/getAllNews")
    public ResponseEntity<Page<News>> getAllNews(Pageable pageable) throws RecordNotFoundException {
        return ResponseEntity.ok(newsService.getAllNews(pageable));
    }

    @GetMapping("/getNewsById/{id}")
    public ResponseEntity<NewsDTO> getAllNews(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

}
