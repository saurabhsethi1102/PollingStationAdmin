package com.farmerskorner.adminapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static com.farmerskorner.adminapp.PollingStation.boothid;

public class Login extends AsyncTask<String,Void,String> {
    private Context context ;
    public Login(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String uname = (String)strings[0];
        String password = (String)strings[1];
        try{
            String link="http://avrutti.com/election/eci/admin_login.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(uname, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
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
        Log.d("Result Activity",result);
        if (result.equals("0")){
            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            boothid = result;
            boothName bn = new boothName(context);
            bn.execute(boothid);
        }

    }
}
