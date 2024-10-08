package com.muhrizram.grabprojectbe.services;

import com.muhrizram.grabprojectbe.models.olaps.Pax;

public interface PaxService {
    Pax registerPax(String fullName, String email, String username, String password);
}
