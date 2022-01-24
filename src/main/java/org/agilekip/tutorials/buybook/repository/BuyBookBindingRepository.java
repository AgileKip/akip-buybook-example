package org.agilekip.tutorials.buybook.repository;

import java.util.Optional;
import org.agilekip.tutorials.buybook.domain.BuyBookBinding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BuyBookBinding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyBookBindingRepository extends JpaRepository<BuyBookBinding, Long> {
    Optional<BuyBookBinding> findByProcessInstanceId(Long processInstanceId);
}
