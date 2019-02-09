package com.tanuj.samplejob.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.tanuj.samplejob.R;
import com.tanuj.samplejob.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {

    private static final String TAG = FirstActivity.class.getSimpleName();
    public static final String COMING_FROM_NOTIFICATION = "coming_from_notification";

    @BindView(R.id.btn_second_activity)
    Button btnSecondActivity;
    @BindView(R.id.show_notification_btn)
    Button showNotificationBtn;
    @BindView(R.id.chronometer)
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);

        notificationAction(getIntent());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date today = new Date();
                String result = formatter.format(today);
                chronometer.setText(result);
            }
        });
        chronometer.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        notificationAction(intent);
    }

    private void notificationAction(Intent intent) {
        if (intent.hasExtra(COMING_FROM_NOTIFICATION)) {
            Toast.makeText(this, getString(R.string.do_planned_work),Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.show_notification_btn, R.id.btn_second_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_notification_btn:
                Utils.showNotification(this);
                break;
            case R.id.btn_second_activity:
                startActivity(SecondActivity.createIntent(this));
                break;
        }
    }
}
