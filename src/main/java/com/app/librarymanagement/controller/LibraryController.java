package com.app.librarymanagement.controller;

import com.app.librarymanagement.model.Book;
import com.app.librarymanagement.model.Library;
import com.app.librarymanagement.model.Subject;
import com.app.librarymanagement.service.BookService;
import com.app.librarymanagement.service.LibraryService;
import com.app.librarymanagement.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;
    private final SubjectService subjectService;
    private final BookService bookService;

    @Autowired
    public LibraryController(LibraryService libraryService, SubjectService subjectService, BookService bookService) {
        this.libraryService = libraryService;
        this.subjectService = subjectService;
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public List<Library> list() {
        return libraryService.list();
    }

    @GetMapping("/getBy/{libraryId}")
    public ResponseEntity<Object> getById(@PathVariable(name = "libraryId") Integer libraryId) {
        return libraryService.getById(libraryId);
    }

    @PostMapping("/add")
    public Library save(@RequestBody Library library) {
        for (Book book : library.getBooks()
        ) {
            for (Subject subject : book.getSubjects()
            ) {
                subjectService.save(subject);
            }
            bookService.save(book);
        }
        return libraryService.save(library);
    }

    @PostMapping("/add/book")
    public ResponseEntity<Object> addBookInLibrary(@RequestBody Book book, @RequestParam(name = "libraryId") Integer libraryId) {
        return libraryService.addBookInLibrary(book, libraryId);
    }

    @PutMapping("/update")
    public Library update(@RequestBody Library library) {
        return libraryService.update(library);
    }

    @DeleteMapping("/delete/{libraryId}")
    public String delete(@PathVariable(name = "libraryId") Integer libraryId) {
        libraryService.delete(libraryId);
        return "Successfully deleted the Library";
    }

}
