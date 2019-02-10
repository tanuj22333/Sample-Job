package com.tanuj.samplejob.activities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.tanuj.samplejob.App;
import com.tanuj.samplejob.R;
import com.tanuj.samplejob.Utils;
import com.tanuj.samplejob.background.NotifyWorker;
import com.tanuj.samplejob.interfaces.SecondPresenter;
import com.tanuj.samplejob.interfaces.SecondView;
import com.tanuj.samplejob.presenters.SecondPresenterImpl;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity implements SecondView, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.record_edt)
    EditText recordEdt;
    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.last_saved_btn)
    Button lastSavedBtn;
    @BindView(R.id.notification_time_btn)
    Button notificationTimeBtn;
    @BindView(R.id.back_btn)
    Button backBtn;

    private SecondPresenter presenter;

    public static Intent createIntent(Context context) {
        return new Intent(context, SecondActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        presenter = new SecondPresenterImpl(this, App.APP_DB);
    }

    @OnClick({R.id.save_btn, R.id.last_saved_btn, R.id.notification_time_btn, R.id.back_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save_btn:
                saveNumber();
                break;
            case R.id.last_saved_btn:
                getLastSavedNumber();
                break;
            case R.id.notification_time_btn:
                showPicker();
                break;
            case R.id.back_btn:
                onBackPressed();
                break;
        }
    }

    private void saveNumber() {
        if (TextUtils.isEmpty(recordEdt.getText().toString())) {
            Utils.showToast(this, R.string.empty_number_input);
            return;
        }

        presenter.saveNumber(Integer.parseInt(recordEdt.getText().toString()));
        recordEdt.getText().clear();
    }

    private void getLastSavedNumber() {
        presenter.getLastSavedNumber();
    }


    private void showPicker() {

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute, false);
        timePickerDialog.show();
    }

    @Override
    public void dataSaveSuccess() {
        Utils.showToast(this, R.string.number_save_success);
    }

    @Override
    public void lastSavedNumber(final Long number) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showToast(SecondActivity.this, getString(R.string.last_saved_number) + String.valueOf(number));
            }
        });
    }

    @Override
    public void errorNoData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showToast(SecondActivity.this, R.string.error_no_record_found);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long millis = calendar.getTimeInMillis();

        if(millis <= System.currentTimeMillis()) {
            Utils.showToast(this, R.string.invalid_time);
            return;
        }

        setBackgroundTask(millis - System.currentTimeMillis());
    }

    private void setBackgroundTask(long millis) {
        Data inputData = new Data.Builder().putInt("key", 1).build();

        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotifyWorker.class)
                .setInitialDelay(millis, TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .addTag("workTag")
                .build();

        WorkManager.getInstance().enqueue(notificationWork);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
