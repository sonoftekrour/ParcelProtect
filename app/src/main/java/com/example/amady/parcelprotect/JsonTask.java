package com.example.amady.parcelprotect;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            StringBuffer buffer = new StringBuffer();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();

            JSONObject parentObj = new JSONObject(finalJson);
            JSONArray parrentArray = parentObj.getJSONArray("Harnais");


            StringBuffer finalBufferData = new StringBuffer();
            for (int i = 0; i < parrentArray.length(); i++ ) {
                JSONObject Harnais = parrentArray.getJSONObject(i);

                int HarnaisID = Harnais.getInt("idHarnais");
                int HarnaisQrCode = Harnais.getInt("QRcode");
                int HarnaisAdresseMac = Harnais.getInt("adresseMAC");
                int HarnaisStatus = Harnais.getInt("status");

                finalBufferData.append("id:" + HarnaisID + "\nQrcode:" + HarnaisQrCode + "\nMAC:" + HarnaisAdresseMac + "\nStatus:"+ HarnaisStatus + "\n\n");

            }
            return finalBufferData.toString();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("postE:", result);
    }
}