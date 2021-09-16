package com.softengine.newschain.servives;

import com.softengine.newschain.exception.Error;
import com.softengine.newschain.exception.RecordNotFoundException;
import com.softengine.newschain.models.dto.NewsDTO;
import com.softengine.newschain.models.dto.Photo;
import com.softengine.newschain.models.entity.News;
import com.softengine.newschain.models.types.NewsStatus;
import com.softengine.newschain.respository.NewsRepository;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final PhotoService photoService;

    public NewsDTO addNews(NewsDTO newsDTO) {
        News forSaveNews = modelMapper.map(newsDTO,News.class);
        forSaveNews.setId(sequenceGeneratorService.getNext(News.SEQUENCE_NAME));
        forSaveNews.setNewsStatus(NewsStatus.CREATED);
        return modelMapper.map(newsRepository.save(forSaveNews),NewsDTO.class);
    }

    public Page<News> getAllNews(Pageable pageable) throws RecordNotFoundException {
        Page<News> newses = newsRepository.findAllByOrderByPublishDateDesc(pageable);
        if (!newses.getContent().isEmpty()){
            for (News news:newses.getContent()){
                fillPhoto(news);
            }
            return newses;
        }else{
            throw new RecordNotFoundException(Error.RECORD_NOT_FOUND_ERROR);
        }
    }

    private void fillPhoto(News news) {
        if (Objects.nonNull(news.getPhotoId())){
            Photo photo = null;
            try {
                photo = photoService.getPhoto(news.getPhotoId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            news.setPhotoId(photo.getImageUrl());
        }
    }

    public NewsDTO getNewsById(Integer id) throws RecordNotFoundException {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()){
            News pnews = news.get();
            fillPhoto(pnews);
            return modelMapper.map(pnews,NewsDTO.class);
        }else{
            throw new RecordNotFoundException(Error.RECORD_NOT_FOUND_ERROR);
        }
    }

    public NewsDTO publishNewsAsEditor(Integer id) throws RecordNotFoundException {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()){
            news.get().setNewsStatus(NewsStatus.PUBLISHED);
            news.get().setPublishDate(LocalDateTime.now());
            return modelMapper.map(newsRepository.save(news.get()),NewsDTO.class);
        }else{
            throw new RecordNotFoundException(Error.RECORD_NOT_FOUND_ERROR);
        }
    }

    public NewsDTO editNewsAsEditor(NewsDTO newsDTO) throws RecordNotFoundException {
        Optional<News> news = newsRepository.findById(newsDTO.getId());
        if (news.isPresent()){
            news.get().setNewsStatus(NewsStatus.EDITED);
            news.get().setEditDate(LocalDateTime.now());
            return modelMapper.map(newsRepository.save(news.get()),NewsDTO.class);
        }else{
            throw new RecordNotFoundException(Error.RECORD_NOT_FOUND_ERROR);
        }
    }
}
