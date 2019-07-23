package com.pravah.web.utils;

import java.util.Objects;

public class TimeUtil {

    public static String getTime(String time,String ... timesToAdd ){
        try {
            if (Objects.isNull(time)|| "".equals(time)) {
                return "";
            }
            if (Objects.isNull(timesToAdd)) {
                return time;
            }
            int hour = (int)Math.ceil(Double.parseDouble((time.split(":")[0])));
            int min = (int)Math.ceil(Double.parseDouble((time.split(":")[1])));

            for (String timeToAdd : timesToAdd) {
                if (Objects.nonNull(timeToAdd) && !"".equals(timeToAdd)) {
                    min = min + (int)Math.ceil(Double.parseDouble(timeToAdd));
                }
            }

            if (min >= 60) {
                min = min - 60;
                hour = hour + 1;
            }
            String hourString = hour < 10 ? "0" + hour : String.valueOf(hour);
            String minuteString = min < 10 ? "0" + min : String.valueOf(min);

            return hourString + ":" + minuteString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
