package com.wisestep.linkshortnerspring.controller;

import com.wisestep.linkshortnerspring.model.LinkDto;
import com.wisestep.linkshortnerspring.model.OnlyLinkDto;
import com.wisestep.linkshortnerspring.service.LinkService;
import com.wisestep.linkshortnerspring.util.LinkShortProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/link-short/")
@CrossOrigin(origins = {"http://localhost:3000"})
public class LinkController {

    private final LinkService linkService;
    private LinkShortProcessor linkShortProcessor;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/raw-link")
    public ResponseEntity<Mono<LinkDto>> getByRawLink(@RequestParam("link") String rawLinkObj){

        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLinkRaw(rawLinkObj));
    }

    @GetMapping("/short-link")
    public ResponseEntity<Mono<LinkDto>> getByShortLink(@RequestParam("link") String shortLinkObj){

        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLinkShort(shortLinkObj));
    }

    @GetMapping("")
    public ResponseEntity<Flux<LinkDto>> getDefault(){

        return ResponseEntity.status(HttpStatus.OK).body(linkService.getAllLinks());
    }

    @PostMapping("/insert")
    public ResponseEntity<Mono<LinkDto>> createLink(@RequestBody OnlyLinkDto rawLinkObj){

        // save the link generated into the database and return the response
        return ResponseEntity.status(HttpStatus.OK)
                .body(linkService.insertShortLink(Mono.just( new LinkShortProcessor().generateShortLink(rawLinkObj))));
    }

    @PutMapping("/update")
    public ResponseEntity<Mono<LinkDto>> updateLink(@RequestBody OnlyLinkDto rawOnlyLinkObj){

        return ResponseEntity.status(HttpStatus.OK).body(linkService.updateShortLink(rawOnlyLinkObj));
    }
}
