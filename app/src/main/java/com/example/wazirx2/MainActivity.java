package com.example.wazirx2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    private ListView lv;
    String name,price1,price2;

    String JSON_URL = "https://api.wazirx.com/api/v2/market-status";
    ArrayList<HashMap<String,String>> priceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceList=new ArrayList<>();
        lv=findViewById(R.id.listview);
        GetData getData=new GetData();
        getData.execute();

        notificationManager=NotificationManagerCompat.from(this);
    }

    public class GetData extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... strings) {
            StringBuilder current= new StringBuilder();
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {

                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);
                    int data;
                    data = isr.read();
                    while (data != -1) {
                        current.append((char) data);
                        data = isr.read();

                    }

                    return current.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }

            return current.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("markets");
                for(int i=0;i< jsonArray.length();i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    name = jsonObject1.getString("baseMarket");
                    price1 = jsonObject1.getString("high");
                    price2 = jsonObject1.getString("low");


                    HashMap<String, String> markets = new HashMap<>();
                    markets.put("baseMarket", name);
                    markets.put("high", price1);
                    markets.put("low", price2);
                    priceList.add(markets);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            SimpleAdapter adapter= new SimpleAdapter(
                    MainActivity.this,
                    priceList,
                    R.layout.row_layout,
                    new String[] {"baseMarket","high","low"},
                    new int[]{R.id.textView, R.id.textView2,R.id.textView3});

            lv.setAdapter(adapter);

        }
    }

    if()
    {
        Notification notification=new NotificationCompat.Builder(this,APP.CHANNEL_1_ID)
                .setContentTitle("this is notification")
                .setContentText("price is increased")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        NotificationManager.notify(1,
                notification);

    }





}