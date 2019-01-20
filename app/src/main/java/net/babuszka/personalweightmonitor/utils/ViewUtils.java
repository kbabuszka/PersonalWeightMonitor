package net.babuszka.personalweightmonitor.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class ViewUtils {

    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }

    public static void snackbarMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}