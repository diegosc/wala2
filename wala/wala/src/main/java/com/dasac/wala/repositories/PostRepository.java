package com.dasac.wala.repositories;

import com.dasac.wala.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
