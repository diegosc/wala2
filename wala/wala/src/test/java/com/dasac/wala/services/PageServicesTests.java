package com.dasac.wala.services;

import com.dasac.wala.dtos.PageResponse;
import com.dasac.wala.entities.PageEntity;
import com.dasac.wala.entities.PostEntity;
import com.dasac.wala.repositories.PageRepository;
import com.dasac.wala.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assert.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
// @ExtendWith({MockitoExtension.class})
public class PageServicesTests {


    @MockitoBean //@Autowired is similar
    private PageRepository pageRepository;

    @MockitoBean  //@Autowired is similar
    private UserRepository userRepository;

    @Autowired
    private PageServiceImpl target;

    @Test
    void readByTitle_ShouldReturnPageResponses_WhenTitleExist(){

        String  title="Debugeando ideas";

        PostEntity postEntity= new PostEntity();

        postEntity.setImg("http://img");
        postEntity.setContent("Some content");
        postEntity.setDateCreation(LocalDateTime.MIN);

        PageEntity pageEntity =new PageEntity();
        pageEntity.setTitle(title);
        pageEntity.setDateCreation(LocalDateTime.MIN);
        pageEntity.setPosts(List.of(postEntity));

        given(pageRepository.findByTitle(title))
                .willReturn(Optional.of(pageEntity));

        PageResponse result =target.readByTitle(title);

    }
}
