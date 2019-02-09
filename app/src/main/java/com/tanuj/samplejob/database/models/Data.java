package com.tanuj.samplejob.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Data {

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    Long id;

    @ColumnInfo
    Long number;

    public Data(Long number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
