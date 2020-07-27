package com.example.moneytracker;

import com.example.moneytracker.data.MoneyTrackerContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatter {

    Calendar calendar = new GregorianCalendar();

    public String dateFormatYyyyMmDdFfHhMm() {
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MoneyTrackerContract.DATE_FORMAT);
        return  simpleDateFormat.format(date);
    }

    //"2020.01.01 1-03 00:46"
    //"2020.07.27 209-04 18:38"

    public String[] dateTimeFormatter(String startDate, int amountOfDays) {
        String[] dateArray = {""};

//        calendar.set( Calendar.DAY_OF_MONTH - amountOfDays);
//
//
//
//
      return null;
    }


}
