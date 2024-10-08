package com.muhrizram.grabprojectbe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.muhrizram.grabprojectbe.models.olaps.Pax;
import com.muhrizram.grabprojectbe.repositories.olaps.PaxRepository;
import java.time.LocalDateTime;

@Service
public class PaxServiceImpl implements PaxService {
    @Autowired
    private PaxRepository paxRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Pax registerPax(String fullName, String email, String username, String password) {
        if (paxRepository.existsByUsername(username)) {
            throw new RuntimeException("Username sudah digunakan");
        }

        Pax pax = new Pax();
        pax.setFullName(fullName);
        pax.setEmail(email);
        pax.setUsername(username);
        pax.setPassword(passwordEncoder.encode(password));
        pax.setCreatedAt(LocalDateTime.now());
        pax.setUpdatedAt(LocalDateTime.now());

        return paxRepository.save(pax);
    }
}
