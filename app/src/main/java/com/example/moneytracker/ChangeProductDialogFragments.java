package com.example.moneytracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ChangeProductDialogFragments extends DialogFragment {

    public static interface ChangeProductDialogListener{
        public void onDialogPositiveClick(String data);
    }

    ChangeProductDialogListener changeProductDialogListener;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            changeProductDialogListener = (ChangeProductDialogFragments.ChangeProductDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DataPickerDialogListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        return null;
    }
}
