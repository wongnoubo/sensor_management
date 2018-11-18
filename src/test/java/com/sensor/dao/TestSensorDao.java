package com.sensor.dao;

import com.sensor.domain.Sensor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.math.BigDecimal;

import com.sensor.dao.SensorDao;

public class TestSensorDao extends BaseJunitTest{
    @Autowired
    private SensorDao sensorDao;

    @Test
    public void testgetAllSensors(){
        ArrayList<Sensor> sensors= new ArrayList<>();
        sensors=sensorDao.getAllSensors();
        for(Sensor sensor: sensors){
            System.out.println(sensor);
        }
    }

    @Test
    public void testquerySensor(){
        ArrayList<Sensor> sensors= new ArrayList<>();
        sensors = sensorDao.querySensor("暨南大学");
        for( Sensor sensor:sensors){
            System.out.println(sensor);
        }
    }


    @Test
    public void testdeleteSensor(){
        sensorDao.deleteSensor(1234567);
        ArrayList<Sensor> sensors= new ArrayList<>();
        sensors=sensorDao.getAllSensors();
        for(Sensor sensor: sensors){
            System.out.println(sensor);
        }
    }

    @Test
    public void testaddSensor(){
        Sensor sensor = new Sensor();
        sensor.setSenserName("暨南大学20周年生日快乐！");
        BigDecimal c=new BigDecimal(30.00);
        sensor.setPrice(c);
        sensor.setSensorAddress("珠海市香洲区前山路");
        sensor.setSensorIntroduction("用于检测3415寝室是否有人");
        sensor.setSensorState(1);
        sensorDao.addSensor(sensor);
        ArrayList<Sensor> sensors= new ArrayList<>();
        sensors=sensorDao.getAllSensors();
        for(Sensor sensortemp: sensors){
            System.out.println(sensortemp);
        }
    }
}
