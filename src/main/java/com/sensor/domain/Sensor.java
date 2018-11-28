package com.sensor.domain;

import java.math.BigDecimal;

public class Sensor {
    private long id;
    private String name;
    private double value;
    private String sensorAddress;
    private String sensorIntroduction;
    private BigDecimal price;
    private int sensorState;
    private int temperature;
    private int humidity;
    private double cputemp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSensorAddress() {
        return sensorAddress;
    }

    public void setSensorAddress(String sensorAddress) {
        this.sensorAddress = sensorAddress;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal sensorPrice) {
        this.price = sensorPrice;
    }

    public String getSensorIntroduction() {
        return sensorIntroduction;
    }

    public void setSensorIntroduction(String sensorIntroduction) {
        this.sensorIntroduction = sensorIntroduction;
    }

    public int getSensorState() {
        return sensorState;
    }

    public void setSensorState(int sensorState) {
        this.sensorState = sensorState;
    }

    public void setValue(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public int getTemperature(){
        return this.temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setTemperature(int tempreature) {
        this.temperature = tempreature;
    }

    public void setCputemp(double cputemp) {
        this.cputemp = cputemp;
    }

    public int getHumidity(){
        return this.humidity;
    }

    public double getCputemp(){
        return this.cputemp;
    }

    @Override
    public String toString(){
        if(sensorState==1)
            return "传感器名字为："+name+" 传感器位置："+sensorAddress+" 传感器详细信息："
                    +sensorIntroduction+" 传感器状态：正常运行";
        else
            return "传感器名字为："+name+" 传感器位置："+sensorAddress+" 传感器详细信息："
                    +sensorIntroduction+" 传感器状态：未运行";
    }
}
