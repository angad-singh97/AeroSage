package io.koatech.aerosage.controllers;

import io.koatech.aerosage.models.Blogpost;
import io.koatech.aerosage.services.BlogpostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin(origins = "http://localhost:3000")
public class BlogpostController {
    @Autowired
    private BlogpostService blogpostService;

    @GetMapping("/{blogpostId}")
    public ResponseEntity<Blogpost> getBlogpostById(@PathVariable String blogpostId) {
        Blogpost blogpost = blogpostService.getBlogpostById(blogpostId);
        return ResponseEntity.ok().body(blogpost);
    }
}
