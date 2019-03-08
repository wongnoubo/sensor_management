package com.sensor.service;

import com.sensor.dao.SensorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sensor.dao.SensorGridDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 分页service
 */

@Service
public class SensorGridService {

    private SensorGridDao sensorGridDao;
    @Autowired
    public void setSensorGridDao(SensorGridDao sensorGridDao){
        this.sensorGridDao = sensorGridDao;
    }

    public ArrayList getSensorPageList(String infotablename,int current,int rowCount){
        return sensorGridDao.getSensorPageList(infotablename,current,rowCount);
    }
}
