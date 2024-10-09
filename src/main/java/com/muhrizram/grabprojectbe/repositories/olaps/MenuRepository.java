package com.muhrizram.grabprojectbe.repositories.olaps;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.muhrizram.grabprojectbe.models.olaps.Menu;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    Optional<Menu> findById(UUID id);

    @Query("SELECT o FROM Menu o WHERE " +
            "LOWER(o.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(o.price AS string) LIKE CONCAT('%', :search, '%')")
    Page<Menu> findBySearch(@Param("search") String search, Pageable pageable);
}
