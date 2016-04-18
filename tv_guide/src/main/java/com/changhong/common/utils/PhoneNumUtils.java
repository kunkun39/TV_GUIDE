package com.changhong.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * User: pengjie
 * Date: 2016/3/30
 * Time: 15:12
 */
public class PhoneNumUtils {
    public static boolean isPhoneNum(String phoneNum) {
        if(!StringUtils.hasText(phoneNum)) {
            return false;
        }
        return Pattern
                .compile("^1[3|4|5|7|8][0-9]\\d{8}$")
                .matcher(phoneNum)
                .matches();
    }
}
