package com.example.line_note;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {
    private static final Data ourInstance = new Data();
    private ArrayList<Note> noteList;
    private Context context;

    public static Data getInstance() {
        return ourInstance;
    }

    public void initContext(Context ctx) {
        if (context == null) {
            context = ctx;
            loadData(ctx);
        }
    }

    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public Note getNote(int position) {
        return noteList.get(position);
    }

    public void addNote(Note newNote) {
        noteList.add(newNote);
        saveData(context);
    }

    public void modifyNote(int position, Note modifiedNote) {
        noteList.set(position, modifiedNote);
        saveData(context);
    }

    public void removeNote(int position) {
        noteList.remove(position);
        saveData(context);
    }

    private Data() {
        noteList = new ArrayList<Note>();
        context = null;
    }

    private String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(noteList);
        return json;
    }

    private ArrayList<Note> parseJson(String json) {
        Gson gson = new Gson();
        ArrayList<Note> list = gson.fromJson(json, new TypeToken<ArrayList<Note>>() {
        }.getType());
        return list;
    }

    public void saveData(Context context) {
        String filename = "data.json";
        String fileContents = toJson();
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
        File file = new File(directory, "data.json");
        FileInputStream inputStream;
        String data = "";
        int size;
        byte[] buf;

        if (file.exists() && file.canRead()) {
            try {
                // open file.
                inputStream = new FileInputStream(file);

                size = inputStream.available();
                if (size == 0) {
                    return;
                }
                if (size < 512) {
                    buf = new byte[size];
                } else {
                    buf = new byte[512];
                }
                // read file.
                while (inputStream.read(buf) != -1) {
                    data += new String(buf, "UTF-8");
                }
                Log.d("data", data);
                noteList = parseJson(data);

                // close file.
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
