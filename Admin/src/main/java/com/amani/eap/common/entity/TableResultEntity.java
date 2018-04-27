package com.amani.eap.common.entity;

import java.util.List;


public class TableResultEntity<T> {

    TableData<T> data;

    public TableResultEntity(long total, List<T> rows) {
        this.data = new TableData<T>(total, rows);
    }

    public TableResultEntity() {
        this.data = new TableData<T>();
    }

    TableResultEntity<T> total(int total) {
        this.data.setTotal(total);
        return this;
    }

    TableResultEntity<T> total(List<T> rows) {
        this.data.setRows(rows);
        return this;
    }

    public TableData<T> getData() {
        return data;
    }

    public void setData(TableData<T> data) {
        this.data = data;
    }

    class TableData<T> {
        long total;
        List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public TableData() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }
}
