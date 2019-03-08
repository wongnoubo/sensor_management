package com.sensor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sensor.domain.Sensor;
import com.sensor.domain.SensorGrid;

/**
分页功能
 */

@Repository
public class SensorGridDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public ArrayList getSensorPageList(String infotablename,int current,int rowCount){
        final SensorGrid sensorGrid = new SensorGrid();
        final ArrayList<Sensor> sensors = new ArrayList<>();//select * from sensorinfo0 limit 0,5;
        jdbcTemplate.query("select * from "+infotablename+" limit "+current*10+","+rowCount,new RowCallbackHandler(){
            public void processRow(ResultSet resultSet) throws SQLException{
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    Sensor sensor = new Sensor();
                    sensor.setName(resultSet.getString("sensorName"));
                    sensor.setSensorAddress(resultSet.getString("sensorAddress"));
                    sensor.setId(resultSet.getLong("sensorId"));
                    sensor.setPrice(resultSet.getBigDecimal("sensorPrice"));
                    sensor.setSensorState(resultSet.getInt("sensorState"));
                    sensor.setSensorIntroduction(resultSet.getString("sensorIntroduction"));
                    sensors.add(sensor);
                }
                sensorGrid.setRows(sensors);
            }
        });
        return sensorGrid.getRows();
    }
}
