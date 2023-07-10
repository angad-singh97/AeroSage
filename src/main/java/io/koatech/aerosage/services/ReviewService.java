package io.koatech.aerosage.services;

import io.koatech.aerosage.models.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByText(String airportId, String searchText);

    List<Review> getAllReviews(String airportId);

    Boolean addReview(String airportId, Review review);
}
