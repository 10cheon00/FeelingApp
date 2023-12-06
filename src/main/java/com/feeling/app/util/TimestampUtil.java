package com.feeling.app.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {
    public static Timestamp createTimestamp(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD");
            Date createdDate = simpleDateFormat.parse(date);
            return new Timestamp(createdDate.getTime());
        } catch (ParseException e) {
            return new Timestamp(946652400000L);
        }

    }
}
