package com.muhrizram.grabprojectbe.repositories.olaps;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.muhrizram.grabprojectbe.models.olaps.Pax;

public interface PaxRepository extends JpaRepository<Pax, Integer> {
    boolean existsByUsername(String username);

    Optional<Pax> findByUsername(String username);
}
