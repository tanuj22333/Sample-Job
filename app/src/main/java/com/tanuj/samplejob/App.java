package com.tanuj.samplejob;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.tanuj.samplejob.database.AppDatabase;

public class App extends Application {

    public static App BASE_CONTEXT;
    public static AppDatabase APP_DB;

    @Override
    public void onCreate() {
        super.onCreate();

        BASE_CONTEXT = this;
        APP_DB = Room.databaseBuilder(this, AppDatabase.class, APP_DB.NAME).fallbackToDestructiveMigration().build();
    }
}
