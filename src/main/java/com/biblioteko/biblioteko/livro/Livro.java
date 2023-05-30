package com.biblioteko.biblioteko.livro;
import java.util.UUID;
public class Livro {
    private String title;
    
    private String author;
    
    private int year;

    private String gender;

    private String synopsis;

    private String id;
    
    private String publisher;

    private String linkString;
    

    /* once we have it we have to create a constructor that allows a user to input instance variables **/
    
    public Livro(String title,String author,int year,String gender, String synopsis, String publisher, String link)
    {   this.title=title;
        this.author=author;
        this.gender = gender;
        this.synopsis = synopsis;
        this.year=year;
        this.publisher=publisher;
        this.id = UUID.randomUUID().toString();
        this.linkString = link;
    }
    
    /* then we have to create an accessor method for each of the instance variables created above to retun whatever 
     * has been inputted by the user
     */
      
    public String getTitle()
    {
        return title;
    }
    
     public String getAuthor()
    {
        return author;
    }
    public String getGender()
    {
        return gender;
    }
    public String getID()
    {
        return id;
    }
    public String getSynopsis()
    {
        return synopsis;
    }

    public int getYear()
    {
        return year;
    }
    public String getPublisher()
    {
        return publisher;
    }
    public String getlinkString() {
        return this.linkString;
    }
    
    

    public void setTitle(String title)
    {
        this.title=title;
    }
    
     public void setAuthor(String author)
    {
       this.author=author;
    }
    public void setYear(int year)
    {
        this.year=year;
    }
    public void setPublisher(String publisher)
    {
        this.publisher=publisher;
    }
    
    public void setGender(String Gender)
    {
        this.gender = Gender;
    } 
    public void setSynopsis(String synopsis)
    {
        this.synopsis=synopsis;
    } 
       
    
    
    
    public String toString()
    {
        return "The details of the book are: " + title + ", " + author + ", " + year + ", " + publisher + ", ";
    }
}
