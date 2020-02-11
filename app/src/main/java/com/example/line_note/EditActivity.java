package com.example.line_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class EditActivity extends AppCompatActivity {
    EditText title;
    EditText content;
    ImageButton imageButton;
    Button complete;
    LinearLayout imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        imageButton = findViewById(R.id.imageButton);
        complete = findViewById(R.id.complete);
        imageList = findViewById(R.id.imageList);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
