package com.sensor.domain;

public class SensorNameTable {
    private String sensorAddress;
    private int id;
    private String sensortype;
    private String tablename;

    public void setSensorAddress(String sensorAddress) {
        this.sensorAddress = sensorAddress;
    }

    public String getSensorAddress() {
        return sensorAddress;
    }

    public int getId() {
        return id;
    }

    public String getSensortype() {
        return sensortype;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSensortype(String sensortype) {
        this.sensortype = sensortype;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }


    @Override
    public String toString(){
        return "表格的名字是： "+tablename+" 传感器地址: "+sensorAddress+" 传感器类型："+sensortype;
    }
}
