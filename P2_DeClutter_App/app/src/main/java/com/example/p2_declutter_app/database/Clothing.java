package com.example.p2_declutter_app.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DB_clothing_items")
public class Clothing {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "clothing_type")
    public String clothingType;

    @ColumnInfo(name = "clothing_description")
    public String description;

    public Clothing(String clothingType, String description){
        this.clothingType = clothingType;
        this.description = description;
    }
}
