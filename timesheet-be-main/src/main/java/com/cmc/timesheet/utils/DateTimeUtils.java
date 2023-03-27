package com.cmc.timesheet.utils;

import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    /**
     * Use for convert from string to Time
     * @param time
     * @param format
     */
    public static Time convertStringToTime(String time,String format) {
        try {

            if (ObjectUtils.isNullorEmpty(time)) {
                throw new Exception("Time is empty");
            }
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(format));
            System.out.println(localTime.getHour());
            return Time.valueOf(localTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Use for convert from Time to OffsetTime
     * @param time
     */
    public static OffsetTime convertTimeToOffsetTime(Time time) {
        try {
            if(ObjectUtils.isNullorEmpty(time)) {
                throw new Exception("Time is empty");
            }
            try {
                ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());
                return OffsetTime.of(time.toLocalTime(),zoneOffset);
            } catch (Exception e) {
                throw new Exception("Could not parse Time");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
