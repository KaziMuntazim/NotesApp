package com.example.savenote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.savenote.Adepter.CAdepter;
import com.example.savenote.DataBase.RoomDb;
import com.example.savenote.Model.Nots;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    CAdepter adepter;
    List<Nots> n = new ArrayList<>();
    RoomDb db;
    FloatingActionButton Flo_btn;
    SearchView ser_view;
    Nots setectNot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.R_view);
        Flo_btn = findViewById(R.id.Flo_btn);
        ser_view  = findViewById(R.id.Ser_view);
        db = RoomDb.getInstance(this);
        n = db.dataF().GetAll();
        UpdateRV(n);
        Flo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , Add_Nots.class);
                startActivityForResult(i , 101);
            }
        });
        ser_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Nots> Flist = new ArrayList<>();
        for (Nots sinNot : n){
            if (sinNot.getTitle().toLowerCase().contains(newText.toLowerCase())
            || sinNot.getNotes().toLowerCase().contains(newText.toLowerCase())){
                 Flist.add(sinNot);
            }
        }
        adepter.FilterList(Flist);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101){
            if (resultCode == Activity.RESULT_OK){
                Nots new_nots = (Nots) data.getSerializableExtra("NO");
                db.dataF().insert(new_nots);
                n.clear();
                n.addAll(db.dataF().GetAll());
                adepter.notifyDataSetChanged();
            }
        } else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK){
                Nots new_nots = (Nots) data.getSerializableExtra("NO");
                db.dataF().upadte(new_nots.getID() , new_nots.getTitle(), new_nots.getNotes());
                n.clear();
                n.addAll(db.dataF().GetAll());
                adepter.notifyDataSetChanged();
            }
        }
    }

    private void UpdateRV(List<Nots> n) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2 , LinearLayoutManager.VERTICAL));
        adepter = new CAdepter(MainActivity.this , n , notsClick);
        recyclerView.setAdapter(adepter);
    }
    private final NotsClick notsClick = new NotsClick() {
        @Override
        public void onClick(Nots nots) {
            Intent intent = new Intent(MainActivity.this , Add_Nots.class);
            intent.putExtra("old_not" , nots);
            startActivityForResult(intent , 102);

//
        }

        @Override
        public void onPress(Nots nots, CardView cardView) {
            setectNot = new Nots();
            setectNot = nots;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this , cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.poup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int itemid = item.getItemId();
        if (itemid == R.id.Pi){
            if (setectNot.isPinne()){
                db.dataF().pin(setectNot.getID() , false);
                Toast.makeText(this, "UnPinned", Toast.LENGTH_SHORT).show();
            }else {
                db.dataF().pin(setectNot.getID() , true);
                Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
            }
            n.clear();
            n.addAll(db.dataF().GetAll());
            adepter.notifyDataSetChanged();
            return true;
        } else if (itemid == R.id.delte) {
            db.dataF().delete(setectNot);
            n.remove(setectNot);
            adepter.notifyDataSetChanged();
            Toast.makeText(this, "Notes Deleted", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }
    }
}