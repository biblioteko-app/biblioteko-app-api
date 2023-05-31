package com.biblioteko.biblioteko.book;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class BookDTO {
    private UUID id;

    private String title;

    private String author;

    private String gender;
    
    private Integer edition;

    private String synopsis;
    
    private Float rating;

    private String photo;

    private Integer pages;

    private String accessLink;
    
}
