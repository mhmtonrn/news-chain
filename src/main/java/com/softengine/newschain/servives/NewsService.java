package com.softengine.newschain.servives;

import com.softengine.newschain.exception.Error;
import com.softengine.newschain.exception.RecordNotFoundException;
import com.softengine.newschain.models.dto.NewsDTO;
import com.softengine.newschain.models.entity.News;
import com.softengine.newschain.models.types.NewsStatus;
import com.softengine.newschain.respository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;
    private final SequenceGeneratorService sequenceGeneratorService;

    public NewsDTO addNews(NewsDTO newsDTO) {
        News forSaveNews = modelMapper.map(newsDTO,News.class);
        forSaveNews.setId(sequenceGeneratorService.getNext(News.SEQUENCE_NAME));
        forSaveNews.setNewsStatus(NewsStatus.CREATED);
        return modelMapper.map(newsRepository.save(forSaveNews),NewsDTO.class);
    }

    public Page<News> getAllNews(Pageable pageable) throws RecordNotFoundException {
        Page<News> newses = newsRepository.findAllByOrderByPublishDateDesc(pageable);
        if (!newses.getContent().isEmpty()){
            return newses;
        }else{
            throw new RecordNotFoundException(Error.RECORD_NOT_FOUND_ERROR);
        }
    }

    public NewsDTO getNewsById(Integer id) throws RecordNotFoundException {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()){
            return modelMapper.map(news.get(),NewsDTO.class);
        }else{
            throw new RecordNotFoundException(Error.RECORD_NOT_FOUND_ERROR);
        }
    }
}
