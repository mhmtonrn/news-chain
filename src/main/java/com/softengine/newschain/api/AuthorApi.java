package com.softengine.newschain.api;

import com.softengine.newschain.exception.RecordNotFoundException;
import com.softengine.newschain.models.dto.AuthorDTO;
import com.softengine.newschain.servives.AuthorServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/author")
@RequiredArgsConstructor
public class AuthorApi {

    private final AuthorServices authorServices;

    @PostMapping("/addAuthor")
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO author){
        return ResponseEntity.ok(authorServices.addAuthor(author));
    }

    @GetMapping("/getAuthorById/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(authorServices.getAuthorById(id));
    }

    @GetMapping("/getAuthorByName/{name}")
    public ResponseEntity<List<AuthorDTO>> getAuthorByName(@PathVariable String name) throws RecordNotFoundException {
        return ResponseEntity.ok(authorServices.getAuthorByName(name));
    }

    @DeleteMapping("/deleteAuthorById/{id}")
    public ResponseEntity<AuthorDTO> deleteAuthorById(@PathVariable Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(authorServices.deleteAuthorById(id));
    }

    @PutMapping("enableEditorById/{id}")
    public ResponseEntity<AuthorDTO> enableEditorById(@PathVariable Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(authorServices.enableEditorById(id));
    }

    @PutMapping("disableEditorById/{id}")
    public ResponseEntity<AuthorDTO> disableEditorById(@PathVariable Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(authorServices.disableEditorById(id));
    }
}
