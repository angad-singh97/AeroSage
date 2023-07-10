package io.koatech.aerosage.repositories;

import io.koatech.aerosage.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
