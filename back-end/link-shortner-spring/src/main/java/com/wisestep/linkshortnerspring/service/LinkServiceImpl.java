package com.wisestep.linkshortnerspring.service;

import com.wisestep.linkshortnerspring.model.OnlyLinkDto;
import com.wisestep.linkshortnerspring.util.CustomLinkMapper;
import com.wisestep.linkshortnerspring.model.LinkDto;
import com.wisestep.linkshortnerspring.repository.LinkRepository;
import com.wisestep.linkshortnerspring.util.LinkShortProcessor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private LinkShortProcessor linkShortProcessor;

    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Flux<LinkDto> getAllLinks() {
        return linkRepository.findAll().map(CustomLinkMapper::mapDto);
//        .map(CustomLinkMapper::mapDto);
    }

    @Override
    public Mono<LinkDto> getLinkRaw(String rawLink) {
        linkShortProcessor = new LinkShortProcessor();

        return linkRepository.findByRawLink(rawLink)
                .map(CustomLinkMapper::mapDto)
                .map(linkShortProcessor::checkIfLinkExpired);
    }

    @Override
    public Mono<LinkDto> getLinkShort(String shortLink) {
        return linkRepository.findByShortLink(shortLink).map(CustomLinkMapper::mapDto);
    }

    @Override
    public Mono<LinkDto> insertShortLink(Mono<LinkDto> linkDto) {
        linkShortProcessor = new LinkShortProcessor();
        Mono<LinkDto> savedLink;
        return linkDto
                // checking first if the provided raw link is already present in the DB
                .map(linkProvided ->
                        linkRepository
                                .findByRawLink(linkProvided.getRawLink())
                                .map(CustomLinkMapper::mapDto)
                                .doOnNext(l -> {
                                    if (l.getRawLink().equals(linkProvided.getRawLink())) {
                                        OnlyLinkDto newOnlyLink = new OnlyLinkDto(linkProvided.getRawLink());
//                                        generate new short Link
                                        LinkDto newLink = linkShortProcessor.generateShortLink(newOnlyLink);

//                                        update the DTO response for user
                                        linkProvided.setShortLink(newLink.getShortLink());

//                                        Mono.just(newLink)
//                                                .map(CustomLinkMapper::mapOriginal)
//                                                .map(linkRepository::save);
//                                throw new RuntimeException("Link is generated");
                                    }
                                })
                                .then(Mono.just(linkProvided)))
                .flatMap(Function.identity())
                .map(CustomLinkMapper::mapOriginal)
                // update the link present in the DB
                .flatMap(linkRepository::save)
                .map(CustomLinkMapper::mapDto)
                ;
    }

    @Override
    public Mono<LinkDto> updateShortLink(OnlyLinkDto rawLinkObj) {
//        Mono<LinkDto> linkDtoMono,
        linkShortProcessor = new LinkShortProcessor();

        return linkRepository.findByRawLink(rawLinkObj.getLink())
                .map(link -> {
                    link.setExpiryTimeStamp(linkShortProcessor.getExpiryTimeStr());
                    link.setStatus("Short Link Updated");
                    return link;
                })
                .doOnNext(l -> l.setShortLink(linkShortProcessor
                        .generateShortLink(rawLinkObj)
                        .getShortLink()))
                .flatMap(linkRepository::save)
                .map(CustomLinkMapper::mapDto);
    }
}
