package com.softengine.newschain.servives;

import com.softengine.newschain.models.entity.DatabaseSequence;
import com.softengine.newschain.respository.SequenceGeneratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SequenceGeneratorService {

    private final SequenceGeneratorRepository sequenceGeneratorRepository;

    public Integer getNext(String sequenceId) {
        Optional<DatabaseSequence> counter = sequenceGeneratorRepository.findById(sequenceId);
        if(!counter.isPresent()){
            DatabaseSequence newSequesnce = new DatabaseSequence();
            newSequesnce.setId(sequenceId);
            newSequesnce.setSeq(0);
            sequenceGeneratorRepository.save(newSequesnce);
            return 0;
        }else{
            DatabaseSequence c = counter.get();
            c.setSeq(c.getSeq()+1);
            sequenceGeneratorRepository.save(c);
            return c.getSeq();
        }
    }
}