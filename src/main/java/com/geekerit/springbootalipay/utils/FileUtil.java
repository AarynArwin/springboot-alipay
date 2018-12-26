package com.geekerit.springbootalipay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aaryn
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    // 文件大小 10MB
    private static final Long FILE_SIZE_LIMIT = 10485760L;

    /**
     * 文件校验
     * @param file
     */
    private static void checkFile(MultipartFile file){
        // 文件内容非空判断
        if (file == null || file.isEmpty()){
           logger.error("文件内容为空");
           return;
        }
        long size = file.getSize();
        // 文件大小判断
        if (size > FILE_SIZE_LIMIT){
            logger.error("文件内容过大");
        }
    }

    /**
     * 文件字节转换
     * @param size 文件大小（字节）
     * @return     文件通用单位
     */
    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }
}
