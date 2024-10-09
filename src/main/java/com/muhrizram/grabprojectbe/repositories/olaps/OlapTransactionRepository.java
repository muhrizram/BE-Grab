package com.muhrizram.grabprojectbe.repositories.olaps;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muhrizram.grabprojectbe.models.olaps.OlapTransaction;

public interface OlapTransactionRepository extends JpaRepository<OlapTransaction, String> {
    Optional<OlapTransaction> findById(String id);

    @Query("SELECT o FROM OlapTransaction o " +
            "JOIN o.pax p " +
            "JOIN o.menu m " +
            "WHERE LOWER(o.status) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<OlapTransaction> findBySearch(@Param("search") String search, Pageable pageable);

    @Query("SELECT EXTRACT(YEAR FROM o.updatedAt) AS year, " +
            "TO_CHAR(o.updatedAt, 'FMMonth') AS month, " +
            "o.status AS status, " +
            "COUNT(o) AS count " +
            "FROM OlapTransaction o " +
            "WHERE o.status IN :statuses " +
            "GROUP BY EXTRACT(YEAR FROM o.updatedAt), TO_CHAR(o.updatedAt, 'FMMonth'), o.status")
    List<Object[]> countOrdersByStatusPerMonth(@Param("statuses") List<String> statuses);

}
