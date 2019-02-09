package com.tanuj.samplejob.presenters;

import android.util.Log;

import com.tanuj.samplejob.database.AppDatabase;
import com.tanuj.samplejob.database.DataDao;
import com.tanuj.samplejob.database.models.Data;
import com.tanuj.samplejob.interfaces.SecondPresenter;
import com.tanuj.samplejob.interfaces.SecondView;

import java.util.List;

public class SecondPresenterImpl implements SecondPresenter {

    private static final String TAG = SecondPresenterImpl.class.getSimpleName();

    private SecondView secondView;
    private DataDao dataDao;

    public SecondPresenterImpl(SecondView secondView, AppDatabase appDatabase) {
        this.secondView = secondView;
        dataDao = appDatabase.dataDao();
    }

    @Override
    public void saveNumber(final long number) {
        runDbOperation(new Runnable() {
            @Override
            public void run() {
                dataDao.insertNumber(new Data(number));
            }
        });
        secondView.dataSaveSuccess();
    }

    @Override
    public void getAllData() {
        runDbOperation(new Runnable() {
            @Override
            public void run() {
                List<Data> allNumbers = dataDao.getAllNumbers();
                Log.d(TAG, allNumbers.toString());
            }
        });
    }

    @Override
    public void getLastSavedNumber() {
        runDbOperation(new Runnable() {
            @Override
            public void run() {
                Data lastNumber = dataDao.getLastNumber();
                if (lastNumber == null) {
                    secondView.errorNoData();
                } else {
                    secondView.lastSavedNumber(lastNumber.getNumber());
                }
            }
        });

    }

    private void runDbOperation(Runnable runnable) {
        new Thread(runnable).start();
    }

//    for now nothing to do here
    @Override
    public void onDestroy() {

    }
}
