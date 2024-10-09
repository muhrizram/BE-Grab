package com.muhrizram.grabprojectbe.models.oltps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OltpMenu {
    private String id;
    private String name;
    private Integer price;
}
