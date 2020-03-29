package com.rohegde7.moengage.remote.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.view.View;
import android.view.Window;

public class UiUtil {

    private static ProgressDialog dialog;

    public static void displayProgress(Context context, String msg) {
        // This has been called from worker thread
        if (Looper.myLooper() != Looper.getMainLooper()) return;

        hideProgress();

        if (context instanceof Activity) {
            Activity activity = ((Activity) context);
            if (activity.isDestroyed() || activity.isFinishing()) return;

            dialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage(msg);
            dialog.show();
        }
    }

    public static void hideProgress() {
        if (dialog != null && dialog.isShowing()) {
            Window window = dialog.getWindow();

            if (window == null) return;

            View decor = window.getDecorView();

            if (decor.getParent() != null) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

    /*
    * Open an URL in browser
    * */
    public static void startWebView(Context context, String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(webIntent);
    }
}
