package com.getguard.client.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class UIUtils {

    public static void showError(Context context, String title, String message) {
        String titleDefault = "Ошибка";
        String messageDefault = "Что пошло не так";
        if (title != null) titleDefault = title;
        if (message != null) messageDefault = message;
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(titleDefault);
        alertDialog.setMessage(messageDefault);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ок",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

}
