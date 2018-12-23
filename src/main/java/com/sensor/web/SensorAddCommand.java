package com.sensor.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 添加传感器使用的命令
 * @author Wongnoubo
 */

public class SensorAddCommand {
    private String name;
    private String introduction;
    private BigDecimal price;
    private String address;
    private int state;
    private long id;

    public void setSensorName(String name){
        this.name = name;
    }

    public String getSensorName(){
        return this.name;
    }

    public void setSensorIntroduction(String introduction){
        this.introduction = introduction;
    }

    public String getSensorIntroduction(){
        return this.introduction;
    }

    public void setSensorAddress(String address){
        this.address = address;
    }

    public String getSensorAddress(){
        return this.address;
    }

    public void setSensorPrice(BigDecimal price){
        this.price = price;
    }

    public BigDecimal getSensorPrice(){
        return this.price;
    }

    public void setSensorId(long id){
        this.id = id;
    }

    public long getSensorId(){
        return this.id;
    }

    public void setSensorState(int state){
        this.state = state;
    }

    public int getSensorState(){
        return this.state;
    }
}
