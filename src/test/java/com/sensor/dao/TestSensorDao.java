package com.sensor.dao;

import com.sensor.BaseJunitTest;
import com.sensor.domain.Sensor;
import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
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

    }

    @Test
    public void testquerySensorById(){
        Sensor sensor= new Sensor();
        sensor = sensorDao.querySensorById(4000013);
        System.out.println(sensor);
    }

    @Test
    public void testeditSensor(){


    }

    @Test
    public void testgetNewestTempSensorValue(){
        int tempreature = sensorDao.getNewestTempSensorValue("thtable1");
        int hum = sensorDao.getNewestHumSensorValue("thtable1");
        System.out.println("温度："+tempreature);
        System.out.println("湿度："+hum);
    }

    @Test
    public void testetNewestCputempValue(){
        double cputemp = sensorDao.getNewestCputempValue("cputemp");
        System.out.println("cpu温度："+cputemp);
    }

    @Test
    public void testgetTemperatureSensorDatas(){
        ArrayList<Integer> temperatures = new ArrayList<>();
        temperatures = sensorDao.getHumitySensorDatas("thtable1");
        for(int e: temperatures){
            System.out.println(e);
        }
    }

    @Test
    public void testgetCputempDatas(){
        ArrayList<Double> cputemps = new ArrayList<>();
        cputemps = sensorDao.getCputempDatas("cputemp");
        for(Double i: cputemps){
            System.out.println(i);
        }
    }
}
