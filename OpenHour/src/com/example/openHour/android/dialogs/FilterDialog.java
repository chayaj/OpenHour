package com.example.openHour.android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.example.openHour.android.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: JessicaC
 * Date: 8/14/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterDialog extends DialogFragment {

    /**
     * Listener
     */
    private DialogInterface.OnClickListener positiveButton;
    private DialogInterface.OnClickListener negativeButton;

    /**
     * Constructor
     *
     * @param positiveButton
     */
    public FilterDialog(DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton) {
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.filter_title)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .create();
    }

}