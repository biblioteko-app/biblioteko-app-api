package com.biblioteko.biblioteko.read;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadPagesDTO {

    private Integer readPages;

    public ReadPagesDTO(Integer readPages) {
        this.readPages = readPages;
    }
    public ReadPagesDTO(){
        this.readPages = 0;
    }
}
    

