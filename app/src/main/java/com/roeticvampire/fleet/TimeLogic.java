package com.roeticvampire.fleet;

import java.sql.Timestamp;

public class TimeLogic {
    public static String CustomTimeFormat(String dateTime) {
        String currentTime= String.valueOf(new Timestamp(System.currentTimeMillis()));
        if(!getYear(currentTime).equals(getYear(dateTime))) return getYear(dateTime);
        if(!getDate(currentTime).equals(getDate(dateTime))) return getDate(dateTime);
        return getTime(dateTime);

    }
    private static String getYear(String dateTime){
        return dateTime.substring(0,4);
    }
    private static String getDate(String dateTime){
        return dateTime.substring(5,10);
    }
    private static String getTime(String dateTime){
        return dateTime.substring(11,16);
    }


}
