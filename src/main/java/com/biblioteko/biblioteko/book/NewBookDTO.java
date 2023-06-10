package com.biblioteko.biblioteko.book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NewBookDTO {

    private String title;

    private String author;

    private String genre;
    
    private Integer edition;

    private String synopsis;
    
    private String photo;

    private Integer pages;

    private String accessLink;

}
