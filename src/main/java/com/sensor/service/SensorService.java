package com.sensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.sensor.dao.SensorDao;
import com.sensor.domain.Sensor;

@Service
public class SensorService {

    private SensorDao sensorDao;
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

}
