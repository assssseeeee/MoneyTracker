package com.example.moneytracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ChangeProductDialogFragments extends DialogFragment {
    EditText editTextChangeProductDialog;
    EditText editTextChangePriceDialog;


    public static interface ChangeProductDialogListener {
        public void onDialogPositiveClick(String productName, String productPrice);

        void onDialogPositiveClick();

        public void onDialogNegativeClick(String data);
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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_choose_change_product, null);
        Bundle arg = getArguments();

        editTextChangeProductDialog = view.findViewById(R.id.editTextChangeProductDialog);
        editTextChangePriceDialog = view.findViewById(R.id.editTextChangePriceDialog);

        assert arg != null;
        editTextChangeProductDialog.setText(arg.getString("productName"));
        editTextChangePriceDialog.setText(arg.getString("productPrice"));

        builder.setTitle(R.string.title_edit_product_dialog)
                .setView(view).setPositiveButton(R.string.button_save_product, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String changeProduct = editTextChangeProductDialog.getText().toString();
                String changePrice = editTextChangePriceDialog.getText().toString();
                changeProductDialogListener.onDialogPositiveClick(changeProduct, changePrice);
            }
        }).setNegativeButton(R.string.button_delete_product, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }
}
