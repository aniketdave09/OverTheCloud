package com.aniket.overthecloud.data.model;

import java.util.ArrayList;
import java.util.List;

public class MainResponse {

    private Integer rowCount;     // changed
    private List<Row> rows;

    public int getRowCount() {
        return rowCount != null ? rowCount : 0;
    }

    public List<Row> getRows() {
        return rows != null ? rows : new ArrayList<>();
    }
}