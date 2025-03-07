package com.example.line_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    Note note;
    TextView title;
    TextView content;
    LinearLayout imageList;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        imageList = findViewById(R.id.imageList);
        button = findViewById(R.id.edit);

        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", 0);

        note = Data.getInstance().getNote(position);
        title.setText(note.getTitle());
        content.setText(note.getContent());

        int size = note.getImageNum();
        for (int i = 0; i < size; i++) {
            ImageView newImageView = new ImageView(this);
            newImageView.setImageBitmap(note.getImage(i, this));
            newImageView.setLayoutParams(new LinearLayout.LayoutParams(getDpSize(100), getDpSize(100)));
            imageList.addView(newImageView);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);
            }
        });
    }

    public int getDpSize(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
