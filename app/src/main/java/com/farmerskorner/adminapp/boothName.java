package com.farmerskorner.adminapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class boothName extends AsyncTask<String, Void, String> {
    public static String name;
    private Context context ;
    public boothName(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String id = (String)strings[0];
        try{
            String link="http://avrutti.com/election/eci/getBoothName.php";
            String data  = URLEncoder.encode("id", "UTF-8") + "=" +
                    URLEncoder.encode(id, "UTF-8");
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
        name = result;
        Intent i1=new Intent(context,PollingStation.class);
        context.startActivity(i1);
    }
}
