package io.koatech.aerosage.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "blogposts")
public class Blogpost {
    @Id
    @Field("BlogId")
    private double blogId;

    @TextIndexed
    @Field("BlogTitle")
    private String blogTitle;

    @TextIndexed
    @Field("BlogText")
    private String blogText;

    public Blogpost(double blogId, String blogTitle, String blogText) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogText = blogText;
    }

    public double getBlogId() {
        return blogId;
    }

    public void setBlogId(double blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogText() {
        return blogText;
    }

    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }
}
