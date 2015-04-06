package com.sensorhub.iot.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/2/25.
 */
public class DateUtils {
    public static SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf_day_time = new SimpleDateFormat("yyyyMMddhhmmss");
    public static SimpleDateFormat sdf_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static String  addDaysForSpecDate(String date,int days) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf_day.parse(date));
        calendar.add(Calendar.DAY_OF_MONTH,days);
        return sdf_day.format(calendar.getTime());
    }
}
