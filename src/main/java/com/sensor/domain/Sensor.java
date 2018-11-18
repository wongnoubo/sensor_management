package com.sensor.domain;

import java.math.BigDecimal;

public class Sensor {
    private long sensorId;
    private String senserName;
    private String sensorAddress;
    private String sensorIntroduction;
    private BigDecimal price;
    private int sensorState;

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public String getSenserName() {
        return senserName;
    }

    public void setSenserName(String senserName) {
        this.senserName = senserName;
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

    public void setPrice(BigDecimal price) {
        this.price = price;
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
    @Override
    public String toString(){
        if(sensorState==1)
            return "传感器名字为："+senserName+" 传感器位置："+sensorAddress+" 传感器详细信息："
                    +sensorIntroduction+" 传感器状态：正常运行";
        else
            return "传感器名字为："+senserName+" 传感器位置："+sensorAddress+" 传感器详细信息："
                    +sensorIntroduction+" 传感器状态：未运行";
    }
}
