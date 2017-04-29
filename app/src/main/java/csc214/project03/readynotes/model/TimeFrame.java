package csc214.project03.readynotes.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Charlotte on 4/27/2017.
 * Monday = 2
 * SAT = 7
 * SUN = 1
 */

public class TimeFrame {
    private int[] days;
    private int sHour;
    private int sMin;
    private int eHour;
    private int eMin;

    public TimeFrame(int[] days, int sHour, int sMin, int eHour, int eMin){
        this.days = days;
        this.sHour = sHour;
        this.sMin = sMin;
        this.eHour = eHour;
        this.eMin = eMin;
    }

    public boolean inFrame(Date time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        for(int day: days){
            if(day == dayOfWeek) {
                if (sHour < hour && hour < eHour) {
                    return true;
                } else if (sHour == hour && sMin < min) {
                    return true;
                } else if (eHour == hour && eMin > min) {
                    return true;
                }
            }
        }

        return false;
    }
}
