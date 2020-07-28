package com.example.moneytracker;

import android.util.Log;

import com.example.moneytracker.data.MoneyTrackerContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHandler {

    DateTimeFormatter dateTimeFormatter;


    public String dateFormatYyyyMmDdFfHhMm() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MoneyTrackerContract.DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    //"2020.01.01 1-03 00:46"
    //"2020.07.27 209-04 18:38"

    public ArrayList<String> dateTimeFormatter(String startDate, int amountOfDays) {
        ArrayList arrayList = new ArrayList();
        dateTimeFormatter = DateTimeFormatter.ofPattern(MoneyTrackerContract.DATE_FORMAT);
        LocalDateTime localDate = LocalDateTime.parse(startDate, dateTimeFormatter);

        Date date = convertToDateViaInstant(localDate);
        Calendar calendarStart = new GregorianCalendar();
        Calendar calendarEnd = new GregorianCalendar();

        calendarStart.setTime(date);
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.DAY_OF_MONTH, -amountOfDays);

        while (calendarStart != calendarEnd) {
            arrayList.add(calendarStart.toString());
            calendarStart.set(Calendar.DAY_OF_MONTH, -1);
        }

        return arrayList;
    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
