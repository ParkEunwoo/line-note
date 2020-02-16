package com.example.line_note;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class Note {
    private String title;
    private String content;
    private ArrayList<String> images;

    Note() {
        images = new ArrayList<String>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addImage(Bitmap image, Context context) {
        String filename = UUID.randomUUID().toString() + ".jpg";
        File file = new File(context.getFilesDir(), filename);

        FileOutputStream outputStream;

        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG,10,outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        images.add(filename);
    }

    public void changeImage(int position, String image) {
        images.set(position, image);
    }

    public void deleteImage(int position) {
        images.remove(position);
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public int getImageNum() {return images.size(); }

    public String getImage(int position) {return images.get(position); }

    public String getShortContent(){
        if(content.length() > 20){
            return content.substring(0, 20);
        } else {
            return content;
        }
    }

    public String getThumbnail(){
        if(images.size()>0) {
            return images.get(0);
        } else {
            return null;
        }
    }




}