package com.wisestep.linkshortnerspring.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OnlyLinkDto {
//    a separate link handler dto for getting the json request of generating new link
    private String link;
}
