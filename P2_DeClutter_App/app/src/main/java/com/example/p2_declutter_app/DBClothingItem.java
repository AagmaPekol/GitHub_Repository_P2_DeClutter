package com.example.p2_declutter_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DB_clothing_items")
public class DBClothingItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "clothing_Type")
    public String clothingType;

    public String description;

    public DBClothingItem(String clothingType, String description){
        this.clothingType = clothingType;
        this.description = description;
    }
}
