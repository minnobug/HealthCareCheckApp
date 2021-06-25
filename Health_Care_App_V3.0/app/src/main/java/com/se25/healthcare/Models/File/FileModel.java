package com.se25.healthcare.Models.File;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileModel {
    private final static String PATH = "user_data.txt";
    private static void setTempData(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(PATH, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private static String getTempData(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(PATH);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            setTempData("", context);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static String getValueFromTemp(String key, Context context) {
        String data = getTempData(context);
        String split = key + ":";
        String[] split_data = data.split(split);
        if (split_data.length > 1)
            return split_data[1].split(";")[0];
        return "";
    }

    public static void setValueToTemp(String key, Object value, Context context) {
        String data = getTempData(context);
        String split = key + ":";
        String[] split_data = data.split(split);
        if (split_data.length > 1) {
            String[] split_value = split_data[1].split(";");
            data = split_data[0] + key + ":" + value + ((split_value.length > 1) ? (";" + split_value[1]) : (";"));
        } else {
            data = data + key + ":" + value + ";";
        }
        setTempData(data, context);
    }
}
