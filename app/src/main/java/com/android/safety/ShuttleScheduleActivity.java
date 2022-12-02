package com.android.safety;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Calendar;

public class ShuttleScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle_schedule);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        String eachOut;

        //West Shuttle mandeville time
        TextView txtView = (TextView) findViewById(R.id.westMand);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView.setText(eachOut);

        //Cardinal Gate time
        TextView txtView2 = (TextView) findViewById(R.id.cardGateTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView2.setText(eachOut);

        //Merion gardens time
        TextView txtView3 = (TextView) findViewById(R.id.merionTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView3.setText(eachOut);

        //Overbrook and City Ave Time
        TextView txtView4 = (TextView) findViewById(R.id.overbrookCityTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView4.setText(eachOut);

        //Cardinal Ave Time
        TextView txtView5 = (TextView) findViewById(R.id.cardAveTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView5.setText(eachOut);

        //reset times for different shuttle
        min = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR);

        //East Mandeville Time
        TextView txtView6 = (TextView) findViewById(R.id.mandTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView6.setText(eachOut);

        //50th and City Ave Time
        TextView txtView7 = (TextView) findViewById(R.id.fiftyCityTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView7.setText(eachOut);

        //40th and City Ave Time
        TextView txtView8 = (TextView) findViewById(R.id.fourtyCityTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView8.setText(eachOut);

        //Target Time
        TextView txtView9 = (TextView) findViewById(R.id.targetTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView9.setText(eachOut);

        //Presidential Green Time
        TextView txtView10 = (TextView) findViewById(R.id.presTime);
        if (min + 5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView10.setText(eachOut);

        //Bala Shopping Center Time
        TextView txtView11 = (TextView) findViewById(R.id.balaShopTime);
        if (min+5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView11.setText(eachOut);

        //Bala and City Ave Time
        TextView txtView12 = (TextView) findViewById(R.id.balaCityTime);
        if (min+5 >= 60) {
            hour += 1;
            min = min + 5 - 60;
        } else {
            min = min + 5;
        }
        if (min < 10) {
            eachOut = "" + hour + ":" + "0"+min;
        } else {
            eachOut = "" + hour + ":" + min;
        }
        txtView12.setText(eachOut);
    }

    public void seeRoutes(View v) {
        Intent routeView = new Intent(this, routeViewActivity.class);
        startActivity(routeView);
    }

    public void goBack( ){
        finish();
    }
}