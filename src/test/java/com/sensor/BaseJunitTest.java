package com.sensor;

import com.sensor.dao.SensorDao;
import com.sensor.domain.Sensor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:ApplicationContext.xml"})
public class BaseJunitTest {

    @Autowired
    private SensorDao sensorDao;

    @Test
    public void testgetAllSensors(){

    }
}

