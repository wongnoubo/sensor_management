package com.sensor.domain;

import java.util.List;

import com.sensor.domain.Sensor;
import java.util.ArrayList;

/**
 * 分页
 */
public class SensorGrid {
    private int current;//当前页面号
    private int rowCount;//每页行数
    private int total;//总行数
    private ArrayList<Sensor> rows;

    public int getCurrent() {
        return current;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<Sensor> getRows() {
        return rows;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRows(ArrayList<Sensor> rows) {
        this.rows = rows;
    }
}