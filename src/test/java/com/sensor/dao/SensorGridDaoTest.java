package com.sensor.dao;

import com.sensor.BaseJunitTest;
import com.sensor.domain.Sensor;
import com.sensor.domain.SensorGrid;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;


public class SensorGridDaoTest extends BaseJunitTest{
    @Autowired
    private SensorGridDao sensorGridDao;

    @Test
    public void testgetSensorPageList(){
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors=sensorGridDao.getSensorPageList("sensorinfo0",0,5);
        System.out.println(sensors.size());
    }
}
