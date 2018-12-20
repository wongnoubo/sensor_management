package com.sensor.domain;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Sensor {
    private long id;
    private String name;
    private double value;
    private String sensorAddress;
    private String sensorIntroduction;
    private String sensortableName;
    private BigDecimal price;
    private int sensorState;
    private int humenState;
    private int toxicAirState;
    private String timeStamp;
    private ArrayList<String> timeStamps;
    private ArrayList<Integer> toxicAirStates;
    private ArrayList<Integer> humenStates;
    private int temperature;
    private ArrayList<Integer> temperatures;
    private int humidity;
    private ArrayList<Integer> humidities;
    private double cputemp;
    private ArrayList<Double> cputemps;

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

    public String getSensortableName() {
        return sensortableName;
    }

    public void setSensortableName(String sensortableName) {
        this.sensortableName = sensortableName;
    }

    public ArrayList<Integer> getTemperatures(){
        return this.temperatures;
    }

    public void setTemperatures(ArrayList<Integer> temperatures){
        this.temperatures = temperatures;
    }

    public ArrayList<Integer> getHumidities(){
        return  this.humidities;
    }

    public void setHumidities(ArrayList<Integer> humidities){
        this.humidities = humidities;
    }

    public ArrayList<Double> getCputemps() {
        return cputemps;
    }

    public void setCputemps(ArrayList<Double> cputemps) {
        this.cputemps = cputemps;
    }

    public int getHumenState() {
        return humenState;
    }

    public void setHumenState(int humenState) {
        this.humenState = humenState;
    }

    public ArrayList<Integer> getHumenStates() {
        return humenStates;
    }

    public void setHumenStates(ArrayList<Integer> humenStates) {
        this.humenStates = humenStates;
    }

    public ArrayList<Integer> getToxicAirStates() {
        return toxicAirStates;
    }

    public int getToxicAirState() {
        return toxicAirState;
    }

    public void setToxicAirState(int toxicAirState) {
        this.toxicAirState = toxicAirState;
    }

    public void setToxicAirStates(ArrayList<Integer> toxicAirStates){
        this.toxicAirStates=toxicAirStates;
    }

    public ArrayList<String> getTimeStamps() {
        return timeStamps;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTimeStamps(ArrayList<String> timeStamps) {
        this.timeStamps = timeStamps;
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
