package com.muhrizram.grabprojectbe.repositories.olaps;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muhrizram.grabprojectbe.models.olaps.OlapTransaction;

public interface OlapTransactionRepository extends JpaRepository<OlapTransaction, String> {
    Optional<OlapTransaction> findById(String id);

    @Query("SELECT o FROM OlapTransaction o WHERE " +
            "LOWER(o.status) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "o.productId LIKE CONCAT('%', :search, '%')")
    Page<OlapTransaction> findBySearch(@Param("search") String search, Pageable pageable);
}
