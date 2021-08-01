package com.hoang.backend_learning.utils;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hoang.backend_learning.dto.PhoneNumberDto;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

@Slf4j
public class FnCommon {
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public static Calendar today() {
        return Calendar.getInstance();
    }

    public static String todayStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return dateFormat.format(today().getTime());
    }

    public static boolean isValidPhoneNumber(PhoneNumberDto phoneNumberDto) {
        Phonenumber.PhoneNumber number = new Phonenumber.PhoneNumber();
        number.setCountryCode(phoneNumberDto.getCountryCode())
            .setNationalNumber(phoneNumberDto.getNationalNumber());
        return phoneNumberUtil.isValidNumber(number);
    }

    public static String generateStudentId() {
        int random = new Random().nextInt(999999) + 100000;
        return String.valueOf(random);
    }
}
