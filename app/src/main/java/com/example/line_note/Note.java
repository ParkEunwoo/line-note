package com.example.line_note;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public String getImageId(int position) {
        return images.get(position);
    }

    public int findIndexById(String id) {
        return images.indexOf(id);
    }

    public String addImage(Bitmap image, Context context) {
        String id = UUID.randomUUID().toString();
        String filename = id + ".jpg";
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
        return id;
    }

    public void changeImage(int position, Bitmap image, Context context) {
        String filename = images.get(position);
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
    }

    public void deleteImage(int position, Context context) {
        context.deleteFile(images.get(position));
        images.remove(position);
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public int getImageNum() {return images.size(); }

    public Bitmap getImage(int position, Context context) {
        String filename = images.get(position);
        String path = context.getFilesDir() + "/" + filename;
        Bitmap image = BitmapFactory.decodeFile(path);
        return image;
    }

    public String getShortContent(){
        if(content.length() > 20){
            return content.substring(0, 20);
        } else {
            return content;
        }
    }

    public Bitmap getThumbnail(Context context){
        if(getImageNum()>0) {
            return getImage(0, context);
        } else {
            return null;
        }
    }




}