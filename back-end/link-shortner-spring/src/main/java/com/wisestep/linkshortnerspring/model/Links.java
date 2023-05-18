package com.wisestep.linkshortnerspring.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "links")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Links {
    @Id
    private String id;
    private String expiryTimeStamp;
    private String rawLink;
    private String shortLink;
    private String status;
}
