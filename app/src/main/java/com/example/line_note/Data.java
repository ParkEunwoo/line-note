package com.example.line_note;

import java.util.ArrayList;

public class Data {
    private static final Data ourInstance = new Data();
    private ArrayList<Note>  noteList;

    public static Data getInstance() {
        return ourInstance;
    }
    public ArrayList<Note> getNoteList(){
        return noteList;
    }
    public Note getNote(int position) {
        return noteList.get(position);
    }

    public void addNote(Note newNote) {
        noteList.add(newNote);
    }

    public void modifyNote(int position, Note modifiedNote) {
        noteList.set(position, modifiedNote);
    }

    public void removeNote(int position) {
        noteList.remove(position);
    }

    private Data() {
        noteList = new ArrayList<Note>();
        for (int i=0; i<15; i++) {
            noteList.add(new Note("제목"+i, "내용입니당", "image"));
        }
    }

}
