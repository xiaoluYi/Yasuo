/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sjl.yuehu.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil
 */
public class DateUtil {

    static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    static DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
    static DateFormat dateForma3 = new SimpleDateFormat("yyyy-MM-dd");
    static DateFormat dateForma4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static DateFormat dateForma5 = new SimpleDateFormat("yyyy年MM月dd日");
    static DateFormat dateForma6 = new SimpleDateFormat("yyyy.MM.dd");
    static DateFormat dateForma7 = new SimpleDateFormat("MM月dd HH:mm");

    public static String toDate(Date date) {
        return dateFormat.format(date);
    }

    public static String toDate5(Date date) {
        return dateForma5.format(date);
    }

    public static String toDate6(Date date) {
        return dateForma6.format(date);
    }

    public static String toDate7(Date date) {
        return dateForma7.format(date);
    }

    public static String toDate(Date date, int add) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, add);
        return toDate(calendar.getTime());
    }

    public static String toDate3(Date date) {
        return dateForma3.format(date);
    }

    public static String toDate4(Date date) {
        return dateForma4.format(date);
    }


    public static Date getLastdayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }


    public static Date getNextdayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }


    public static boolean isTheSameDay(Date one, Date another) {
        Calendar _one = Calendar.getInstance();
        _one.setTime(one);
        Calendar _another = Calendar.getInstance();
        _another.setTime(another);
        int oneDay = _one.get(Calendar.DAY_OF_YEAR);
        int anotherDay = _another.get(Calendar.DAY_OF_YEAR);

        return oneDay == anotherDay;
    }

    public static String getMonthAndDay(Date date) {

        Calendar calendar = Calendar.getInstance();

        int _day = calendar.get(Calendar.DAY_OF_MONTH);
        int _month = calendar.get(Calendar.MONTH);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        int day = calendar1.get(Calendar.DAY_OF_MONTH);
        int month = calendar1.get(Calendar.MONTH);

        return (month + 1) + "月" + day + "日";

        /**
         if (_month == month) {
         if (_day == day) {
         return "今天";
         } else {
         int i = _day - day;
         if (i == 1) {
         return "昨天";
         } else {
         return (month + 1) + "月" + day + "日";
         }
         }
         } else {
         return (month + 1) + "月" + day + "日";
         }**/
    }

    public static String getHourAndMinute(Date date) {
        return dateFormat2.format(date);
    }
}
