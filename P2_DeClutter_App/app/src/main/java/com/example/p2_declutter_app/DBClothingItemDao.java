package com.example.p2_declutter_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DBClothingItemDao {

    @Insert
    public void addItem(DBClothingItem item);

    @Update
    public void updateItem(DBClothingItem item);

    @Delete
    public void deleteItem(DBClothingItem item);

    @Query("SELECT * FROM DB_clothing_items")
    public List<DBClothingItem> getAllItems();

    @Query("SELECT * FROM db_clothing_items WHERE id==:item_id")
    public DBClothingItem getDBClothingItem(int item_id);
}
