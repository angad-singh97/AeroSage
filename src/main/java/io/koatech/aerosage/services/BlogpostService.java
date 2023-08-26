package io.koatech.aerosage.services;

import io.koatech.aerosage.models.Blogpost;

public interface BlogpostService {
    Blogpost getBlogpostById (String id);
}
