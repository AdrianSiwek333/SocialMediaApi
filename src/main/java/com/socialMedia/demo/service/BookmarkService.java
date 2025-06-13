package com.socialMedia.demo.service;

import com.socialMedia.demo.model.Bookmark;
import com.socialMedia.demo.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public void addBookmark(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }
}
