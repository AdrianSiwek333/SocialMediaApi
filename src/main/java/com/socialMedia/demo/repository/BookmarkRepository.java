package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {


}
