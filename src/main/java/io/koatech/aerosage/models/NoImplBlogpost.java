package io.koatech.aerosage.models;

public class NoImplBlogpost extends Blogpost{

    private static final Integer BLOG_INDEX = -1;
    private static final String BLOG_TITLE = "NONE";
    private static final String BLOG_TEXT = "NONE";

    public NoImplBlogpost() {
        super(BLOG_INDEX, BLOG_TITLE, BLOG_TEXT);
    }
}
