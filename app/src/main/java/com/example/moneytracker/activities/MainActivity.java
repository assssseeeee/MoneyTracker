package com.example.moneytracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
                intent.putExtra("state", true);
                startActivity(intent);
                break;
            case R.id.buttonExpenseList:
                intent.putExtra("state", false);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_purchase_history:
                intent = new Intent("android.intent.action.PURCHASE_HISTORY");
                startActivity(intent);
                break;
            case R.id.menu_statistics:
                intent = new Intent("android.intent.action.STATISTICS");
                startActivity(intent);
                break;
            case R.id.menu_settings:
                intent = new Intent("android.intent.action.SETTINGS");
                startActivity(intent);
                break;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
