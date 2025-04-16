package com.example.p2_declutter_app;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {DBClothingItem.class}, version = 1)
public abstract class ClothingDB extends RoomDatabase {

    private static volatile ClothingDB INSTANCE;

    public abstract DBClothingItemDao DBClothingItemDao();

    public static ClothingDB getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ClothingDB.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ClothingDB.class, "clothing_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
