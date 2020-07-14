package com.example.moneytracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.moneytracker.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonIncomeList, buttonExpenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonIncomeList = findViewById(R.id.buttonIncomeList);
        buttonExpenseList = findViewById(R.id.buttonExpenseList);
        buttonIncomeList.setOnClickListener(this);
        buttonExpenseList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, IncomeListActivity.class);
        switch (v.getId()) {
            case R.id.buttonIncomeList:





                break;
            case R.id.buttonExpenseList:

                break;
            default:
                break;
        }
    }
}
