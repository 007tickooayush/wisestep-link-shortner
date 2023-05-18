package com.wisestep.linkshortnerspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {

    private String id;
    private String expiryTimeStamp;
    private String rawLink;
    private String shortLink;
    private String status;
}
