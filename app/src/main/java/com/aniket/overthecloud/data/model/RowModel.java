package com.aniket.overthecloud.data.model;

import java.util.List;

public class RowModel {

    private String rowTitle;
    private List<Movie> movieList;

    public RowModel(String rowTitle, List<Movie> movieList) {
        this.rowTitle = rowTitle;
        this.movieList = movieList;
    }

    public String getRowTitle() {
        return rowTitle;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
