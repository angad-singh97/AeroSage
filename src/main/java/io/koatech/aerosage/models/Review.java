package io.koatech.aerosage.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "reviews")
public class Review {
    @Id
    @Field("ReviewId")
    private double reviewId;
    @Field("AirportId")
    private String airportId;
    @Field("ReviewTimeStamp")
    private Date reviewTimeStamp;
    @TextIndexed
    @Field("ReviewText")
    private String reviewText;

    @Field("ReviewRating")
    private int reviewRating;

    public Review(Date reviewTimeStamp, String reviewText, int reviewRating) {
        this.reviewTimeStamp = reviewTimeStamp;
        this.reviewText = reviewText;
        this.reviewRating = reviewRating;
    }

    public Date getReviewTimeStamp() {
        return reviewTimeStamp;
    }

    public void setReviewTimeStamp(Date reviewTimeStamp) {
        this.reviewTimeStamp = reviewTimeStamp;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }
}
