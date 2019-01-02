package net.babuszka.personalweightmonitor.utils;

import android.content.Context;
import android.widget.Toast;

public class ViewUtils {
    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }
}