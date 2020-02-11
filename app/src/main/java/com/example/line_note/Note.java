package com.example.line_note;

import java.util.ArrayList;

public class Note {
    private String title;
    private String content;
    private ArrayList<String> images;

    Note() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addImage(String image) {
        images.add(image);
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

    public String getShortContent(){
        return content.substring(0, 20);
    }

    public String getThumbnail(){
        if(images.size()>0) {
            return images.get(0);
        } else {
            return null;
        }
    }




}