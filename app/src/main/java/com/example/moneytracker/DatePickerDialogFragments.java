package com.example.moneytracker;

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

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerDialogFragments extends DialogFragment {
    private CalendarView calendarView;
    private String selectedDate;

    public static interface DatePickerDialogListener {
        public void onDialogPositiveClick(String dialogSelectedDate);
    }

    DatePickerDialogListener datePickerDialogListener;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            datePickerDialogListener = (DatePickerDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPickerDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choose_date, null);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        selectedDate = dateFormat.format(date);

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = new StringBuilder().append(year).append(".0").append(month + 1)
                        .append(".").append(dayOfMonth).toString();
            }
        });

        builder.setTitle(R.string.title_set_date)
                .setView(view).setPositiveButton(R.string.button_alert_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                datePickerDialogListener.onDialogPositiveClick(selectedDate);
            }
        });
        return builder.create();
    }
}