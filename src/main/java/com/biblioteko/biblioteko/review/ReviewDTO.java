package com.biblioteko.biblioteko.review;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {

    private Float stars;

    public ReviewDTO(Float stars) {
        this.stars = stars;
    }

}
