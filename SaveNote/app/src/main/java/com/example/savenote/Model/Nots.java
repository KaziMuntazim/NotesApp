package com.example.savenote.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "MyTb")
public class Nots implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int ID = 0;
    @ColumnInfo(name = "title")
    String Title = "";
    @ColumnInfo(name = "notes")
    String Notes = "";
    @ColumnInfo(name = "date")
    String Date = "";
    @ColumnInfo(name = "pinne")
    boolean Pinne = false;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public boolean isPinne() {
        return Pinne;
    }

    public void setPinne(boolean pinne) {
        Pinne = pinne;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
