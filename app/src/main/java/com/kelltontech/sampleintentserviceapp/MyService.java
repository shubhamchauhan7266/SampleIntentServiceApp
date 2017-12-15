package com.kelltontech.sampleintentserviceapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 15/12/17.
 */

public class MyService extends IntentService {

    public static final String RESPONSE_CODE = "response_code";
    public static final String GET = "GET";

    public MyService() {
        super(MyService.class.getSimpleName());
    }

    public MyService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        String url = intent.getStringExtra(MainActivity.URL);
        int requestCode = intent.getIntExtra(MainActivity.REQUEST_CODE,0);
        //Set methods and timeouts
        try {
            URL myUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();
            connection.setRequestMethod(GET);
            connection.connect();
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            switch (requestCode){
                case 1:
                    broadcastIntent.putExtra(RESPONSE_CODE, 1);
                    sendBroadcast(broadcastIntent);

                    break;
                case 2:

                    broadcastIntent.putExtra(RESPONSE_CODE, 2);
                    sendBroadcast(broadcastIntent);
                    break;
                default:
                    broadcastIntent.putExtra(RESPONSE_CODE, 0);
                    sendBroadcast(broadcastIntent);
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
