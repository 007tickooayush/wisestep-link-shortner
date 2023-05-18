package com.wisestep.linkshortnerspring.util;

import com.wisestep.linkshortnerspring.model.Links;
import com.wisestep.linkshortnerspring.model.LinkDto;
import org.springframework.beans.BeanUtils;

public class CustomLinkMapper {

    public static LinkDto mapDto(Links link){
        LinkDto linkDto = new LinkDto();

        BeanUtils.copyProperties(link,linkDto);

        return linkDto;
    }

    public static Links mapOriginal(LinkDto linkDto){
        Links link = new Links();

        BeanUtils.copyProperties(linkDto,link);

        return link;
    }

}
