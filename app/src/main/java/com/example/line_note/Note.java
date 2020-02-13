package com.example.line_note;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

public class Note {
    private String title;
    private String content;
    private ArrayList<Bitmap> images;

    Note() {
        images = new ArrayList<Bitmap>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addImage(Bitmap image) {
        images.add(image);
    }

    public void changeImage(int position, Bitmap image) {
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

    public String getShortContent(){
        if(content.length() > 20){
            return content.substring(0, 20);
        } else {
            return content;
        }
    }

    public Bitmap getThumbnail(){
        if(images.size()>0) {
            return images.get(0);
        } else {
            return null;
        }
    }




}