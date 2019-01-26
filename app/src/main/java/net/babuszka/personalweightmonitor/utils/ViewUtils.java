package net.babuszka.personalweightmonitor.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class ViewUtils {

    public static void toastMessage(Context context, String message, MessageTypes messageTypes) {

        switch (messageTypes) {
            case SUCCESS:   Toasty.success(context, message,Toast.LENGTH_SHORT).show();     break;
            case ERROR:     Toasty.error(context, message,Toast.LENGTH_SHORT).show();       break;
            case WARNING:   Toasty.warning(context, message,Toast.LENGTH_SHORT).show();     break;
            case INFO:      Toasty.info(context, message,Toast.LENGTH_SHORT).show();        break;
            case NORMAL:    Toasty.normal(context, message,Toast.LENGTH_SHORT).show();      break;
        }
    }

    public static void snackbarMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}