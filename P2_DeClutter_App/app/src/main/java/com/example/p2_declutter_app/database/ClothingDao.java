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

    @Query("SELECT * FROM db_clothing_items")
    List<Clothing> getAllItems();

    @Query("SELECT * FROM db_clothing_items WHERE clothing_type = :type AND decision_keep_donate_sell = :decision")
    List<Clothing> getItemsByTypeAndDecision(String type, String decision);

    @Query("SELECT DISTINCT clothing_type FROM DB_clothing_items WHERE decision_keep_donate_sell = :decision")
    List<String> getClothingTypeByDecision(String decision);

    @Query("SELECT COUNT(*) FROM DB_clothing_items WHERE decision_keep_donate_sell = :decision AND clothing_type = :clothingType")
    int getCountForClothingTypeAndDecision(String decision, String clothingType);

    @Query("SELECT * FROM DB_clothing_items WHERE decision_keep_donate_sell = :decision AND session_id = :sessionId")
    List<Clothing> getClothingByDecisionAndSession(String decision, String sessionId);
}
