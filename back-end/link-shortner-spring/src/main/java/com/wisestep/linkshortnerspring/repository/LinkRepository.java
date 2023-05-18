package com.wisestep.linkshortnerspring.repository;

import com.wisestep.linkshortnerspring.model.LinkDto;
import com.wisestep.linkshortnerspring.model.Links;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LinkRepository extends ReactiveMongoRepository<Links, String> {
    Mono<Links> findByRawLink(String rawLink);
    Mono<Links> findByShortLink(String shortLink);
}
