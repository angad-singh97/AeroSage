package io.koatech.aerosage.beans;


import io.koatech.aerosage.models.Blogpost;
import io.koatech.aerosage.repositories.BlogpostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DummyBlogs {

    private final BlogpostRepository blogpostRepository;

    @Autowired
    public DummyBlogs(BlogpostRepository blogpostRepository) {
        this.blogpostRepository = blogpostRepository;
    }

    @PostConstruct
    public void init() {
        try {
            String[] fileNames = {"blogpost1.txt", "blogpost2.txt", "blogpost3.txt"};

            for (int i = 0; i < fileNames.length; i++) {
                String fileContent = new String(Files.readAllBytes(ResourceUtils.getFile("classpath:" + fileNames[i]).toPath()));
                Blogpost blogpost = new Blogpost(i + 1, getTitleByIndex(i), fileContent);
                blogpostRepository.save(blogpost);
            }

            System.out.println("Data loaded and saved to the database.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTitleByIndex(int index) {
        switch (index) {
            case 0:
                return "Top 10 Airports by Daily Traffic";
            case 1:
                return "Fastest Growing Airlines: 2023";
            case 2:
                return "All About Planes";
            default:
                return "Unknown Title";
        }
    }

}