package com.hezd.www.customdial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DialKeyboard dialKeyboard = findViewById(R.id.keyboard);
        dialKeyboard.setOnCallBtnCLickListener(new DialKeyboard.OnCallBtnClickListener() {
            @Override
            public void onCallBtnClick(String phoneNum) {
                if(!TextUtils.isEmpty(phoneNum))
                    Toast.makeText(MainActivity.this,phoneNum,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
