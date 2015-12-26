package com.app.android.laboratorywork3.dao;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidUtils {

    public static void setText(Activity activity, int textViewId, String text) {
        ((TextView) activity.findViewById(textViewId)).setText(text);
    }

    public static void setText(View itemView, int textViewId, String text) {
        ((TextView) itemView.findViewById(textViewId)).setText(text);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
