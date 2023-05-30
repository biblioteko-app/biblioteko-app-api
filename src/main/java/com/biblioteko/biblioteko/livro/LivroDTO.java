package com.biblioteko.biblioteko.livro;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDTO {
    private String title;
    
    private String author;
    
    private int year;

    private String gender;

    private String synopsis;

    private String id;
    
    private String publisher;

    private String linkString;

    public LivroDTO(String title,String author,int year,String gender, String synopsis, String publisher, String link)
    {   this.title=title;
        this.author=author;
        this.gender = gender;
        this.synopsis = synopsis;
        this.year=year;
        this.publisher=publisher;
        this.id = UUID.randomUUID().toString();
        this.linkString = link;
    }
}
