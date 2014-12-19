package com.login.beachstop.garcom.android.utils;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

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
     * @throws java.security.NoSuchAlgorithmException
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

    public static boolean isDigit(String s) {
        return s.matches("[0-9]*");
    }

    /**
     * Converte uma String para um objeto Date. Caso a String seja vazia ou nula,
     * retorna null - para facilitar em casos onde formulários podem ter campos
     * de datas vazios.
     * @param data String no formato dd/MM/yyyy a ser formatada
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula
     * @throws Exception Caso a String esteja no formato errado
     */
    public static java.sql.Date formataData(String data) throws Exception {
        if (data == null || data.equals(""))
            return null;

        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date( ((java.util.Date)formatter.parse(data)).getTime() );
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static String joinToString(Collection<?> collection, CharSequence separator) {

        if (collection.isEmpty()) {
            return "";
        } else {
            StringBuilder sepValueBuilder = new StringBuilder();
            for (Object obj : collection) {
                // Append the valuen and the separator even if it's the las
                // element
                sepValueBuilder.append(obj).append(separator);
            }
            // Remove the last separator
            sepValueBuilder.setLength(sepValueBuilder.length() - separator.length());
            return sepValueBuilder.toString();
        }
    }
}
