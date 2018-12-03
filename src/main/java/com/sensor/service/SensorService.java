package com.sensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.sensor.dao.SensorDao;
import com.sensor.domain.Sensor;
import com.sensor.domain.SensorNameTable;

@Service
public class SensorService {

    private SensorDao sensorDao;
    private SensorNameTable sensorNameTable;
    @Autowired
    public void setSensorDao(SensorDao sensorDao){
        this.sensorDao = sensorDao;
    }

    public ArrayList<Sensor> querySensor(String searchWord){
        return sensorDao.querySensor(searchWord);
    }

    public ArrayList<Sensor> getAllSensors(){
        return sensorDao.getAllSensors();
    }

    public Sensor querySensorById(long sensorId){
        return sensorDao.querySensorById(sensorId);
    }

    public int deleteSensor(long sensorId){
        return sensorDao.deleteSensor(sensorId);
    }

    public boolean matchSensor(String searchWord){
        return sensorDao.matchSensor(searchWord)>0;
    }

    public boolean addSensor(Sensor sensor){
        return sensorDao.addSensor(sensor)>0;
    }

    public boolean editSensor(Sensor sensor){
        return sensorDao.editSensor(sensor)>0;
    }

    public int getNewestTempSensorValue(String tablename){
        return sensorDao.getNewestTempSensorValue(tablename);
    }

    public ArrayList<Integer> getTemperatureSensorDatas(String tablename){
        return sensorDao.getTemperatureSensorDatas(tablename);
    }

    public int getNewestHumSensorValue(String tablename){
        return sensorDao.getNewestHumSensorValue(tablename);
    }

    public ArrayList<Integer> getHumitySensorDatas(String tableanme){
        return sensorDao.getHumitySensorDatas(tableanme);
    }

    public double getNewestCputempValue(String tablename){
        return sensorDao.getNewestCputempValue(tablename);
    }

    public ArrayList<Double> getCputempDatas(String tablename){
        return sensorDao.getCputempDatas(tablename);
    }

    public boolean setSensorTableName(String tablename,String sensortype,String sensorAddress){
        return sensorDao.setSensorTableName(tablename,sensortype,sensorAddress)>0;
    }

    public int deleteSensorTableName(int id){
        return sensorDao.deleteSensorTableName(id);
    }

    public SensorNameTable getSensorTableName(String sensortype,String sensorAddress){
        return sensorDao.getSensorTableName(sensortype,sensorAddress);
    }

    public boolean createSensorTable(String tablename,String value){
        return sensorDao.createSensorTable(tablename,value)>0;
    }

    public boolean dropSensorTable(String tablename){
        return sensorDao.dropSensorTable(tablename)>0;
    }
}
