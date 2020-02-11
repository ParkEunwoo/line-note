package com.example.line_note;

import android.net.Uri;

import java.util.ArrayList;

public class Note {
    private String title;
    private String content;
    private ArrayList<Uri> images;

    Note() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addImage(Uri image) {
        images.add(image);
    }

    public void changeImage(int position, Uri image) {
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

    public Uri getThumbnail(){
        if(images.size()>0) {
            return images.get(0);
        } else {
            return null;
        }
    }




}