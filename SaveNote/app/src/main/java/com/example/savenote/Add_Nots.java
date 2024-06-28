package com.example.savenote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.savenote.Model.Nots;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Nots extends AppCompatActivity {

    Toolbar Tool_bar;
    ImageView img_save;
    EditText Title_view , Notes_view;
    Nots nots;
    boolean oldnotes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_nots);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Tool_bar = findViewById(R.id.Tool_bar);
        img_save = findViewById(R.id.img_save);
        Title_view = findViewById(R.id.Title_view);
        Notes_view = findViewById(R.id.Notes_view);
        nots = new Nots();
        try {
            nots = (Nots) getIntent().getSerializableExtra("old_not");
            Title_view.setText(nots.getTitle());
            Notes_view.setText(nots.getNotes());
            oldnotes = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tit = Title_view.getText().toString();
                String not = Notes_view.getText().toString();

                if (not.isEmpty()){
                    Toast.makeText(Add_Nots.this, "Enter The Notes", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("EEE ,  d MMM yyyy HH:mm a");
                Date date = new Date();
                if (!oldnotes){
                    nots = new Nots();
                }
                nots.setTitle(tit);
                nots.setNotes(not);
                nots.setDate(format.format(date));
                Intent intent = new Intent();
                intent.putExtra("NO" , nots);
                setResult(Activity.RESULT_OK , intent);
                finish();
            }
        });
    }
}