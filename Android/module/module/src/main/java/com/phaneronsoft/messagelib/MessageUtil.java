package com.phaneronsoft.messagelib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.net.URLEncoder;

public class MessageUtil {

    private final static String TAG = "MessageUtil";

    public enum App {
        WHATSAPP("com.whatsapp"),
        MESSENGER("com.facebook.orca");

        private final String text;

        App(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }


    public static void sendMessenger(Context context, String number)
    {
        send(context, number, App.MESSENGER);
    }

    public static void sendWhatsapp(Context context, String number, String message)
    {
        send(context, message, number, App.WHATSAPP);
    }

    public static void send(Context pContext, String number, App app) {
        send(pContext, "", number, app);
    }

    public static void send(Context pContext, String message, String number, App app) {
        try {
            Log.d(TAG, "===> app: " + app.toString() + " message: " + message + " number:" + number);
            switch (app) {
                case WHATSAPP:
                    if (isPackageInstalled(pContext, App.WHATSAPP.toString())) {
                        try {
                            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(message, "UTF-8");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            pContext.startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        install(pContext, App.WHATSAPP.toString());
                    }
                    break;

                case MESSENGER:
                    if (isPackageInstalled(pContext, App.MESSENGER.toString())) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setPackage("com.facebook.orca");
                        intent.setData(Uri.parse("https://m.me/"+ number));
                        pContext.startActivity(intent);
                    } else {
                        install(pContext, App.MESSENGER.toString());
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isPackageInstalled(Context pContext, String targetPackage) {
        PackageManager pm = pContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    private static void install(Context pContext, String targetPackage) {
        try {
            Log.d(TAG, "===> install app: " + targetPackage);
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + targetPackage));
            pContext.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
