package org.toursys.repository.model.wch;

import java.io.Serializable;
import java.util.List;

import org.toursys.repository.model.wch.season.WChRecord;

public class WChTableRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<WChRecord> records;
    private WChTableHeader tableHeader;

    public List<WChRecord> getRecords() {
        return records;
    }

    public void setRecords(List<WChRecord> records) {
        this.records = records;
    }

    public WChTableHeader getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(WChTableHeader tableHeader) {
        this.tableHeader = tableHeader;
    }

}
