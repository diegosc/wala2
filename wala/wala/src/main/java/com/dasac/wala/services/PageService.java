package com.dasac.wala.services;

import com.dasac.wala.dtos.PageRequest;
import com.dasac.wala.dtos.PageResponse;
import com.dasac.wala.dtos.PostRequest;
import com.dasac.wala.dtos.PostResponse;

public interface PageService {

    PageResponse create (PageRequest page);
    PageResponse readByTitle(String title);
    PageResponse update(PageRequest page, String title);
    void delete(String title);
    PageResponse createPost(PostRequest post, String title);
    void deletePost(Long idPost, String title);

}
