package org.agilekip.tutorials.buybook.repository;

import org.agilekip.tutorials.buybook.domain.BuyBook;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BuyBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyBookRepository extends JpaRepository<BuyBook, Long> {}
