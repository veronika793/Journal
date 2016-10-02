package com.veronica.myjournal.managers;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;

import com.veronica.myjournal.R;


/**
 * Created by Veronica on 9/28/2016.
 */
public class DialogManager implements DialogInterface.OnCancelListener{

    private Context _context;

    public DialogManager(Context ctx) {
        this._context = ctx;
    }

    public void showDialog(Drawable icon,String msg, String title){
        AlertDialog.Builder builder =  new AlertDialog.Builder(_context);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(icon);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(_context.getResources().getColor(R.color.colorPrimary));
            }
        });

        alertDialog.show();

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }
}
