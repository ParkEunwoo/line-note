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
        for (int i=0; i<5; i++) {
            Note note = new Note();
            note.setTitle("제목");
            note.setContent("내용dfsjakljdflkas;jflkjasdlkfjkldsajfioenqinfdajkfjkldasfnklndksaf");
            noteList.add(note);
        }
    }

}
