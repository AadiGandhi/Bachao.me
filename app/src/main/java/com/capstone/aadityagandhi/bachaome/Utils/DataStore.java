package com.capstone.aadityagandhi.bachaome.Utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by aaditya.gandhi on 4/3/16.
 * This handles the file operations required to create persistent data storage
 */
public class DataStore {

    public DataStore(){

    }
    public void writeToApplicationStorage(String content, FILE_TYPE fileType, Context context){
        String fileName = fileType.toString();
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.write(System.getProperty("line.separator").getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readFromApplicationData(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ArrayList<String> lines = new ArrayList<>();
            return lines;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ArrayList<String> lines = new ArrayList<>();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            ArrayList<String> lines = new ArrayList<>();
            return lines;
        }
    }

    public void deleteFromApplicationData(FILE_TYPE file_type, Context context, String value){
        String fileName = file_type.toString();
        ArrayList<String> mList = readFromApplicationData(context,fileName);
        for(int i=0; i< mList.size();i++){
            if(mList.get(i).equals(value)){
                mList.remove(i);
                break;
            }
        }
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.close();
            for (String mstring:mList) {
                writeToApplicationStorage(mstring,file_type,context);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static enum FILE_TYPE {
        UID("uid_store"), OTHER("other");

        private String value;

        private FILE_TYPE(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

}
