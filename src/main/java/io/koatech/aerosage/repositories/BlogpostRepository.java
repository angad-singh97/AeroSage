package io.koatech.aerosage.repositories;

import io.koatech.aerosage.models.Blogpost;
import io.koatech.aerosage.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BlogpostRepository extends MongoRepository<Blogpost, String> {
}
