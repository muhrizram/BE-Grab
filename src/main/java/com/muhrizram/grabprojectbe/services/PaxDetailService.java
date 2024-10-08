package com.muhrizram.grabprojectbe.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.muhrizram.grabprojectbe.models.olaps.Pax;
import com.muhrizram.grabprojectbe.repositories.olaps.PaxRepository;

@Service
public class PaxDetailService implements UserDetailsService {
    @Autowired
    private PaxRepository paxRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Pax> optionalPax = paxRepository.findByUsername(username);
        Pax pax = optionalPax.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new User(pax.getUsername(), pax.getPassword(),
                new ArrayList<>());
    }
}
