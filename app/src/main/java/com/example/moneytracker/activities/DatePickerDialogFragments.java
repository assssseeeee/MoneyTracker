package com.example.moneytracker.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.DialogFragment;

import com.example.moneytracker.R;

public class DatePickerDialogFragments extends DialogFragment {
    private CalendarView calendarView;
    private String selectedDate;


    public interface DataPickerDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }


    DataPickerDialogListener dataPickerDialogListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        calendarView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = new StringBuilder().append(year).append(".0").append(month + 1)
                        .append(".").append(dayOfMonth).toString();
                Log.d("selectedDate", selectedDate);
            }
        });
        return inflater.inflate(R.layout.dialog_choose_date, container, false);
    }

//    @Override
//    public void onAttach(@NonNull Activity activity) {
//        super.onAttach(activity);
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            dataPickerDialogListener = (DataPickerDialogListener) activity;
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(activity.toString()
//                    + " must implement NoticeDialogListener");
//        }
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}