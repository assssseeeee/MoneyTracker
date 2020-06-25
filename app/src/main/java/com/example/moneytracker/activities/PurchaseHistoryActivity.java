package com.example.moneytracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;

import com.example.moneytracker.R;

import java.util.Calendar;

public class PurchaseHistoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        CalendarView calendarView = findViewById(R.id.calendarView);

    }
}
