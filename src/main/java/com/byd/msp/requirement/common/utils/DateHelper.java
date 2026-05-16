package com.byd.msp.requirement.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_FULL_DATE_FORMAT_2 = "yyyy/MM/dd HH:mm:ss";
    public static final String DEFAULT_SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_SHORT_DATE_FORMAT_2 = "yyyy/MM/dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FULL_DATE_FORMAT);
        return formatter.format(dateDate);
    }
}
