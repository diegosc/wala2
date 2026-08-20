package com.dasac.wala.repositories;

import com.dasac.wala.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PageRepository extends JpaRepository<PageEntity, Long> {

    Optional<PageEntity>findByTitle(String title);

    //delete from page where title = ;title
  @Modifying
  @Query("DELETE FROM PageEntity Where title =:title")
    void   deleteByTitle(String title);

    //if exist return true if not return false
    Boolean existsByTitle(String title);

}
