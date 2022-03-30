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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final SubjectRepository subjectRepository;
    private final LibraryRepository libraryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, SubjectRepository subjectRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.subjectRepository = subjectRepository;
        this.libraryRepository = libraryRepository;
    }

    public List<Book> list() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            List<Library> libraries = libraryRepository.findAll();
            for (Library lib : libraries
            ) {
                for (int i = 0; i < lib.getBooks().size(); i++) {
                    if (lib.getBooks().get(i).equals(book.get())) {
                        lib.getBooks().remove(book.get());
                        libraryRepository.save(lib);
                        break;
                    }
                }
            }
            bookRepository.delete(book.get());
        } else {
            throw new RuntimeException("Book doesn't exists against this ID");
        }
    }

    public ResponseEntity<Object> getById(Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no book against this book ID", HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> getAllBooksBySubjectId(Integer subjectId) {
        HashMap<Integer, Object> map = new HashMap<>();
        List<Book> books = bookRepository.findAll();
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isPresent()) {
            for (Book book : books
            ) {
                for (int i = 0; i < book.getSubjects().size(); i++) {
                    if (book.getSubjects().get(i).equals(subject.get())) {
                        map.put(book.getId(), book);
                    }
                }
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no subject against this Id", HttpStatus.OK);
        }
    }
}
