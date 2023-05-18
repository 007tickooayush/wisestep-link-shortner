package com.wisestep.linkshortnerspring.service;

import com.wisestep.linkshortnerspring.model.LinkDto;
import com.wisestep.linkshortnerspring.model.OnlyLinkDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LinkService {
    public Flux<LinkDto> getAllLinks();
    public Mono<LinkDto> insertShortLink(Mono<LinkDto> linkDto);
    public Mono<LinkDto> getLinkRaw(String rawLink);
    public Mono<LinkDto> getLinkShort(String shortLink);
    public Mono<LinkDto> updateShortLink(OnlyLinkDto rawLinkObj);
//    public Mono<LinkDto> updateShortLink(Mono<LinkDto> linkDtoMono, OnlyLinkDto rawLinkObj);
}
