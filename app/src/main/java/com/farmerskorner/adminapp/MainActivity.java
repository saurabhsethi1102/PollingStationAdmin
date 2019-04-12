package com.farmerskorner.adminapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView adminLogin;

    public static int lang=0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button submit;
    static EditText userName;
    static EditText password;

    public static final String MyPREFERENCES="MyPrefs";
    public static final String uName="userNameKey";
    public static final String Pass="passKey";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Hindi:
                updateViews("hi");
                return true;

            case R.id.English:
                updateViews("en");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit=(Button)findViewById(R.id.logInButton);
        userName= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        adminLogin=findViewById(R.id.admnLogin);
        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login login = new Login(getApplicationContext());
                    String uname = userName.getText().toString();
                    String pass = password.getText().toString();
                    login.execute(uname,pass);
                    editor.putString(uName, uname);
                    editor.putString(Pass, pass);
                    editor.commit();
                }
            });


//        LoginSession loginSession = new LoginSession(this);
//        loginSession.execute();
    }
    public void updateViews(String languageCode){
        Context context=LocaleHelper.setLocale(this, languageCode);
        Resources resources=context.getResources();

        if (languageCode=="hi"){
            lang=1;
        }
        else{
            lang=0;
        }

        getSupportActionBar().setTitle(resources.getString(R.string.home));
        adminLogin.setText(resources.getString(R.string.admin_login));
        userName.setHint(resources.getString(R.string.username));
        password.setHint(resources.getString(R.string.password));
        submit.setText(resources.getString(R.string.login));
    }
}
