package com.login.beachstop.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utilitarios {

    public static String getHourNow() {
        Calendar now = Calendar.getInstance();
        String dataReturn = "";

        dataReturn = String.valueOf(now.get(Calendar.YEAR)) + String.valueOf(now.get(Calendar.MONTH) + 1) + String.valueOf(now.get(Calendar.DATE));
        dataReturn += String.valueOf(now.get(Calendar.HOUR_OF_DAY)) + String.valueOf(now.get(Calendar.MINUTE)) + String.valueOf(now.get(Calendar.SECOND));

        return dataReturn;
    }

    public static boolean hasConnection(Context contexto) {

        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable()))
            return true;
        else
            return false;

    }

    /**
     * Method gerarHash
     *
     * @param plainText
     * @param algorithm The algorithm to use like MD2, MD5, SHA-1, etc.
     * @return String
     * @throws NoSuchAlgorithmException
     * @author Henrique Machado
     */
    public static String gerarHash(String plainText, String algorithm) {

        MessageDigest mdAlgorithm;

        StringBuffer hexString = new StringBuffer();

        try {

            mdAlgorithm = MessageDigest.getInstance(algorithm);

            mdAlgorithm.update(plainText.getBytes());

            byte[] digest = mdAlgorithm.digest();

            for (int i = 0; i < digest.length; i++) {

                plainText = Integer.toHexString(0xFF & digest[i]);

                if (plainText.length() < 2) {

                    plainText = "0" + plainText;
                }

                hexString.append(plainText);
            }

        } catch (Exception e) {

        }

        return hexString.toString();
    }

    public static String getKeyMd5() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        return gerarHash(Constantes.KEY_SERVLET + dateFormat.format(cal.getTime()).toString(), "md5");
    }

    public static JSONArray getAlwaysJsonArray(JSONObject object, String arrayName) {

        JSONArray array = null;

        try {

            array = object.getJSONArray(arrayName);

        } catch (JSONException e) {

            array = new JSONArray();

            try {
                array.put(object.getJSONObject(arrayName));

            } catch (JSONException e1) {

                e1.printStackTrace();

            }

        }

        return array;

    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
