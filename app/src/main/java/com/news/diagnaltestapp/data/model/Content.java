package com.news.diagnaltestapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sreehari
 * on 15/8/19.
 */
public class Content {
    private String name;

    @SerializedName("poster-image")
    private String posterImage;

    public String getName() {
        return name;
    }

    public String getPosterImage() {
        return posterImage;
    }
}
