package com.example.line_note;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {
    final int REQ_CODE_SELECT_IMAGE=100;
    final int CAPTURE_IMAGE = 200;
    final int NETWORK_URL = 300;
    final CharSequence[] oItems = {"갤러리", "사진촬영"};
    private static final int DIALOG_ID = 15979;
    int position;

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
        complete = findViewById(R.id.edit);
        imageList = findViewById(R.id.imageList);
        newNote = new Note();
        oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        final EditText editText = new EditText(this);
        editText.setHint("url");

        oDialog.setTitle("이미지 불러오기").setView(editText).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int button) {

                dialog.dismiss();

            }

        }).
                setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String uri = editText.getText().toString();
                        if(uri != null){
                            try{
                                FutureTarget<Bitmap> futureTarget = Glide.with(EditActivity.this).asBitmap().load(uri).submit();
                                Bitmap img = futureTarget.get();
                                addImageView(img, newNote.addImage(img, getApplicationContext()));
                                Glide.with(EditActivity.this).clear(futureTarget);
                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(getApplicationContext(),editText.getText().toString() ,Toast.LENGTH_LONG).show();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override

            public void onCancel(DialogInterface dialog) {

                dialog.dismiss();

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
                        dialog.dismiss();

                    }
                })
                .setCancelable(true);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        if(position > -1) {
            newNote = Data.getInstance().getNote(position);
            title.setText(newNote.getTitle());
            content.setText(newNote.getContent());

            int size = newNote.getImageNum();
            for(int i=0;i<size;i++) {
                addImageView(newNote.getImage(i, this), newNote.getImageId(i));
            }
        }

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote.setTitle(title.getText().toString());
                newNote.setContent(content.getText().toString());
                if(position > -1) {
                    Data.getInstance().modifyNote(position, newNote);
                    Snackbar.make(view, "노트가 변경되었습니다.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Data.getInstance().addNote(newNote);
                    Snackbar.make(view, "새 노트가 추가되었습니다.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id, Bundle bundle) {

        switch (id) {

            case DIALOG_ID:

                return oDialog.show();

            default:

                return super.onCreateDialog(id, bundle);

        }

    }
    public void addImageView(Bitmap img, final String id) {
        if(img == null) {
            return ;
        }
        final FrameLayout layout = new FrameLayout(this);
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getDpSize(100));
        layout.setLayoutParams(param);

        ImageView newImageView = new ImageView(this);
        newImageView.setImageBitmap(img);
        newImageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        ImageButton delete = new ImageButton(this);
        delete.setImageResource(R.drawable.ic_close_red_24dp);
        delete.setBackgroundColor(Color.argb(0,0,0,0));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(getDpSize(20), getDpSize(20));
        params.gravity = Gravity.TOP|Gravity.END;
        delete.setLayoutParams(params);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote.deleteImage(newNote.findIndexById(id), getApplicationContext());
                imageList.removeView(layout);
            }
        });
        layout.addView(newImageView);
        layout.addView(delete);
        imageList.addView(layout);
    }

    public int getDpSize(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode == Activity.RESULT_OK)

        {

            if(requestCode == REQ_CODE_SELECT_IMAGE)

            {

                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    BitmapFactory.Options option = new BitmapFactory.Options();
                    option.inSampleSize = 4;
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    addImageView(img, newNote.addImage(img, getApplicationContext()));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            } else if(requestCode == CAPTURE_IMAGE) {
                try {
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    addImageView(img, newNote.addImage(img, getApplicationContext()));
                }catch (Exception e)
                {

                    e.printStackTrace();

                }
            }

        }

    }
    private Bitmap resize(Context context, Uri uri, int resize) {
        Bitmap resizeBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); // 이미지의 크기를 options 에 담아줌

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1; // 숫자가 클수록 용량이 작아짐, 4 라고 하면 4픽셀을 1픽셀로 만들어줌

            while (true) { //가로세로 크기를 리사이즈크기에 최대한 맞춰서 작을때까지 반복함.
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); //정해진 샘플사이즈로 비트맵을 다시만든다
            resizeBitmap = bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resizeBitmap;
    }
}
