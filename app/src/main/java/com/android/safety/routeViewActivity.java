package com.android.safety;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class routeViewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_view);

        //show nextToArrive;
        TextView tv1 = (TextView) findViewById(R.id.pracText);
        try {
            tv1.setText(nextToArrive());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //show busLocation
        TextView tv2 = (TextView) findViewById(R.id.pracText2);
        try {
            tv2.setText(busLocation());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //show trainView
        TextView tv3 = (TextView) findViewById(R.id.pracText3);
        try {
            tv3.setText(trainView());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //show busDetours
        TextView tv4 = (TextView) findViewById(R.id.pracText4);
        try {
            tv4.setText(busDetours());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*This method gets a list of the next trains arrive based on an input station
     * of origin and destination station.
     * @return an array of all the items in the list.
     */
    public static String nextToArrive() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://septa.p.rapidapi.com/hackathon/NextToArrive/?req1=Airport%20Terminal%20B&req2=Ardmore&req3=10")
                .get()
                .addHeader("X-RapidAPI-Key", "fb6caa2d9cmsh6fe63631eb2b07cp1a3225jsn6ed622aac633")
                .addHeader("X-RapidAPI-Host", "septa.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String respo = response.body().toString();

        return respo;
    }

    /*Gets a current location and information of a given bus based on a certain
     * route number input.
     * @return an array
     */
    public static String busLocation() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://septa.p.rapidapi.com/hackathon/BusDetours/?req1=2")
                .get()
                .addHeader("X-RapidAPI-Key", "fb6caa2d9cmsh6fe63631eb2b07cp1a3225jsn6ed622aac633")
                .addHeader("X-RapidAPI-Host", "septa.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String respo = response.body().toString();
        /*
        String[] nta = new String[20];
        String splitted[] = respo.split(",");
        for(int i = 0; i < 20; i++) {
            nta[i] = splitted[i].split(":")[1].substring(1, splitted[i].split(":")[1].length()-1);
            System.out.println(nta[i]);
        }*/
        return respo;
    }

    /*Gets any information on bus detours based on a given route number input.
     * @return an array
     */
    public static String busDetours() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://septa.p.rapidapi.com/hackathon/BusDetours/?req1=2")
                .get()
                .addHeader("X-RapidAPI-Key", "fb6caa2d9cmsh6fe63631eb2b07cp1a3225jsn6ed622aac633")
                .addHeader("X-RapidAPI-Host", "septa.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String respo = response.body().toString();
        /*
        String[] nta = new String[20];
        String splitted[] = respo.split(",");
        for(int i = 0; i < 20; i++) {
            nta[i] = splitted[i].split(":")[1].substring(1, splitted[i].split(":")[1].length()-1);
            System.out.println(nta[i]);
        }
        */
        return respo;
    }

    /*Gets information on the current location of a train.
     * @return an array
     */
    public static String trainView () throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://septa.p.rapidapi.com/hackathon/TrainView/")
                .get()
                .addHeader("X-RapidAPI-Key", "fb6caa2d9cmsh6fe63631eb2b07cp1a3225jsn6ed622aac633")
                .addHeader("X-RapidAPI-Host", "septa.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String respo = response.body().toString();
        /*
        String[] nta = new String[20];
        String splitted[] = respo.split(",");
        for(int i = 0; i < 20; i++) {
            nta[i] = splitted[i].split(":")[1].substring(1, splitted[i].split(":")[1].length()-1);
            System.out.println(nta[i]);
        }

         */
        return respo;
    }

    public void goBack () {
        finish();
    }
}