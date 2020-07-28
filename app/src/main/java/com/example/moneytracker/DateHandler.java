package com.example.moneytracker;

import android.annotation.SuppressLint;
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

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MoneyTrackerContract.DATE_FORMAT);;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MoneyTrackerContract.DATE_FORMAT);;

    public String dateFormatYyyyMmDdHhMm() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    public ArrayList<String> dateTimeFormatter(String startDate, int amountOfDays) {
        ArrayList<String> arrayList = new ArrayList<>();
        LocalDateTime localDate = LocalDateTime.parse(startDate, dateTimeFormatter);
        Date date = convertToDateViaInstant(localDate);

        Calendar calendarStart = new GregorianCalendar();
        Calendar calendarEnd = new GregorianCalendar();

        calendarStart.setTime(date);
        calendarEnd.setTime(date);
        calendarEnd.add(Calendar.DAY_OF_MONTH, -amountOfDays);

        while (!(calendarStart.equals(calendarEnd))) {
            String value = simpleDateFormat.format(calendarStart.getTime());
            arrayList.add(value);
            calendarStart.add(Calendar.DAY_OF_MONTH, -1);
        }
        return arrayList;
    }

    Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
