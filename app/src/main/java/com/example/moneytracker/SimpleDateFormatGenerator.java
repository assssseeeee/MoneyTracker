package com.example.moneytracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatGenerator {

    public String dateYMD(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return simpleDateFormat.format(date);
    }

    public String dateYMDW(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd ww");
        return simpleDateFormat.format(date);
    }

    




}
