package io.koatech.aerosage.services;

import io.koatech.aerosage.beans.RoutesGraph;
import io.koatech.aerosage.models.Blogpost;
import io.koatech.aerosage.models.NoImplBlogpost;
import io.koatech.aerosage.repositories.BlogpostRepository;
import io.koatech.aerosage.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogpostServiceImpl implements BlogpostService{

    private BlogpostRepository blogpostRepository;

    @Autowired
    public BlogpostServiceImpl(BlogpostRepository blogpostRepository) {
        this.blogpostRepository = blogpostRepository;
    }

    @Override
    public Blogpost getBlogpostById(String id) {
        Optional<Blogpost> optionalBlogpost = blogpostRepository.findById(id);
        return optionalBlogpost.orElseGet(NoImplBlogpost::new);
    }
}
