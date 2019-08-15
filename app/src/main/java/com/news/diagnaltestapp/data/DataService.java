package com.news.diagnaltestapp.data;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class DataService {
    private static DataService sInstance;

    public static DataService getInstance() {
        if(sInstance == null) {
            sInstance = new DataService();
        }
        return sInstance;
    }

    public static String readFile(AssetManager mgr, String path) {
        StringBuilder contents = new StringBuilder();
        try (InputStream is = mgr.open(path); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            contents = new StringBuilder(reader.readLine());
            String line = null;
            while ((line = reader.readLine()) != null) {
                contents.append('\n').append(line);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return contents.toString();
    }
}
