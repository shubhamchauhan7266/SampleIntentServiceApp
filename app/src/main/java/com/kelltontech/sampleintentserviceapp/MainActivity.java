package com.kelltontech.sampleintentserviceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ResponseReceiver mReceiver;
    private Button mBtFirst;
    private Button mBtSecond;
    public static final String URL = "url";
    public static final String REQUEST_CODE = "request_code";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtFirst = findViewById(R.id.bt_first);
        mBtSecond = findViewById(R.id.bt_second);

        mBtFirst.setOnClickListener(this);
        mBtSecond.setOnClickListener(this);

        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        mReceiver = new ResponseReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_first:
                String urlFirst ="https://mdn.github.io/learning-area/javascript/oojs/json/superheroes.json";
                Intent firstIntent = new Intent(this, MyService.class);
                firstIntent.putExtra(URL,urlFirst);
                firstIntent.putExtra(REQUEST_CODE,1);
                startService(firstIntent);
                break;
            case R.id.bt_second:
                String urlSecond="https://mdn.github.io/learning-area/javascript/oojs/json/superheroes.json";
                Intent secondIntent = new Intent(this, MyService.class);
                secondIntent.putExtra(URL,urlSecond);
                secondIntent.putExtra(REQUEST_CODE,2);
                startService(secondIntent);
                break;
            default:
                break;
        }
    }

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP = "com.mamlambo.intent.action.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
            int responseCode = intent.getIntExtra(MyService.RESPONSE_CODE,0);
            switch (responseCode){
                case 1:
                    mBtFirst.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    mBtSecond.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }
    }
}
