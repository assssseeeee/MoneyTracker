package com.example.moneytracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        Intent intent;
        switch (v.getId()) {
            case R.id.buttonIncomeList:
                intent = new Intent(this, IncomeSheetActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonExpenseList:
                intent = new Intent(this, ExpenseSheetActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
