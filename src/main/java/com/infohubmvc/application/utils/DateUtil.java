package com.infohubmvc.application.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {


    public static Date getDateFromString(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {

        }
        return date;
    }

    public static LocalDate getLocalDateFromString(String dateString) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate date = null;
        try {
            date = df.parse(dateString).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {

        }
        return date;
    }

    public static String getDateAsString(Date date) {
        return getDateAsString(date, "yyyy-MM-dd");
    }

    public static String getDateAsString(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDate.format(formatter);
    }

    public static String getDateAsString(Date date, String format) {
        if (StringUtil.isEmpty(format)) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formatedDate = df.format(date);
            return formatedDate;
        } else {
            DateFormat df = new SimpleDateFormat(format);
            String formatedDate = df.format(date);
            return formatedDate;
        }
    }

    public static String getDateAsString(Date date, String format, Locale locale) {
        if (StringUtil.isEmpty(format)) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", locale);
            String formatedDate = df.format(date);
            return formatedDate;
        } else {
            SimpleDateFormat df = new SimpleDateFormat(format, locale);
            String formatedDate = df.format(date);
            return formatedDate;
        }
    }

    public static Date getDateFromRequestEx(String dateString){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if ("today".equalsIgnoreCase(dateString)) {
            return new Date();
        }
        if (StringUtil.isEmpty(dateString)) {
            return null;
        }

        Date date;
        try {
            date = df.parse(dateString);
        } catch (ParseException e){
            date = null;
        } catch (Exception e) {
            date = null;
        }
        return date;
    }

    public static Date getDateFromRequest(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if ("today".equalsIgnoreCase(dateString) || StringUtil.isEmpty(dateString)) {
            return new Date();
        }
        Date date;
        try {
            date = df.parse(dateString);
        } catch (ParseException e){
            throw e;
        } catch (Exception e) {
            date = new Date();
        }
        return date;
    }

    public static Date getFirstDayOfMonthFromRequest(String monthString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = monthString + "01";
        try {
            return df.parse(dateString);
        } catch (Exception e) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            return cal.getTime();
        }
    }
    public static Date getLastDayOfMonth(Date month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();

    }


    public static String getStringDateForRequest(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "";
        try {
            dateString = df.format(date);
        } catch (Exception e) {
            dateString = "";
        }
        return dateString;
    }

    public static String getStringDateTimeForRequest(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "";
        try {
            dateString = df.format(date);
        } catch (Exception e) {
            dateString = "";
        }
        return dateString;
    }

    public static String getStringMonthForRequest(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        String dateString = "";
        try {
            dateString = df.format(date);
        } catch (Exception e) {
            dateString = df.format(new Date());
        }
        return dateString;
    }

    public static String getStringDateForExtraseRequest(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dateString = "";
        try {
            dateString = df.format(date);
        } catch (Exception e) {
            dateString = "";
        }
        return dateString;
    }
    public static String getStringMonthForExtraseRequest(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMM");
        String dateString = "";
        try {
            dateString = df.format(date);
        } catch (Exception e) {
            dateString = df.format(new Date());
        }
        return dateString;
    }


    public static boolean validateDateFromRequest(String forDate){
        if (StringUtil.isEmpty(forDate)) {
            return false;
        }
        if (forDate.length() == 10){
            return validateDayFromRequest(forDate);
        } else if (forDate.length()==7){
            return validateMonthFromRequest(forDate);
        } else {
            return  false;
        }
    }

    public static String validatePeriodicitateDateFromRequest(String forDate){
        if (forDate.length() == 10){
            return "daily";
        } else if (forDate.length()==7){
            return "monthly";
        }
        return null;
    }

    public static boolean validateMonthFromRequest(String forMonth){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(forMonth + "-01");
        } catch (ParseException e){
            return false;
        }
        return true;
    }

    public static boolean validateDayFromRequest(String forDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(forDate);
        } catch (ParseException e){
            return false;
        }
        return true;
    }

    public static Long getTime(){
        Calendar cal = Calendar.getInstance();
        Long res = ((Integer)(cal.get(Calendar.HOUR_OF_DAY) * 1000000
                + cal.get(Calendar.MINUTE) * 10000
                + cal.get(Calendar.SECOND) * 100
                + cal.get(Calendar.MILLISECOND) % 100)).longValue();
        //DateFormat df = new SimpleDateFormat("HHmmss");
        //String formatedDate = df.format(new Date());
        return res; //  Long.parseLong(formatedDate);
    }

    public static Long getTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Long res = ((Integer)(cal.get(Calendar.HOUR_OF_DAY) * 1000000
                + cal.get(Calendar.MINUTE) * 10000
                + cal.get(Calendar.SECOND) * 100
                + cal.get(Calendar.MILLISECOND) % 100)).longValue();
        //DateFormat df = new SimpleDateFormat("HHmmss");
        //String formatedDate = df.format(new Date());
        return res; //  Long.parseLong(formatedDate);
    }


    public static java.sql.Date getSqlDate(Date date) {
        if (date == null){
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
    public static java.sql.Date getSqlDateNow() {
        return new java.sql.Date(new Date().getTime());
    }

    public static Date createDate(Date date, Integer time){
        if (date == null) {
            return null;
        }
        if (time == null) {
            time = 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int seconds = time / 100 % 100;
        int minutes = time / 10000 % 100;
        int hours = time / 1000000 % 100;
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    public static String createDateStr(Date date, Integer time){
        if (date == null) {
            return null;
        }
        if (time == null) {
            time = 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int seconds = time / 100 % 100;
        int minutes = time / 10000 % 100;
        int hours = time / 1000000 % 100;
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);
        return getStringDateTimeForRequest(cal.getTime());
    }
}
