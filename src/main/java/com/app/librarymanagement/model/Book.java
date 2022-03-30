package com.app.librarymanagement.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String author;
    private LocalDate publishDate;

    @ManyToMany(targetEntity = Subject.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<Subject> subjects = new ArrayList<>();
}
