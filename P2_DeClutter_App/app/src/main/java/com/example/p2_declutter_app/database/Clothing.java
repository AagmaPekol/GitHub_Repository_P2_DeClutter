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

    @ColumnInfo(name = "image_path")
    public String imageUri;

    @ColumnInfo(name = "decision_keep_donate_sell")
    public String decision;

    @ColumnInfo(name = "ai_text")
    public String aiText;

    @ColumnInfo(name = "session_id")
    public String sessionId;

    public Clothing(String clothingType, String description, String imageUri, String decision, String aiText, String sessionId){
        this.clothingType = clothingType;
        this.description = description;
        this.imageUri = imageUri;
        this.decision = decision;
        this.aiText = aiText;
        this.sessionId = sessionId;
    }
}
