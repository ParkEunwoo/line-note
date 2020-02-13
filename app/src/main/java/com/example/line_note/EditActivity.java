package com.example.line_note;

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

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class EditActivity extends AppCompatActivity {
    final int REQ_CODE_SELECT_IMAGE=100;
    final int CAPTURE_IMAGE = 200;
    final int NETWORK_URL = 300;
    final CharSequence[] oItems = {"갤러리", "사진촬영", "url링크"};


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

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                oDialog.setTitle("이미지 불러오기")
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

                                } else if(which == 2) {/*
                                    Intent urlIntent = new Intent();
                                    startActivityForResult(urlIntent, NETWORK_URL);*/

                                    try{
                                        Uri uri = getUriFromUrl("https://www.oracle.com/a/ocom/img/hp11-intl-java-logo.jpg");
                                        ImageView image = (ImageView)findViewById(R.id.imageView);
                                        image.setImageURI(uri);
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }
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
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public Uri getUriFromUrl(String thisUrl) {
        try{
            URL url = new URL(thisUrl);
            Uri.Builder builder =  new Uri.Builder()
                    .scheme(url.getProtocol())
                    .authority(url.getAuthority())
                    .appendPath(url.getPath());
            return builder.build();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addImageView(Uri uri) {
        ImageView newImageView = new ImageView(this);
        newImageView.setImageURI(uri);
        imageList.addView(newImageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode == Activity.RESULT_OK)

        {

            if(requestCode == REQ_CODE_SELECT_IMAGE)

            {

                try {
                    Uri uri = data.getData();
                    Toast.makeText(getBaseContext(), "uri : "+uri.toString(), Toast.LENGTH_SHORT).show();
                    addImageView(uri);
                } catch (Exception e)

                {

                    e.printStackTrace();

                }

            } else if(requestCode == CAPTURE_IMAGE) {
                try {
                    Uri uri = data.getData();
                    addImageView(uri);

                }catch (Exception e)

                {

                    e.printStackTrace();

                }
            } else if(requestCode == NETWORK_URL) {
                try{
                    URL url = new URL("https://sdtimes.com/wp-content/uploads/2019/03/jW4dnFtA_400x400.jpg");
                    Uri.Builder builder =  new Uri.Builder()
                            .scheme(url.getProtocol())
                            .authority(url.getAuthority())
                            .appendPath(url.getPath());
                    Uri uri = builder.build();
                    ImageView image = (ImageView)findViewById(R.id.imageView);
                    image.setImageURI(uri);
                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        }

    }

}
