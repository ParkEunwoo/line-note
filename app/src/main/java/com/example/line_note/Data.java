package com.example.line_note;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
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

    public void saveData(Context context){
        String filename = "myfile";
        String fileContents = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData(Context context) {
        File directory = context.getFilesDir();
        File file = new File(directory, "myfile");
        FileInputStream inputStream;
        String data = "";
        int size;
        byte[] buf;

        if (file.exists() && file.canRead()) {
            try {
                // open file.
                inputStream = new FileInputStream(file) ;

                size = inputStream.available();
                buf = new byte[size] ;
                // read file.
                while ((size = inputStream.read(buf)) != -1) {
                    data += new String(buf, "UTF-8");
                }
                Log.d("data", data);

                // close file.
                inputStream.close() ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }

    }
}
