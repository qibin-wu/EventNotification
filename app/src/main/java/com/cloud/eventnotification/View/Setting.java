package com.cloud.eventnotification.View;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloud.eventnotification.CloudDB.RDS;
import com.cloud.eventnotification.R;


import java.util.concurrent.ExecutionException;

/*
*  update three user setting time
*
*
* */
public class Setting extends AppCompatActivity {
       private Button btnOK;
       private Button btnRef;
       private EditText edTh;
       private EditText edRA;
       private TextView AndroidID;
       private static int TH=60;
       private static int RA=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnOK=findViewById(R.id.button);
        btnRef=findViewById(R.id.buttonRef);
        edTh=findViewById(R.id.edTh);
        edRA=findViewById(R.id.eTra);
        AndroidID=findViewById(R.id.txtAID);
        AndroidID.setText(Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID));

        new Thread(new Runnable(){
            public void run(){

                TH = RDS.getTh(Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID));

            }
        }).start();
        new Thread(new Runnable(){
            public void run(){

                RA = RDS.getRa(Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID));

            }
        }).start();

        edTh.setText(TH+"");
        edRA.setText(RA+"");

        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ref = new Intent();
                ref.setClass(Setting.this, Setting.class);
                Setting.this.startActivity(ref);
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable(){
                    public void run(){

                       RDS.setTime(Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID),edTh.getText().toString(),edRA.getText().toString());

                    }
                }).start();
                Intent ref = new Intent();
                ref.setClass(Setting.this, MainActivity.class);
                Setting.this.startActivity(ref);
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem home = menu.add(0, 17, 0, "");
        home.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        home.setIcon(R.drawable.home);
        return super.onCreateOptionsMenu(menu);

    }

    //do something by id
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent();
                intent.setClass(this, Setting.class);
                this.startActivity(intent);
                break;
            case 17:
                Intent back = new Intent();
                back.setClass(this, MainActivity.class);
                this.startActivity(back);
                break;

        }
        return true;
    }


}
