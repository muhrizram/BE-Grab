package com.muhrizram.grabprojectbe.DTOs.requests.oltp;

import com.muhrizram.grabprojectbe.models.oltps.OltpMenu;
import com.muhrizram.grabprojectbe.models.oltps.OltpPax;

import lombok.Data;

@Data
public class OltpTransactionRequest {
    private OltpMenu menu;
    private OltpPax pax;
    private String status;
}
