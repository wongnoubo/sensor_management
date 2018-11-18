package com.sensor.service;

import com.sensor.BaseJunitTest;
import com.sensor.domain.Sensor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class SensorServiceTest extends BaseJunitTest {
    @Autowired
    private SensorService sensorService;

    @Test
    public void testquerySensor(){
        ArrayList<Sensor> sensors= new ArrayList<>();
        sensors = sensorService.querySensor("温度传感器");
        for(Sensor sensor: sensors){
            System.out.println(sensor);
        }
    }

    @Test
    public void testgetAllSensors(){
        ArrayList<Sensor> sensors= new ArrayList<>();
        sensors = sensorService.getAllSensors();
        for(Sensor sensor: sensors){
            System.out.println(sensor);
        }
    }

    @Test
    public void testdeleteSensor(){
        sensorService.deleteSensor(1233467);
    }
}
