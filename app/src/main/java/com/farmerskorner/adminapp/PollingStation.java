package com.farmerskorner.adminapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PollingStation extends AppCompatActivity {
    public static String boothid;
    public String staticName;
    TextView boothName1, polling_station, enter;
    EditText number;
    Button submit;
    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        MainActivity.password.setText(null);
        MainActivity.userName.setText(null);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling_station);

        boothName1 = findViewById(R.id.boothName);
        polling_station = findViewById(R.id.polling_station);
        enter = findViewById(R.id.people);
        staticName = boothName.name;
        boothName1.setText(staticName);
        number = findViewById(R.id.number);
        submit = findViewById(R.id.submit);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        final String time = date.format(currentLocalTime);


        if (MainActivity.lang == 1) {
            updateViews("hi");
        } else {
            updateViews("en");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                people p = new people(getApplicationContext());
                p.execute(boothid, number.getText().toString(), time);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();

        getSupportActionBar().setTitle(resources.getString(R.string.home));
        polling_station.setText(resources.getString(R.string.polling_station));
        enter.setText(resources.getString(R.string.people));
        number.setHint(resources.getString(R.string.no_people));
        submit.setText(resources.getString(R.string.update));


    }
}