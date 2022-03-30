package com.app.librarymanagement.service;

import com.app.librarymanagement.model.Book;
import com.app.librarymanagement.model.Library;
import com.app.librarymanagement.model.Subject;
import com.app.librarymanagement.repository.BookRepository;
import com.app.librarymanagement.repository.LibraryRepository;
import com.app.librarymanagement.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository, SubjectRepository subjectRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Library> list() {
        return libraryRepository.findAll();
    }

    public Library save(Library library) {
        return libraryRepository.save(library);
    }

    public Library update(Library library) {
        return libraryRepository.save(library);
    }

    public ResponseEntity<Object> getById(Integer libraryId) {
        Optional<Library> library = libraryRepository.findById(libraryId);
        if (library.isPresent()) {
            return new ResponseEntity<>(library.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no library against this library ID", HttpStatus.OK);
        }
    }

    public void delete(Integer libraryId) {
        Optional<Library> library = libraryRepository.findById(libraryId);
        if (library.isPresent()) {
            libraryRepository.delete(library.get());
        } else {
            throw new RuntimeException("Library doesn't exists against this ID");
        }
    }

    public ResponseEntity<Object> addBookInLibrary(Book book, Integer libraryId) {
        Optional<Library> library = libraryRepository.findById(libraryId);
        if (library.isPresent()) {
            subjectRepository.saveAll(book.getSubjects());
            Book savedBook = bookRepository.save(book);
            library.get().getBooks().add(savedBook);
            libraryRepository.save(library.get());
            return new ResponseEntity<>("Book is successfully added in the Library", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("There is no library against this Library ID", HttpStatus.NOT_FOUND);
        }
    }
}
