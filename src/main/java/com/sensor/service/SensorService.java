package com.sensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.sensor.dao.SensorDao;
import com.sensor.domain.Sensor;


/**
 * 传感器service
 * @author Wongnoubo
 */

@Service
public class SensorService {

    private SensorDao sensorDao;
    @Autowired
    public void setSensorDao(SensorDao sensorDao){
        this.sensorDao = sensorDao;
    }

    public ArrayList<Sensor> querySensor(String searchWord,String sensorInfoName){
        return sensorDao.querySensor(searchWord,sensorInfoName);
    }

    public ArrayList<Sensor> getAllSensors(String infotablename){
        return sensorDao.getAllSensors(infotablename);
    }

    public Sensor querySensorById(long sensorId,String sensorInfoTableName){
        return sensorDao.querySensorById(sensorId,sensorInfoTableName);
    }

    public int deleteSensor(long sensorId,String sensorInfoTableName){
        return sensorDao.deleteSensor(sensorId,sensorInfoTableName);
    }

    public boolean matchSensor(String searchWord,String sensorTableName){
        return sensorDao.matchSensor(searchWord,sensorTableName)>0;
    }

    public boolean addSensor(Sensor sensor,String sensorInfoTable){
        return sensorDao.addSensor(sensor,sensorInfoTable)>0;
    }

    public boolean editSensor(Sensor sensor,String sensorInfoTable){
        return sensorDao.editSensor(sensor,sensorInfoTable)>0;
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

    public boolean createSensorTable(String tablename){
        return sensorDao.createSensorTable(tablename)<0;
    }

    public boolean dropSensorTable(String tablename){
        return sensorDao.dropSensorTable(tablename)>0;
    }

    public int getHumenState(String tablename){
        return sensorDao.getHumenState(tablename);
    }

    public ArrayList<Integer>getHumenStates(String tablename){
        return sensorDao.getHumenStates(tablename);
    }

    public int getAirState(String tablename){
        return sensorDao.getAirState(tablename);
    }

    public ArrayList<Integer> getAirStates(String tablename){
        return sensorDao.getAirStates(tablename);
    }

    public ArrayList<String>getTimeStamps(String tablename){
        return sensorDao.getTimeStamps(tablename);
    }

    public String getTimeStamp(String tablename){
        return sensorDao.getTimeStamp(tablename);
    }

}
