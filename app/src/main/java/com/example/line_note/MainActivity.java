package com.example.line_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Note> list = new ArrayList<Note>();
        for (int i=0; i<15; i++) {
            list.add(new Note("제목"+i, "내용입니당.ㅇㅇㄹ.ㅁㅇㄴ르", "image"));
        }


        RecyclerView recyclerView = findViewById(R.id.noteList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;


        ListAdapter adapter = new ListAdapter(list) ;
        recyclerView.setAdapter(adapter) ;
    }
}
