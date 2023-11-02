package com.forever.dadamda.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;

@Service
public class TimeService{
    public static LocalDateTime fromUnixTime(Long unixTime) {
        if(unixTime == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneOffset.UTC);
    }

    public static Long fromLocalDateTime(LocalDateTime localDateTime) {
        if(localDateTime == null) {
            return null;
        }

        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
