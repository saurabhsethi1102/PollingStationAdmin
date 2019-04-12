package com.farmerskorner.adminapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class people extends AsyncTask<String,Void,String> {
    private Context context ;
    public people(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String id = (String)strings[0];
        String person= (String)strings[1];
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        final String time = date.format(currentLocalTime);

        try{
            String link="http://avrutti.com/election/eci/updatePeople.php";
            String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                    URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("person", "UTF-8") + "=" +
                    URLEncoder.encode(person, "UTF-8");
            data += "&" + URLEncoder.encode("time", "UTF-8") + "=" +
                    URLEncoder.encode(time, "UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String("Exception: " + e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new String("Exception: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return new String("Exception: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result){
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        Log.d("Result Activity",result);
    }
}
