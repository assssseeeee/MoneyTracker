package com.example.moneytracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.moneytracker.data.MoneyTrackerContract;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerDialogFragments extends DialogFragment {
    private CalendarView calendarView;
    private static String selectedDate;
    private static String selectedDateCalendar;
    DateHandler dateHandler;

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

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choose_date, null);

        dateHandler = new DateHandler();
        selectedDate = dateHandler.dateFormatYyyyMmDdHhMm();

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MoneyTrackerContract.DATE_FORMAT);

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDateCalendar = new StringBuilder().append(year).append(".0").append(month + 1)
                        .append(".").append(dayOfMonth).toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                try {
                    Date dateFormat = formatter.parse(selectedDateCalendar);
                    selectedDate = simpleDateFormat.format(dateFormat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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