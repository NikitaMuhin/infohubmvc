package com.infohubmvc.application.utils;


import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Pattern;

import static org.springframework.util.Assert.hasText;

/**
 *
 */
public class StringUtil {

    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$");

    public static void minLength(String str, int len) throws IllegalArgumentException {
        hasText(str);
        if (str.length() < len) {
            throw new IllegalArgumentException();
        }
    }

    public static void maxLength(String str, int len) throws IllegalArgumentException {
        hasText(str);
        if (str.length() > len) {
            throw new IllegalArgumentException();
        }
    }

    public static void validEmail(String email) throws IllegalArgumentException {
        minLength(email, 4);
        maxLength(email, 255);
        if (!email.contains("@") || StringUtils.containsWhitespace(email)) {
            throw new IllegalArgumentException();
        }
    }

    public static boolean isValidUuid(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }

    public static boolean equals(String str1, String str2) {
        return equals(str1, str2, false);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return equals(str1, str2, true);
    }

    public static boolean equals(String str1, String str2, boolean ignoreCase) {
        if (StringUtil.isEmpty(str1) && StringUtil.isEmpty(str2)) return true;
        if (StringUtil.isEmpty(str1) && StringUtil.isNotEmpty(str2)) return false;
        if (ignoreCase) {
            if (str1.equalsIgnoreCase(str2)) return true;
        } else {
            if (str1.equals(str2)) return true;
        }

        return false;
    }

    public static String getQueryParametersString(int size) {
        String s = "";
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                s += ",";
            }
            s += "?";
        }
        return s;
    }

    public static String getQueryParametersString(int size, String column, String operator, String iterator) {
        String s = "";
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                s += " " + iterator + " ";
            }
            s += column + " " + operator + " ?";
        }
        return s;
    }

    public static String getSpacesRemoved(String str){
        if (isEmpty(str)) return str;
        return str.replaceAll("\\s+"," ");
    }

    public static String padLeft(String str, int length, String cuChar){
        String result = "";
        String fillBy = cuChar;
        result = padLeft(str, length);
        if (fillBy == null || fillBy.length() == 0){
            fillBy = "0";
        }
        return result.replaceAll(" ",fillBy);
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padRight(String str, int length, String cuChar){
        String result = "";
        String fillBy = cuChar;
        result = padRight(str, length);
        if (fillBy == null || fillBy.length() == 0){
            fillBy = "0";
        }
        return result.replaceAll(" ",fillBy);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static String getAsString(Double value){
        if (value == null){
            return String.format ("%.2f", 0.00);
        }
        return String.format ("%.2f", value);
    }
    public static String getAsString(BigDecimal value){
        if (value == null){
            return String.format ("%.2f", 0.00);
        }
        return String.format ("%.2f", value);
    }
    public static String getAsString(float value){

        return String.format ("%.2f", value);
    }

    public static String getAsString(Double value, Character delimiter){

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(delimiter);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00", decimalFormatSymbols);
        if (value == null){
            return decimalFormat.format(0.00);
        }
        return decimalFormat.format(value);
    }

    public static String getRateAsString(Double value){
        if (value == null){
            return String.format ("%.4f", 0.00);
        }
        return String.format ("%.4f", value);
    }

    public static String getAsString(Double value, String currency){
        if (value == null){
            return String.format ("%.2f %s", 0.00, currency);
        }
        return String.format ("%.2f %s", value, currency);
    }

    public static String substringLn(String inStr, int startPos, int count){
        String result = substring(inStr,startPos,count);
        if (isEmpty(result)) {
            return "";
        }
        return result + "\n";
    }
    public static String substring(String inStr, int startPos, int count){
        String result = "";
        int _startPos = startPos - 1;
        if (isEmpty(inStr) || _startPos < 0) {
            return "";
        }
        if (inStr.length() <= _startPos){
            return "";
        }
        result = inStr.substring(_startPos);
        if (result.length()<=count){
            return result;
        } else {
            return result.substring(0, count);
        }
    }

    public static boolean isNumeric(String str){

        boolean isNumeric = false;
        try {
            Long ll = Long.parseLong(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }

        //return isNumeric;
    }
    public static Long getLong(String str){
        if (StringUtil.isEmpty(str)) return 0L;
        Long value = 0L;
        try {
            value = Long.parseLong(str);
            return value;
        } catch (NumberFormatException e){
            return 0L;
        }

        //return isNumeric;
    }

    public static String trim(String str){

        if (StringUtil.isEmpty(str)){
            return str;
        }
        return str.trim();
    }

}
