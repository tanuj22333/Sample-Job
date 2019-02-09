package com.tanuj.samplejob.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tanuj.samplejob.database.models.Data;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * from Data")
    public List<Data> getAllNumbers();

    @Insert
    public Long insertNumber(Data data);

    @Query("SELECT * FROM Data ORDER BY ID DESC LIMIT 1")
    public Data getLastNumber();

    @Query("DELETE FROM Data")
    public void deleteAllTableData();
}
