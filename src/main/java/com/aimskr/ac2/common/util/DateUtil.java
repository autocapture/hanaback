package com.aimskr.ac2.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtil {
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_SLASH = "yyyy/MM/dd";
    public static final String YYYYMMDD_DASH = "yyyy-MM-dd";
    public static final String DATETIME_HANA = "yyyyMMddHHmmss";
    public static final String DATETIME_NANO = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String DATETIME = "yyyy-MM-dd HH:mm";

    public static String nowWithFormat(String format) {
        LocalDateTime now = LocalDateTime.now();

        // 원하는 형식을 지정합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        // 날짜와 시간을 지정된 형식으로 변환합니다.
        return now.format(formatter);
    }



    /**
     * 날짜를 담은 문자열이 주어진 형식과 일치하는지 확인한다.
     */
    public static boolean checkDateFormat(String date, String format) {
        if (!StringUtils.hasText(date) || !StringUtils.hasText(format)) return false;
        if (!(format.equals(YYYYMMDD) || !format.equals(YYYYMMDD_SLASH) || !format.equals(YYYYMMDD_DASH) || !format.equals(DATETIME_HANA))) {
            log.error("[checkDateFormat] Wrong format. format = {}", format);
            return false;
        }

        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat(format);
            dateFormatParser.setLenient(false);
            dateFormatParser.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ISO 형식(YYYY-MM-DD)의 문자열을 받아, LocalDate로 생성하여 리턴한다.
     */
    public static LocalDate convertLocalDateFromISO(String date) {
        if (!StringUtils.hasText(date)) return null;
        if (!checkDateFormat(date, YYYYMMDD_DASH)) {
            log.error("[DateUtil] convertLocalDateFromISO() : 날짜 형식이 올바르지 않습니다. date = {}", date);
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }


    /**
     * KOKAO 형식(NANO Sec)의 문자열을 받아, LocalDateTime으로 생성하여 리턴한다.
     */
    public static LocalDateTime convertLocalDateTimeFromNano(String datetime) {
        if (!StringUtils.hasText(datetime)) return null;
        if (!checkDateFormat(datetime, DATETIME_HANA)) {
            log.error("[DateUtil] convertLocalDateTimeFromNano() : 날짜시간 형식이 올바르지 않습니다. date = {}", datetime);
            return null;
        }
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DATETIME_HANA));
    }

    /**
     * 날짜 스트링을 형식을 변경해서 리턴한다.
     */
    public static String convertFormat(String date, String fromFormat, String toFormat) {
        if (!StringUtils.hasText(date) || !StringUtils.hasText(fromFormat) || !StringUtils.hasText(toFormat))
            return null;

        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat(fromFormat);
            dateFormatParser.setLenient(false);
            SimpleDateFormat dateFormatFormatter = new SimpleDateFormat(toFormat);
            dateFormatFormatter.setLenient(false);
            return dateFormatFormatter.format(dateFormatParser.parse(date));
        } catch (Exception e) {
            return null;
        }
    }
}
