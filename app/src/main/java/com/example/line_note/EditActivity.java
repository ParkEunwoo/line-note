package com.example.line_note;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    final int REQ_CODE_SELECT_IMAGE=100;
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
                                Toast.makeText(getApplicationContext(),
                                        oItems[which], Toast.LENGTH_LONG).show();
                            }
                        })
                        .setCancelable(false)
                        .show();


/*
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);

                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
*/
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
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {





        Toast.makeText(getBaseContext(), "resultCode : "+resultCode, Toast.LENGTH_SHORT).show();



        if(requestCode == REQ_CODE_SELECT_IMAGE)

        {

            if(resultCode== Activity.RESULT_OK)

            {

                try {

                    //Uri에서 이미지 이름을 얻어온다.

                    //String name_Str = getImageNameToUri(data.getData());



                    //이미지 데이터를 비트맵으로 받아온다.

                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    ImageView image = (ImageView)findViewById(R.id.imageView);



                    //배치해놓은 ImageView에 set

                    image.setImageBitmap(image_bitmap);





                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();





                } catch (FileNotFoundException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (IOException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (Exception e)

                {

                    e.printStackTrace();

                }

            }

        }

    }
    public String getImageNameToUri(Uri data)

    {

        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = managedQuery(data, proj, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);



        cursor.moveToFirst();



        String imgPath = cursor.getString(column_index);

        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);



        return imgName;

    }

*/
}
