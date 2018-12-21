package com.geekerit.springbootalipay.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Aaryn
 */
public class DateUtil {

    /**
     * 生成当前时间的年月日时分秒字符串
     * @return
     */
    public static String nowTimeString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        String format = formatter.format(now);
        return format;
    }
}
