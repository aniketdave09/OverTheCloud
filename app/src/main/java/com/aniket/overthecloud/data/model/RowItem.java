package com.aniket.overthecloud.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class RowItem {

    private String tileType;
    private String tid;
    private String title;
    private String poster;
    private String portrait;
    private String background;

    private Double rating;              // changed
    private String runtime;

    private Integer startTime;          // changed
    private String startIndex;         // changed

    private List<String> target;
    private String type;

    private Boolean useAlternate;       // changed
    private String alternateUrl;
    private String playstoreUrl;

    @SerializedName("package")          // IMPORTANT mapping
    private String packageName;

    private Boolean detailPage;         // changed
    private String source;

    private List<String> genre;
    private String year;
    private List<String> director;
    private List<String> cast;

    private String synopsis;
    private String showTileInfo;
    private String tileWidth;
    private String tileHeight;

    // -------- SAFE GETTERS --------

    public String getTileType() {
        return tileType != null ? tileType : "";
    }

    public String getTid() {
        return tid != null ? tid : "";
    }

    public String getTitle() {
        return title != null ? title : "";
    }

    public String getPoster() {
        return poster != null ? poster : "";
    }

    public String getPortrait() {
        return portrait != null ? portrait : "";
    }

    public String getBackground() {
        return background != null ? background : "";
    }

    public double getRating() {
        return rating != null ? rating : 0.0;
    }

    public String getRuntime() {
        return runtime != null ? runtime : "";
    }

    public int getStartTime() {
        return startTime != null ? startTime : 0;
    }

    public String getStartIndex() {
        return startIndex != null ? startIndex : "0";
    }

    public List<String> getTarget() {
        return target != null ? target : new ArrayList<>();
    }

    public String getType() {
        return type != null ? type : "";
    }

    public boolean isUseAlternate() {
        return useAlternate != null ? useAlternate : false;
    }

    public String getAlternateUrl() {
        return alternateUrl != null ? alternateUrl : "";
    }

    public String getPlaystoreUrl() {
        return playstoreUrl != null ? playstoreUrl : "";
    }

    public String getPackageName() {
        return packageName != null ? packageName : "";
    }

    public boolean isDetailPage() {
        return detailPage != null ? detailPage : false;
    }

    public String getSource() {
        return source != null ? source : "";
    }

    public List<String> getGenre() {
        return genre != null ? genre : new ArrayList<>();
    }

    public String getYear() {
        return year != null ? year : "";
    }

    public List<String> getDirector() {
        return director != null ? director : new ArrayList<>();
    }

    public List<String> getCast() {
        return cast != null ? cast : new ArrayList<>();
    }

    public String getSynopsis() {
        return synopsis != null ? synopsis : "";
    }

    public String getShowTileInfo() {
        return showTileInfo != null ? showTileInfo : "";
    }

    public String getTileWidth() {
        return tileWidth != null ? tileWidth : "";
    }

    public String getTileHeight() {
        return tileHeight != null ? tileHeight : "";
    }
}