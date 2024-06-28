package com.example.savenote.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.savenote.Model.Nots;

import java.util.List;

@Dao
public interface DataF {
    @Insert(onConflict = REPLACE)
    void insert(Nots no);

    @Query("SELECT * FROM myTb ORDER BY ID DESC")
    List<Nots> GetAll();

    @Query("UPDATE myTb SET Title = :title , Notes = :nots WHERE ID = :id")
    void upadte(int id , String title , String nots);

    @Delete
    void delete(Nots no);

    @Query("UPDATE mytb SET Pinne = :pin WHERE ID = :id")
    void pin(int id , boolean pin);
}
