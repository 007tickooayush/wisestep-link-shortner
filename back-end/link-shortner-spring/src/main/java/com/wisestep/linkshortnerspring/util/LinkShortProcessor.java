package com.wisestep.linkshortnerspring.util;

import com.wisestep.linkshortnerspring.model.LinkDto;
import com.wisestep.linkshortnerspring.model.OnlyLinkDto;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

public class LinkShortProcessor {


    public void checkIfLinkExists() {

    }


    public LinkDto checkIfLinkExpired(LinkDto linkDto) {
        String expiryTimeStr = linkDto.getExpiryTimeStamp();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        boolean expired = false;
        try {
            Date currentTime = new Date();
            Date expiryTime = dateFormat.parse(expiryTimeStr);

//            check if the time has expired
            expired = currentTime.compareTo(expiryTime) > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (expired) {
            linkDto.setStatus("Link has expired");
        }

        return linkDto;
    }

    public LinkDto generateShortLink(OnlyLinkDto rawLinkObj) {
        // create the attributes and save them into the DTO
        LinkDto linkDto = new LinkDto();


        // create a random url string extension to save in database
        Random random = new SecureRandom();
        int length = 7;
        String allLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder randomUrl = new StringBuilder(random.ints(length, 0,
                        allLetters.length())
                .mapToObj(c -> String.valueOf(allLetters.charAt(c)))
                .collect(Collectors.joining()));


        // set the attributes of the object
        linkDto.setShortLink(randomUrl.toString());
        linkDto.setRawLink(rawLinkObj.getLink());

        // add one hour to expiry time
        String expiryTimeStr = getExpiryTimeStr();

        linkDto.setExpiryTimeStamp(expiryTimeStr);
        linkDto.setStatus("Fresh Link generated");

        return linkDto;
    }

    public String getExpiryTimeStr(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,1);
        Date expiryTime = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        return dateFormat.format(expiryTime);
    }
}
