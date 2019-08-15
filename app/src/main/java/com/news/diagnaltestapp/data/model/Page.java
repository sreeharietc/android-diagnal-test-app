package com.news.diagnaltestapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sreehari
 * on 15/8/19.
 */
class Page {
    private String title;

    @SerializedName("total-content-items")
    private String totalContentItems;

    @SerializedName("page-num")
    private String pageNumber;

    @SerializedName("page-size")
    private String pageSize;

    @SerializedName("content-items")
    private ContentItems contentItems;

    public String getTitle() {
        return title;
    }

    public String getTotalContentItems() {
        return totalContentItems;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public ContentItems getContentItems() {
        return contentItems;
    }
}
