package org.agilekip.tutorials.buybook.repository;

import java.util.List;
import org.agilekip.tutorials.buybook.domain.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select book from Book book where book.author.login = ?#{principal.username}")
    List<Book> findByAuthorIsCurrentUser();
}
