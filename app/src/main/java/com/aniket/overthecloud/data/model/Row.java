package com.aniket.overthecloud.data.model;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private Integer rowIndex;       // changed
    private String rowLayout;
    private String rowHeader;
    private String rowType;

    private List<RowItem> rowItems;

    public int getRowIndex() {
        return rowIndex != null ? rowIndex : 0;
    }

    public String getRowLayout() {
        return rowLayout != null ? rowLayout : "";
    }

    public String getRowHeader() {
        return rowHeader != null ? rowHeader : "";
    }

    public String getRowType() {
        return rowType != null ? rowType : "";
    }

    public List<RowItem> getRowItems() {
        return rowItems != null ? rowItems : new ArrayList<>();
    }
}