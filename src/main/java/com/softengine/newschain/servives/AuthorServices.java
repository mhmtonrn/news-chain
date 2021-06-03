package com.softengine.newschain.servives;

import com.softengine.newschain.exception.Error;
import com.softengine.newschain.exception.RecordNotFoundException;
import com.softengine.newschain.models.dto.AuthorDTO;
import com.softengine.newschain.models.entity.Author;
import com.softengine.newschain.models.types.AuthorStatus;
import com.softengine.newschain.respository.AuthorRepository;
import com.softengine.newschain.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServices {

    private final AuthorRepository authorRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ModelMapper modelMapper;

    @Transactional
    public AuthorDTO addAuthor(AuthorDTO author) {
        Author authorDAO = modelMapper.map(author, Author.class);
        authorDAO.setId(sequenceGeneratorService.getNext(Author.SEQUENCE_NAME));
        authorDAO.setSecretKey(RandomString.getAlphaNumericString(10));
        authorDAO.setAuthorStatus(AuthorStatus.WAITING);
        return modelMapper.map(authorRepository.save(authorDAO), AuthorDTO.class);
    }

    public AuthorDTO getAuthorById(Integer id) throws RecordNotFoundException {
        Author returnAuthorDAO = getAuthorByIdForDb(id);
        return modelMapper.map(returnAuthorDAO, AuthorDTO.class);
    }

    public List<AuthorDTO> getAuthorByName(String name) throws RecordNotFoundException {
        Optional<List<Author>> returnAuthorDTO = authorRepository.findByName(name);
        if (returnAuthorDTO.isPresent()) {
            Type listType = new TypeToken<List<AuthorDTO>>() {}.getType();
            return modelMapper.map(returnAuthorDTO.get(), listType);
        } else {
            throw new RecordNotFoundException(Error.RECORD_NOT_FOUND_ERROR);
        }
    }

    public AuthorDTO deleteAuthorById(Integer id) throws RecordNotFoundException {
        AuthorDTO author = getAuthorById(id);
        authorRepository.deleteById(id);
        return author;
    }

    public AuthorDTO enableEditorById(Integer id) throws RecordNotFoundException {
        Author author = getAuthorByIdForDb(id);
        author.setAuthorStatus(AuthorStatus.ENABLE);
        return modelMapper.map(authorRepository.save(author), AuthorDTO.class);
    }

    public AuthorDTO disableEditorById(Integer id) throws RecordNotFoundException {
        Author author = getAuthorByIdForDb(id);
        author.setAuthorStatus(AuthorStatus.DISABLE);
        return modelMapper.map(authorRepository.save(author), AuthorDTO.class);
    }

    private Author getAuthorByIdForDb(Integer id) throws RecordNotFoundException {
        Optional<Author> returnAuthorDTO = authorRepository.findById(id);
        if (returnAuthorDTO.isPresent()) {
            return returnAuthorDTO.get();
        } else {
            throw new RecordNotFoundException(Error.RECORD_NOT_FOUND_ERROR);
        }
    }
}
