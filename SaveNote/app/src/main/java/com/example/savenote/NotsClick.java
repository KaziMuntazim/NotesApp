package com.example.savenote;

import androidx.cardview.widget.CardView;

import com.example.savenote.Model.Nots;

public interface NotsClick {
    void onClick(Nots nots);
    void onPress(Nots nots , CardView cardView);
}
