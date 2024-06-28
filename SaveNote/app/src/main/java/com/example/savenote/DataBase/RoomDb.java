package com.example.savenote.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.savenote.Model.Nots;

@Database(entities = Nots.class , version = 1 , exportSchema = false)
public abstract class RoomDb extends RoomDatabase {
    private static RoomDb ddatabase;
    private static String DATABASE_NAME = "mydb";

    public synchronized static RoomDb getInstance(Context context){
        if (ddatabase == null){
            ddatabase = Room.databaseBuilder(context.getApplicationContext() , RoomDb.class , DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return ddatabase;
    }
    public abstract DataF dataF();
}
