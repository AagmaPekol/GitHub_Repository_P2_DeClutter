package com.example.p2_declutter_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClothingDao {

    @Insert
    void addItem(Clothing item);

    @Update
    void updateItem(Clothing item);

    @Delete
    void deleteItem(Clothing item);

}
