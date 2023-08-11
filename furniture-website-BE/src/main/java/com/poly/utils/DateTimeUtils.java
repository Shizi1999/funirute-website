package com.poly.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateTimeUtils {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public Date now(){
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.of("GMT+7")).toInstant());
    }

    public  String format(Date date){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    public  String format(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public  Date parse(String dateString, String pattern)  {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateString);
        }catch (Exception ex){
            return null;
        }
    }

    public Date parse(String dateString)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateString);
        }catch (Exception ex){
            return null;
        }
    }
}
