package com.tanuj.samplejob.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.tanuj.samplejob.database.models.Data;

@Database(entities = {Data.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String NAME = "APP_DB";

    public abstract DataDao dataDao();
}
