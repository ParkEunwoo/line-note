package com.example.line_note;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.sql.DataSource;

public class EditActivity extends AppCompatActivity {
    final int REQ_CODE_SELECT_IMAGE=100;
    final int CAPTURE_IMAGE = 200;
    final int NETWORK_URL = 300;
    final CharSequence[] oItems = {"갤러리", "사진촬영"};


    EditText title;
    EditText content;
    ImageButton imageButton;
    Button complete;
    LinearLayout imageList;
    Note newNote;
    AlertDialog.Builder oDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        imageButton = findViewById(R.id.imageButton);
        complete = findViewById(R.id.complete);
        imageList = findViewById(R.id.imageList);
        newNote = new Note();
        oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        final EditText editText = new EditText(this);
        editText.setHint("url");

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                oDialog.setTitle("이미지 불러오기").setView(editText).setPositiveButton("입력",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String uri = editText.getText().toString();
                                if(uri != null){
                                    try{
                                        FutureTarget<Bitmap> futureTarget = Glide.with(EditActivity.this).asBitmap().load(uri).submit();
                                        Bitmap img = futureTarget.get();
                                        addImageView(img);
                                        newNote.addImage(img);
                                        Glide.with(EditActivity.this).clear(futureTarget);
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }
                                }
                                Toast.makeText(getApplicationContext(),editText.getText().toString() ,Toast.LENGTH_LONG).show();
                            }
                        })
                        .setItems(oItems, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(which == 0) {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                                } else if(which == 1) {
                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, CAPTURE_IMAGE);

                                }
                            }
                        })
                        .setCancelable(true)
                        .show();



            }
        });


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote.setTitle(title.getText().toString());
                newNote.setContent(content.getText().toString());
                Data.getInstance().addNote(newNote);
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addImageView(Bitmap img) {
        if(img == null) {
            return ;
        }
        ImageView newImageView = new ImageView(this);
        newImageView.setImageBitmap(img);
        imageList.addView(newImageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode == Activity.RESULT_OK)

        {

            if(requestCode == REQ_CODE_SELECT_IMAGE)

            {

                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    addImageView(img);
                    newNote.addImage(img);
                } catch (Exception e)

                {

                    e.printStackTrace();

                }

            } else if(requestCode == CAPTURE_IMAGE) {
                try {
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    addImageView(img);
                    newNote.addImage(img);
                }catch (Exception e)

                {

                    e.printStackTrace();

                }
            }

        }

    }

}
