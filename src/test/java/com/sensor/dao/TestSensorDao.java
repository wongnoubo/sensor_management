package com.sensor.dao;

import com.sensor.BaseJunitTest;
import com.sensor.domain.Sensor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.math.BigDecimal;


public class TestSensorDao extends BaseJunitTest {
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
        sensors = sensorDao.querySensor("4000004");
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

    @Test
    public void testquerySensorById(){
        ArrayList<Sensor> sensors= new ArrayList<>();
        sensors = sensorDao.querySensorById("4000005");
        for( Sensor sensor:sensors){
            System.out.println(sensor);
        }
    }

    @Test
    public void testeditSensor(){
        Sensor sensor = new Sensor();
        sensor.setSenserName("四川大学");
        BigDecimal c=new BigDecimal(20.00);
        sensor.setPrice(c);
        sensor.setSensorAddress("四川省成都市");
        sensor.setSensorIntroduction("我爱你啊！");
        sensor.setSensorState(0);
        sensor.setSensorId(4000005);
        sensorDao.editSensor(sensor);
        ArrayList<Sensor> sensors= new ArrayList<>();
        sensors=sensorDao.getAllSensors();
        for(Sensor sensortemp: sensors){
            System.out.println(sensortemp);
        }
    }
}
